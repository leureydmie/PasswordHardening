package com.rpecebou.init;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import com.rpecebou.math.Polynomial;
import com.rpecebou.math.PseudoRandomGenerator;
import com.rpecebou.structures.Constants;
import com.rpecebou.structures.InstructionTable;
import com.rpecebou.structures.LoginAttempt;
import com.rpecebou.structures.NonVolatileStorageContent;
import com.rpecebou.utils.StringUtils;

/**
 * 
 * @author rpecebou
 *
 *         Generate an instruction table
 */
public class InstructionTableGenerator {

	private BigInteger _r;

	private BigInteger _q;

	private Polynomial _f;

	private LoginAttempt _loginAttempt;

	private List<Integer> _distinguishedFeatures;

	private List<BigInteger> _alphas;

	private List<BigInteger> _betas;

	/**
	 * 
	 * @param r
	 *            the key for the pseudo random number generators
	 * @param q
	 *            the modulus
	 * @param f
	 *            the polynomial
	 * @param loginAttempt
	 * @param distinguishedFeatures
	 */
	public InstructionTableGenerator(BigInteger q, BigInteger r, Polynomial f, LoginAttempt loginAttempt,
			List<Integer> distinguishedFeatures) {
		_r = r;
		_q = q;
		_f = f;
		_loginAttempt = loginAttempt;
		_distinguishedFeatures = distinguishedFeatures;
		_alphas = new LinkedList<>();
		_betas = new LinkedList<>();
	}

	/**
	 *
	 * @param r
	 *            the key for the pseudo random number generators
	 * @param q
	 *            the modulus
	 * @param f
	 *            the polynomial
	 * @param loginAttempt
	 */
	public InstructionTableGenerator(BigInteger q, BigInteger r, Polynomial f, LoginAttempt loginAttempt) {
		this(q, r, f, loginAttempt, null);
		_distinguishedFeatures = new LinkedList<>();
		for (int i = 0; i < loginAttempt.getNumberOfFeatures(); i++) {
			_distinguishedFeatures.add(0);
		}
	}

	/**
	 * 
	 * @return the instruction table, as specified in the paper, and write it to
	 *         the non volatile storage
	 */
	public NonVolatileStorageContent process() {
		Integer password = StringUtils.toInt(_loginAttempt.getTypedPassword());
		BigInteger key = new BigInteger(_r.toString() + password.toString());
		PseudoRandomGenerator P = new PseudoRandomGenerator(Constants.Q_SIZE, _r);
		PseudoRandomGenerator G = new PseudoRandomGenerator(Constants.Q_SIZE, key);
		for (int i = 0; i < _loginAttempt.getNumberOfFeatures(); i++) {
			BigInteger alpha = calculateNextCoefficient(P, G);
			BigInteger beta = calculateNextCoefficient(P, G);
			fillTableRow(i, alpha, beta);
		}
		NonVolatileStorageContent content = new NonVolatileStorageContent(_q, _r,
				new InstructionTable(_alphas, _betas));
		try {
			Files.write(Paths.get(Constants.PATH_TO_NON_VOLATILE_STORAGE), content.toString().getBytes("UTF-8"));
		} catch (IOException e) {
			e.getMessage();
			System.exit(-1);
		}
		return content;
	}

	/**
	 * 
	 * @param index
	 * @param alpha
	 * @param beta
	 */
	private void fillTableRow(int index, BigInteger alpha, BigInteger beta) {
		int featureState = _distinguishedFeatures.get(index);
		_alphas.add(featureState == 1 ? beta : alpha);
		_betas.add(featureState == -1 ? alpha : beta);
	}

	/**
	 * 
	 * @param f
	 *            the polynomial
	 * @param P
	 *            a pseudo random generator
	 * @param G
	 *            a pseudo random generator
	 * @param q
	 *            the modulus
	 * @return
	 */
	private BigInteger calculateNextCoefficient(PseudoRandomGenerator P, PseudoRandomGenerator G) {
		return (_f.evaluate(P.next()).add(G.next()));
	}

}
