package com.rpecebou.math;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PseudoRandomGeneratorTest {

	private static final int POLYNOMIAL_DEGREE = 10;

	private static final int BIT_SIZE = 160;

	private BigInteger _seed;

	@Before
	public void setUp() {
		_seed = new BigInteger(BIT_SIZE, new Random());
		System.out.println("--- PseudoRandomGenerator Test : Seed for this execution will be " + _seed);
	}

	@Test
	public void next() {
		PseudoRandomGenerator P = new PseudoRandomGenerator(BIT_SIZE, _seed);
		PseudoRandomGenerator Q = new PseudoRandomGenerator(BIT_SIZE, _seed);
		for (int i = 0; i < POLYNOMIAL_DEGREE; i++) {
			Assert.assertEquals(P.next(), Q.next());
		}
	}

	@Test
	public void generatePolynomial() {
		BigInteger modulus = new PseudoRandomGenerator(BIT_SIZE, _seed).next();
		BigInteger initialValue = new PseudoRandomGenerator(BIT_SIZE).next();
		Polynomial P = PseudoRandomGenerator.generatePolynomial(BIT_SIZE, POLYNOMIAL_DEGREE, modulus, initialValue);
		Assert.assertEquals(P.evaluate(BigInteger.ZERO), initialValue);
		Assert.assertTrue(P.getDegree() == POLYNOMIAL_DEGREE - 1);
	}
}
