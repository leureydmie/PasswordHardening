package com.rpecebou.math;

import java.math.BigInteger;
import java.util.Random;

public class RandomPrimeGenerator {

	private static final int PRIME_CERTAINTY = 1;

	/**
	 * 
	 * @param size
	 *            the size in bits we want the random number to be
	 * @return a random prime of size bits long
	 */
	public static BigInteger generate(int size) {
		Random r = new Random();
		return new BigInteger(size, r).nextProbablePrime();
	}

}
