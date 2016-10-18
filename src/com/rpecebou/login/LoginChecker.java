package com.rpecebou.login;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import com.rpecebou.exceptions.InvalidInputException;
import com.rpecebou.io.InputParser;
import com.rpecebou.structures.Constants;
import com.rpecebou.structures.LoginAttempt;
import com.rpecebou.structures.NonVolatileStorageContent;
import com.rpecebou.structures.Point;

/**
 * 
 * @author rpecebou
 *
 *         Check if a login attempt is valid
 */
public class LoginChecker {

	private LoginAttempt _loginAttempt;

	private NonVolatileStorageContent _context;

	/**
	 * 
	 * @param loginAttempt
	 * @param context
	 */
	public LoginChecker(LoginAttempt loginAttempt, NonVolatileStorageContent context) {
		_loginAttempt = loginAttempt;
		_context = context;
	}

	/**
	 * 
	 * @return the list of login attempts contained in the history file ; null
	 *         if login failed
	 */
	public List<LoginAttempt> process(boolean correctErrors) {
		if (_loginAttempt.getNumberOfFeatures() != _context.getInstructionTable().getSize()) {
			/*
			 * ACCESS DENIED
			 */
			return null;
		}
		List<LoginAttempt> result = selectInstructionsAndDecrypt(_loginAttempt);
		if (null != result) {
			return result;
		}
		if (!correctErrors) {
			/*
			 * NO ERROR CORRECTION, ACCESS DENIED
			 */
			return null;
		}
		/*
		 * ERROR CORRECTION
		 */
		int i = 0;
		for (Integer feature : _loginAttempt.getFeatures()) {
			Integer modifiedFeature = feature < Constants.T ? 2 * Math.abs(Constants.T) : -2 * Math.abs(Constants.T);
			List<Integer> newFeatures = new LinkedList<>();
			newFeatures.addAll(_loginAttempt.getFeatures());
			newFeatures.set(i, modifiedFeature);
			result = selectInstructionsAndDecrypt(new LoginAttempt(_loginAttempt.getTypedPassword(), newFeatures));
			if (null != result) {
				return result;
			}
			i++;
		}
		/*
		 * ACCESS DENIED
		 */
		return null;
	}

	private List<LoginAttempt> selectInstructionsAndDecrypt(LoginAttempt loginAttempt) {
		List<Point> selectedInstructions = new InstructionSelector(_context).process(loginAttempt);
		BigInteger hardenedPassword = new HardenedPasswordCalculator(selectedInstructions, _context.getQ())
				.interpolate();
		String historyFileContent = new HistoryFileDecryptor(hardenedPassword).process();
		if (null == historyFileContent) {
			/*
			 * LOGIN FAILED
			 */
			return null;
		}
		try {
			return new InputParser(historyFileContent).parseStream();
		} catch (InvalidInputException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
}
