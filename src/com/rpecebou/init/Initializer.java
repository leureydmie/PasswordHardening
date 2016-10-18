package com.rpecebou.init;

import java.math.BigInteger;
import java.util.LinkedList;

import com.rpecebou.math.Polynomial;
import com.rpecebou.math.PseudoRandomGenerator;
import com.rpecebou.math.RandomPrimeGenerator;
import com.rpecebou.structures.Constants;
import com.rpecebou.structures.LoginAttempt;

/**
 * 
 * @author rpecebou
 *
 *         Perform the entire initialization step
 */
public class Initializer {

	private BigInteger _q;

	private BigInteger _r;

	private BigInteger _hardenedPassword;

	private Polynomial _f;

	private LoginAttempt _initialLogin;

	/**
	 * 
	 * @param user
	 * @param initialLogin
	 */
	public Initializer(LoginAttempt initialLogin, String password) {
		assert (password.equals(initialLogin.getTypedPassword()));
		_initialLogin = initialLogin;
		/*
		 * This is the initial q from the article. Must be a prime number
		 */
		_q = RandomPrimeGenerator.generate(Constants.Q_SIZE);
		/*
		 * Not necessarily prime. Hpwd < q
		 */
		_hardenedPassword = new PseudoRandomGenerator(Constants.Q_SIZE).next().mod(_q);
		_f = PseudoRandomGenerator.generatePolynomial(Constants.Q_SIZE, _initialLogin.getNumberOfFeatures() - 1, _q,
				_hardenedPassword);
		/*
		 * Is not necessarily prime
		 */
		_r = new PseudoRandomGenerator(Constants.R_SIZE).next();
	}

	/**
	 * Perform the initialization step
	 * 
	 * - Generate the instruction table
	 * 
	 * - Create the history file
	 */
	public int process() {
		new InstructionTableGenerator(_q, _r, _f, _initialLogin).process();
		new HistoryFileCreator(_initialLogin, new LinkedList<>()).process(_hardenedPassword);
		return 1;
	}

}
