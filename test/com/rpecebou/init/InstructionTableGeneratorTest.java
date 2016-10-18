package com.rpecebou.init;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rpecebou.math.Polynomial;
import com.rpecebou.math.PseudoRandomGenerator;
import com.rpecebou.math.RandomPrimeGenerator;
import com.rpecebou.structures.Constants;
import com.rpecebou.structures.LoginAttempt;
import com.rpecebou.structures.NonVolatileStorageContent;

public class InstructionTableGeneratorTest {

	private LoginAttempt _initialLogin;

	private BigInteger _hardenedPassword;

	private BigInteger _q;

	private BigInteger _r;

	@Test
	public void process() {
		Polynomial f = PseudoRandomGenerator.generatePolynomial(Constants.Q_SIZE, 10, _q, _hardenedPassword);
		NonVolatileStorageContent content = new InstructionTableGenerator(_q, _r, f, _initialLogin).process();
		Assert.assertEquals(content.getR(), _r);
		Assert.assertEquals(content.getQ(), _q);
		System.out.println(content.getInstructionTable().toString());
	}

	@Before
	public void setUp() {
		_initialLogin = new LoginAttempt("Toto", Arrays.asList(new Integer[] { 10, 2, 22 }));
		_q = RandomPrimeGenerator.generate(Constants.Q_SIZE);
		_r = new PseudoRandomGenerator(Constants.Q_SIZE).next();
		_hardenedPassword = new PseudoRandomGenerator(Constants.Q_SIZE).next().mod(_q);
	}

}
