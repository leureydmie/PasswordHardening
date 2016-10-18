package com.rpecebou.login;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rpecebou.init.HistoryFileCreator;
import com.rpecebou.init.Initializer;
import com.rpecebou.io.Parsers;
import com.rpecebou.math.PseudoRandomGenerator;
import com.rpecebou.math.RandomPrimeGenerator;
import com.rpecebou.structures.Constants;
import com.rpecebou.structures.LoginAttempt;
import com.rpecebou.structures.NonVolatileStorageContent;
import com.rpecebou.structures.Point;

public class HistoryFileDecryptorTest {

	private LoginAttempt _initialLogin;

	private BigInteger _hardenedPassword;

	private BigInteger _q;

	@Test
	public void process_shouldBeGranted() {
		Assert.assertNotNull(new HistoryFileDecryptor(_hardenedPassword).process());
	}

	@Test
	public void process_shouldBeDenied() {
		Assert.assertNull(
				new HistoryFileDecryptor(new PseudoRandomGenerator(Constants.Q_SIZE).next().mod(_q)).process());
	}

	@Test
	public void process_calculatedPassword_shouldBeGranted() {
		new Initializer(_initialLogin, _initialLogin.getTypedPassword()).process();
		LoginAttempt newLoginAttempt = new LoginAttempt("TotoTo", Arrays.asList(new Integer[] { 10, 2, 22, 2, 22 }));
		NonVolatileStorageContent content = null;
		try {
			content = Parsers.parseVolatileStorage();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		List<Point> selectedFeatures = new InstructionSelector(content).process(newLoginAttempt);
		BigInteger hardenedPassword = new HardenedPasswordCalculator(selectedFeatures, content.getQ()).interpolate();
		System.out.println("Calculated hardened password is : " + hardenedPassword.toString());
		Assert.assertNotNull(new HistoryFileDecryptor(hardenedPassword).process());
	}

	@Before
	public void setUp() {
		_initialLogin = new LoginAttempt("TotoTo", Arrays.asList(new Integer[] { 10, 2, 22, 2, 22 }));
		_q = RandomPrimeGenerator.generate(Constants.Q_SIZE);
		_hardenedPassword = new PseudoRandomGenerator(Constants.Q_SIZE).next().mod(_q);
		new HistoryFileCreator(_initialLogin, new LinkedList<>()).process(_hardenedPassword);
	}

}
