package com.rpecebou.math;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author rpecebou
 *
 *         This class contains functions to encrypt and decrypt strings using
 *         AES
 * 
 */
public class Crypto {

	/**
	 * 
	 * @param s
	 *            the string to encrypt
	 * @param hardenedPassword
	 *            the key
	 * 
	 * @return an array of bytes corresponding to the encryption of the string
	 *         using AES
	 */
	public static byte[] encrypt(String s, BigInteger hardenedPassword) {
		/*
		 * Encryption key should be 128 bits long, so hash the hardened password
		 */
		byte[] hashedPassword = hashMD5(hardenedPassword.toByteArray());
		/*
		 * Now encrypt stream using AES
		 */
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES");
			SecretKeySpec secretKey = new SecretKeySpec(hashedPassword, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(s.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param encryptedStream
	 *            the stream to decrypt
	 * @param hardenedPassword
	 *            the key
	 * 
	 * @return a string corresponding to the decryption of the stream using AES
	 */
	public static String decrypt(byte[] encryptedStream, BigInteger hardenedPassword) {
		/*
		 * Decryption key should be 128 bits long, so hash the hardened password
		 */
		byte[] hashedPassword = hashMD5(hardenedPassword.toByteArray());
		/*
		 * Now encrypt stream using AES
		 */
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES");
			SecretKeySpec secretKey = new SecretKeySpec(hashedPassword, "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(encryptedStream), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param input
	 *            the stream to hash
	 * @return the MD5 hash of the stream, as a byte array
	 */
	public static byte[] hashMD5(byte[] input) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			return digest.digest(input);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param input
	 *            the stream to hash
	 * @return the MD5 hash converted as a string
	 */
	public static String hashMD5(String input) {
		try {
			byte[] byteArrayHash = hashMD5(input.getBytes("UTF-8"));
			return new BigInteger(1, byteArrayHash).toString(16);
		} catch (UnsupportedEncodingException e) {
			System.err.println(e.getMessage());
			return "";
		}
	}

}
