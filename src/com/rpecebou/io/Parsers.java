package com.rpecebou.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.rpecebou.structures.Constants;
import com.rpecebou.structures.InstructionTable;
import com.rpecebou.structures.NonVolatileStorageContent;

/**
 * 
 * @author rpecebou
 *
 *         Parse the content of the non volatile storage
 */
public class Parsers {

	private static final String SEPARATOR = ", ";

	/**
	 * 
	 * @return the content of the non volatile storage
	 * @throws FileNotFoundException
	 */
	public static NonVolatileStorageContent parseVolatileStorage() throws FileNotFoundException {
		Scanner sc = new Scanner(new File(Constants.PATH_TO_NON_VOLATILE_STORAGE));
		BigInteger q = new BigInteger(sc.nextLine());
		BigInteger r = new BigInteger(sc.nextLine());
		List<BigInteger> alphas = new LinkedList<>();
		for (String s : sc.nextLine().split(SEPARATOR)) {
			alphas.add(new BigInteger(s));
		}
		List<BigInteger> betas = new LinkedList<>();
		for (String s : sc.nextLine().split(SEPARATOR)) {
			betas.add(new BigInteger(s));
		}
		sc.close();
		return new NonVolatileStorageContent(q, r, new InstructionTable(alphas, betas));
	}

}
