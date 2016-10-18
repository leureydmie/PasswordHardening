package com.rpecebou.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.rpecebou.exceptions.InvalidInputException;
import com.rpecebou.structures.LoginAttempt;

/**
 * 
 * @author rpecebou
 *
 *         This class contains all the functions to parse login attempts from
 *         file or string
 */
public class InputParser {

	private static final String STATISTICS_SEPARATOR = ",";

	Scanner _scanner;

	/**
	 * 
	 * @param fileToParse
	 */
	public InputParser(File fileToParse) {
		try {
			_scanner = new Scanner(fileToParse);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * 
	 * @param stringToParse
	 */
	public InputParser(String stringToParse) {
		_scanner = new Scanner(stringToParse);
	}

	/**
	 * 
	 * @return the list of the login attempts contained in the stream
	 * @throws InvalidInputException
	 */
	public List<LoginAttempt> parseStream() throws InvalidInputException {
		List<LoginAttempt> result = new LinkedList<>();
		LoginAttempt l = parseLoginAttempt();
		while (null != l) {
			if (!l.equals(LoginAttempt.DUMMY_ATTEMPT)) {
				result.add(l);
			}
			l = parseLoginAttempt();
		}
		_scanner.close();
		return result;
	}

	/**
	 * 
	 * @return the first login attempt contained in the stream
	 * @throws InvalidInputException
	 */
	public LoginAttempt parseLoginAttempt() throws InvalidInputException {
		if (!_scanner.hasNext()) {
			return null;
		}
		String typedPassword = _scanner.nextLine();
		if (!_scanner.hasNext()) {
			throw new InvalidInputException();
		}
		List<Integer> statistics = new LinkedList<>();
		for (String s : _scanner.nextLine().split(STATISTICS_SEPARATOR)) {
			statistics.add(Integer.parseInt(s));
		}
		if (typedPassword.length() != statistics.size() + 1) {
			throw new InvalidInputException();
		}
		return new LoginAttempt(typedPassword, statistics);
	}

}
