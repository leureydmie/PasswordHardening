package com.rpecebou.login;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.rpecebou.math.Crypto;
import com.rpecebou.structures.Constants;

/**
 * 
 * @author rpecebou
 *
 */
public class HistoryFileDecryptor {

	private BigInteger _hardenedPassword;

	private String _content;

	private String _hash;

	/**
	 * 
	 * @param hardenedPassword
	 *            the calculated hardened password
	 */
	public HistoryFileDecryptor(BigInteger hardenedPassword) {
		_hardenedPassword = hardenedPassword;
		_content = "";
		_hash = "";
	}

	/**
	 * 
	 * @return the login history if decryption successful, null otherwise
	 */
	public String process() {
		try {
			byte[] encryptedStream = Files.readAllBytes(Paths.get(Constants.PATH_TO_HISTORY_FILE));
			String content = Crypto.decrypt(encryptedStream, _hardenedPassword);
			if (null == content) {
				/*
				 * Decryption failed - ACCESS DENIED
				 */
				return null;
			}
			extractContentFrom(content);
			String calculatedHash = Crypto.hashMD5(_content);
			return calculatedHash.equals(_hash) ? _content : null;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * 
	 * @param stream
	 *            the stream to process
	 * 
	 *            Separate the login history from the hash
	 */
	private void extractContentFrom(String stream) {
		List<String> splittedStream = Arrays.asList(stream.split("\n"));
		int streamSize = splittedStream.size() - 1;
		_hash = splittedStream.get(streamSize);
		StringBuilder sb = new StringBuilder();
		for (String s : splittedStream.subList(0, streamSize)) {
			sb.append(s);
			sb.append("\n");
		}
		_content = sb.toString();
	}

}
