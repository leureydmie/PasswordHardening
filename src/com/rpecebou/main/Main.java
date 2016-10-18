package com.rpecebou.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import com.rpecebou.exceptions.InvalidInputException;
import com.rpecebou.io.InputParser;

/**
 * 
 * @author rpecebou
 *
 *         Entry point for the system
 */
public class Main {

	public static void main(String[] args) {
		assert (args.length == 3);
		String password = args[0];
		String inputPath = args[1];
		boolean correctErrors = args[2].equals("true") ? true : false;
		try {
			System.setErr(new PrintStream("errors.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		InputParser parser = new InputParser(new File(inputPath));
		try {
			new Processor(parser, password).process(correctErrors);
		} catch (InvalidInputException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

}
