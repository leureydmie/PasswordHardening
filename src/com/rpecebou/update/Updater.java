package com.rpecebou.update;

import java.math.BigInteger;
import java.util.List;

import com.rpecebou.init.HistoryFileCreator;
import com.rpecebou.init.InstructionTableGenerator;
import com.rpecebou.math.Polynomial;
import com.rpecebou.math.PseudoRandomGenerator;
import com.rpecebou.structures.Constants;
import com.rpecebou.structures.LoginAttempt;
import com.rpecebou.structures.NonVolatileStorageContent;

/**
 * 
 * @author rpecebou
 *
 *         Once the login is successful, perform the updates as specified in the
 *         paper
 */
public class Updater {

	private List<LoginAttempt> _loginHistory;

	private LoginAttempt _loginAttempt;

	private BigInteger _q;

	private BigInteger _newR;

	private BigInteger _newHardenedPassword;

	private Polynomial _newF;

	/**
	 * 
	 * @param user
	 * @param loginHistory
	 * @param loginAttempt
	 */
	public Updater(List<LoginAttempt> loginHistory, LoginAttempt loginAttempt, NonVolatileStorageContent context) {
		_loginHistory = loginHistory.size() >= Constants.HISTORY_SIZE ? loginHistory.subList(1, loginHistory.size())
				: loginHistory;
		assert (_loginHistory.size() < Constants.HISTORY_SIZE);
		_loginAttempt = loginAttempt;
		_q = context.getQ();
		_newHardenedPassword = new PseudoRandomGenerator(Constants.Q_SIZE).next().mod(_q);
		_newF = PseudoRandomGenerator.generatePolynomial(Constants.Q_SIZE, _loginAttempt.getNumberOfFeatures() - 1, _q,
				_newHardenedPassword);
		/*
		 * Is not necessarily prime
		 */
		_newR = new PseudoRandomGenerator(Constants.R_SIZE).next();
	}

	/**
	 * 
	 */
	public void process() {
		new HistoryFileCreator(_loginAttempt, _loginHistory).process(_newHardenedPassword);
		/*
		 * Consider the first five attempts as legit, so do not calculate
		 * distinguished features
		 */
		if (_loginHistory.size() >= Constants.HISTORY_SIZE - 2) {
			List<Integer> distinguishedFeatures = new DistinguishingFeaturesSelector(_loginHistory, _loginAttempt)
					.process();
			new InstructionTableGenerator(_q, _newR, _newF, _loginAttempt, distinguishedFeatures).process();
		} else {
			new InstructionTableGenerator(_q, _newR, _newF, _loginAttempt).process();
		}

	}
}
