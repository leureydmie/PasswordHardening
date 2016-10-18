package com.rpecebou.login;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rpecebou.init.InstructionTableGenerator;
import com.rpecebou.math.Polynomial;
import com.rpecebou.math.PseudoRandomGenerator;
import com.rpecebou.math.RandomPrimeGenerator;
import com.rpecebou.structures.Constants;
import com.rpecebou.structures.LoginAttempt;
import com.rpecebou.structures.NonVolatileStorageContent;
import com.rpecebou.structures.Point;

public class HardenedPasswordCalculatorTest {

	private LoginAttempt _initialLogin;

	private BigInteger _hardenedPassword;

	private List<Point> _points;

	private BigInteger _q;

	@Test
	public void interpolationTest() {
		Polynomial p = PseudoRandomGenerator.generatePolynomial(Constants.Q_SIZE, 2, _q, _hardenedPassword);
		List<Point> points = new LinkedList<>();
		for (int i = 1; i < 5; i++) {
			BigInteger b = new PseudoRandomGenerator(160).next();
			points.add(new Point(b, p.evaluate(b)));
		}
		/*
		 * Test that Lagrange interpolation works on a small polynomial
		 */
		Assert.assertEquals(_hardenedPassword, new HardenedPasswordCalculator(points, _q).interpolate());
	}

	@Test
	public void process() {
		BigInteger calculatedHardenedPassword = new HardenedPasswordCalculator(_points, _q).interpolate();
		/*
		 * Test that Lagrange interpolation works on actual polynomial
		 */
		Assert.assertEquals(_hardenedPassword, calculatedHardenedPassword);
	}

	@Before
	public void setUp() {
		_initialLogin = new LoginAttempt("TotoTo", Arrays.asList(new Integer[] { 10, 2, 22, 2, 22 }));
		_q = RandomPrimeGenerator.generate(Constants.Q_SIZE);
		BigInteger r = new PseudoRandomGenerator(Constants.R_SIZE).next();
		_hardenedPassword = new PseudoRandomGenerator(Constants.Q_SIZE).next().mod(_q);
		Polynomial f = PseudoRandomGenerator.generatePolynomial(Constants.Q_SIZE, 1, _q, _hardenedPassword);
		NonVolatileStorageContent content = new InstructionTableGenerator(_q, r, f, _initialLogin).process();
		_points = new InstructionSelector(content).process(_initialLogin);
	}

}
