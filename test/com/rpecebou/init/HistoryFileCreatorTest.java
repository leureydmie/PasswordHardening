package com.rpecebou.init;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rpecebou.login.HistoryFileDecryptor;
import com.rpecebou.math.PseudoRandomGenerator;
import com.rpecebou.math.RandomPrimeGenerator;
import com.rpecebou.structures.Constants;
import com.rpecebou.structures.LoginAttempt;

public class HistoryFileCreatorTest {

	private static final LoginAttempt INITIAL_LOGIN = new LoginAttempt("Toto",
			Arrays.asList(new Integer[] { 10, 2, 22 }));

	private BigInteger _hardenedPassword;

	@Test
	public void process_noLoginHistory() {
		new HistoryFileCreator(INITIAL_LOGIN, new LinkedList<>()).process(_hardenedPassword);
		Assert.assertTrue(new File(Constants.PATH_TO_HISTORY_FILE).exists());
		Assert.assertNotNull(new HistoryFileDecryptor(_hardenedPassword).process());
	}

	@Before
	public void setUp() {
		BigInteger q = RandomPrimeGenerator.generate(Constants.Q_SIZE);
		_hardenedPassword = new PseudoRandomGenerator(Constants.Q_SIZE).next().mod(q);
	}

}
