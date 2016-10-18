package com.rpecebou.main;

import java.io.FileNotFoundException;
import java.util.List;

import com.rpecebou.exceptions.InvalidInputException;
import com.rpecebou.init.Initializer;
import com.rpecebou.io.InputParser;
import com.rpecebou.io.Parsers;
import com.rpecebou.login.LoginChecker;
import com.rpecebou.structures.LoginAttempt;
import com.rpecebou.structures.NonVolatileStorageContent;
import com.rpecebou.update.Updater;

/**
 * 
 * @author rpecebou
 *
 *         Coordination of the different stages for the hardening password
 *         authentication
 */
public class Processor {

	private InputParser _loginAttempts;

	private String _password;

	/**
	 * 
	 * @param loginAttempts
	 *            the login attempts parser
	 */
	public Processor(InputParser loginAttempts, String password) {
		_loginAttempts = loginAttempts;
		_password = password;
	}

	/**
	 * 
	 * @throws InvalidInputException
	 */
	public void process(boolean correctErrors) throws InvalidInputException {
		init();
		for (LoginAttempt nextAttempt = _loginAttempts
				.parseLoginAttempt(); null != nextAttempt; nextAttempt = _loginAttempts.parseLoginAttempt()) {
			int accessGranted = checkAttempt(nextAttempt, correctErrors);
			/*
			 * Write access to output
			 */
			System.out.println(accessGranted);
		}
	}

	/**
	 * 
	 * @param nextAttempt
	 * @return 1 if access granted, 0 otherwise
	 */
	private int checkAttempt(LoginAttempt nextAttempt, boolean correctErrors) {
		/*
		 * Get structures from non-volatile storage
		 */
		NonVolatileStorageContent content = null;
		try {
			content = Parsers.parseVolatileStorage();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}

		List<LoginAttempt> loginHistory = new LoginChecker(nextAttempt, content).process(correctErrors);
		if (null == loginHistory) {
			/*
			 * ACCESS DENIED
			 */
			return 0;
		}
		/*
		 * ACCESS GRANTED
		 */
		new Updater(loginHistory, nextAttempt, content).process();
		return 1;
	}

	/**
	 * Launch Initialization stage
	 * 
	 * @throws InvalidInputException
	 */
	private void init() throws InvalidInputException {
		System.out.println(new Initializer(_loginAttempts.parseLoginAttempt(), _password).process());
	}

}
