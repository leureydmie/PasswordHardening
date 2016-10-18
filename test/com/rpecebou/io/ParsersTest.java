package com.rpecebou.io;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rpecebou.init.InstructionTableGenerator;
import com.rpecebou.math.Polynomial;
import com.rpecebou.math.PseudoRandomGenerator;
import com.rpecebou.math.RandomPrimeGenerator;
import com.rpecebou.structures.Constants;
import com.rpecebou.structures.InstructionTable;
import com.rpecebou.structures.LoginAttempt;
import com.rpecebou.structures.NonVolatileStorageContent;

public class ParsersTest {

	private LoginAttempt _initialLogin;
	private BigInteger _q;
	private BigInteger _hardenedPassword;
	private BigInteger _r;
	private Polynomial _f;

	@Test
	public void parseNonVolatileStorage() {
		try {
			NonVolatileStorageContent nvsc = Parsers.parseVolatileStorage();
			Assert.assertEquals(_q, nvsc.getQ());
			Assert.assertEquals(_r, nvsc.getR());
			System.out.println(nvsc.getInstructionTable());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void toStringTest() {
		InstructionTable table = new InstructionTable(Arrays.asList(new BigInteger[] { BigInteger.valueOf(2) }),
				Arrays.asList(new BigInteger[] { BigInteger.valueOf(2) }));
		System.out.println(table);
	}

	@Before
	public void setUp() {
		_initialLogin = new LoginAttempt("TotoTo", Arrays.asList(new Integer[] { 10, 2, 22, 2, 22 }));
		_q = RandomPrimeGenerator.generate(Constants.Q_SIZE);
		_r = new PseudoRandomGenerator(Constants.R_SIZE).next();
		_hardenedPassword = new PseudoRandomGenerator(Constants.Q_SIZE).next().mod(_q);
		_f = PseudoRandomGenerator.generatePolynomial(Constants.Q_SIZE, _initialLogin.getNumberOfFeatures() - 1, _q,
				_hardenedPassword);
		new InstructionTableGenerator(_q, _r, _f, _initialLogin).process();
	}

}
