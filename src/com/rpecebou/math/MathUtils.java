package com.rpecebou.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 
 * @author rpecebou
 *
 *         Useful functions on BigIntegers
 */
public class MathUtils {

	private static final BigDecimal TWO = new BigDecimal("2");

	private static final BigInteger THREE = new BigInteger("3");

	/**
	 * 
	 * @param x
	 * @return the square root of x
	 */
	public static BigInteger sqrt(BigInteger x) {
		BigInteger div = BigInteger.ZERO.setBit(x.bitLength() / 2);
		BigInteger div2 = div;
		// Loop until we hit the same value twice in a row, or wind
		// up alternating.
		for (;;) {
			BigInteger y = div.add(x.divide(div)).shiftRight(1);
			if (y.equals(div) || y.equals(div2))
				return y;
			div2 = div;
			div = y;
		}
	}

	/**
	 * 
	 * @param b
	 * @return whether b is prime or not
	 */
	public static boolean isPrime(BigInteger b) {
		if (b.compareTo(BigInteger.ONE) == 0 || b.compareTo(TWO.toBigInteger()) == 0) {
			return true;
		}
		BigInteger squareRoot = sqrt(b);
		for (BigInteger i = THREE; i.compareTo(squareRoot) < 0; i = i.add(TWO.toBigInteger())) {
			System.out.println("Testing primality of : " + b);
			if (b.mod(i).equals(BigInteger.ZERO)) {
				return false;
			}
		}
		return true;
	}

}
