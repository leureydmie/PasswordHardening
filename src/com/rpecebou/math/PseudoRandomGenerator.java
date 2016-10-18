package com.rpecebou.math;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author rpecebou
 *
 *         This class contains functions to help generate pseudorandom numbers
 *         of a certain size
 */
public class PseudoRandomGenerator {

	private int _size;

	private Random _generator;

	/**
	 * 
	 * @param size
	 *            the bit length of the generated coefficients (before modulus)
	 * @param degree
	 *            the degree of the polynomial
	 * @param modulus
	 *            the finite field index for the coefficients
	 * @param initialValue
	 *            the value of the polynomial for x=0
	 * @return a random generated polynomial P, with P(0)=initialValue
	 */
	public static Polynomial generatePolynomial(int size, int degree, BigInteger modulus, BigInteger initialValue) {
		List<BigInteger> coefficients = new LinkedList<>();
		coefficients.add(initialValue);
		for (int i = 0; i < degree - 1; i++) {
			coefficients.add(new PseudoRandomGenerator(size).next().mod(modulus));
		}
		return new Polynomial(coefficients);
	}

	/**
	 * 
	 * @param size
	 * @param key
	 */
	public PseudoRandomGenerator(int size, BigInteger key) {
		_size = size;
		if (key.equals(BigInteger.ZERO)) {
			_generator = new Random();
		} else {
			_generator = new Random(key.longValue());
		}
	}

	/**
	 * 
	 * @param size
	 */
	public PseudoRandomGenerator(int size) {
		this(size, BigInteger.ZERO);
	}

	/**
	 * 
	 * @return the next random number output by the generator
	 */
	public BigInteger next() {
		return new BigInteger(_size, _generator);
	}

}
