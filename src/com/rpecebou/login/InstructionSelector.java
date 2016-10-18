package com.rpecebou.login;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import com.rpecebou.math.PseudoRandomGenerator;
import com.rpecebou.structures.Constants;
import com.rpecebou.structures.InstructionTable;
import com.rpecebou.structures.LoginAttempt;
import com.rpecebou.structures.NonVolatileStorageContent;
import com.rpecebou.structures.Point;
import com.rpecebou.utils.StringUtils;

/**
 * 
 * @author rpecebou
 * 
 *         This class contains the functions for selecting the numbers from the
 *         instruction table given a login attempt
 */
public class InstructionSelector {

	private BigInteger _q;

	private BigInteger _r;

	private InstructionTable _instructionTable;

	/**
	 * 
	 * @param q
	 *            the prime number from initialization step
	 * @param r
	 *            the random number, seed for the prime number generators
	 */
	public InstructionSelector(NonVolatileStorageContent content) {
		_q = content.getQ();
		_r = content.getR();
		_instructionTable = content.getInstructionTable();
	}

	/**
	 * 
	 * @param loginAttempt
	 *            the credentials provided by the user
	 * @param instructionTable
	 *            the instruction table for the user trying to log in
	 * @return a list of Point selected with the features from the login attempt
	 */
	public List<Point> process(LoginAttempt loginAttempt) {
		List<Point> result = new LinkedList<>();
		Integer password = StringUtils.toInt(loginAttempt.getTypedPassword());
		BigInteger key = new BigInteger(_r.toString() + password.toString());
		PseudoRandomGenerator P = new PseudoRandomGenerator(Constants.Q_SIZE, _r);
		PseudoRandomGenerator G = new PseudoRandomGenerator(Constants.Q_SIZE, key);
		int i = 0;
		for (Integer phi : loginAttempt.getFeatures()) {
			BigInteger x1 = P.next();
			BigInteger x2 = P.next();
			BigInteger y1 = _instructionTable.getAlpha(i).subtract(G.next());
			BigInteger y2 = _instructionTable.getBeta(i).subtract(G.next());
			result.add((phi < Constants.T) ? new Point(x1, y1) : new Point(x2, y2));
			i++;
		}
		return result;
	}

}
