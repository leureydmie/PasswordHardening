package com.rpecebou.init;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.rpecebou.math.Crypto;
import com.rpecebou.structures.Constants;
import com.rpecebou.structures.LoginAttempt;

/**
 * 
 * @author rpecebou
 *
 *         Generate the history file
 */
public class HistoryFileCreator {

	private List<LoginAttempt> _loginHistory;

	private LoginAttempt _initialLogin;

	/**
	 * 
	 * @param initialLogin
	 * @param loginHistory
	 */
	public HistoryFileCreator(LoginAttempt initialLogin, List<LoginAttempt> loginHistory) {
		_loginHistory = loginHistory;
		_initialLogin = initialLogin;
	}

	/**
	 * 
	 * @param hardenedPassword
	 *            the password to use for encrypting the history file
	 */
	public void process(BigInteger hardenedPassword) {
		String content = createHistoryFile();
		try {
			Files.write(Paths.get(Constants.PATH_TO_HISTORY_FILE), Crypto.encrypt(content, hardenedPassword));
		} catch (IOException e) {
			e.getMessage();
			System.exit(-1);
		}
	}

	/**
	 * 
	 * @return the non-encrypted content of the history file
	 * 
	 *         Should be encrypted before written to file !!!
	 */
	private String createHistoryFile() {
		StringBuilder builder = new StringBuilder();
		for (LoginAttempt l : _loginHistory) {
			builder.append(l.toString());
		}
		builder.append(_initialLogin.toString());
		for (int i = 1 + _loginHistory.size(); i < Constants.HISTORY_SIZE; i++) {
			builder.append(LoginAttempt.DUMMY_ATTEMPT.toString());
		}
		/*
		 * Append MD5 hash will make us able to detect if we correctly decrypted
		 * the file
		 */
		builder.append(Crypto.hashMD5(builder.toString()));
		return builder.toString();
	}

}
