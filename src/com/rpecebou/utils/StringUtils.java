package com.rpecebou.utils;

/**
 * 
 * @author rpecebou
 *
 *         Some useful functions on strings
 */
public class StringUtils {

	/**
	 * 
	 * @param s
	 *            the string to convert
	 * @return an int value corresponding to that string
	 */
	public static int toInt(String s) {
		int result = 0;
		int factor = 1;
		for (char c : s.toCharArray()) {
			result += c * factor;
			factor *= 100;
		}
		return Math.abs(result);
	}

}
