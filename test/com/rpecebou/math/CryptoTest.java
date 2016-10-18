package com.rpecebou.math;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CryptoTest {

	private static final int SIZE = 160;

	private static final String TO_ENCRYPT = "this is a string to encrypt using AES and the hardened password";

	private Random _generator;

	private BigInteger _hardenedPassword;

	@Test
	public void hashMD5() {
		byte[] hashedPassword = Crypto.hashMD5(_hardenedPassword.toByteArray());
		Assert.assertTrue(hashedPassword.length == 128 / 8);
	}

	@Test
	public void encryptAndDecrypt() {
		byte[] encryptedText = Crypto.encrypt(TO_ENCRYPT, _hardenedPassword);
		String decryptedText = Crypto.decrypt(encryptedText, _hardenedPassword);
		Assert.assertEquals(TO_ENCRYPT, decryptedText);
	}

	@Before
	public void setUp() {
		_generator = new Random();
		_hardenedPassword = new BigInteger(SIZE, _generator);
	}
}
