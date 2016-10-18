package com.rpecebou.login;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import com.rpecebou.structures.Point;

/**
 * 
 * @author rpecebou
 *
 */
public class HardenedPasswordCalculator {

	private List<BigInteger> _upperValues;

	private List<BigInteger> _lowerValues;

	private List<Point> _points;

	private BigInteger _modulus;

	public HardenedPasswordCalculator(List<Point> points, BigInteger modulus) {
		_points = points;
		_upperValues = new LinkedList<>();
		_lowerValues = new LinkedList<>();
		_modulus = modulus;
	}

	/**
	 * 
	 * @param points
	 * @return the hardened password calculated with that set of points, using
	 *         Lagrange interpolation
	 */
	public BigInteger interpolate() {
		BigInteger result = BigInteger.ZERO;
		for (int i = 0; i < _points.size(); i++) {
			fillValues(i);
		}
		BigInteger commonDenominator = BigInteger.ONE;
		for (BigInteger l : _lowerValues) {
			commonDenominator = commonDenominator.multiply(l);
		}
		int i = 0;
		for (BigInteger u : _upperValues) {
			/*
			 * Division has to be round
			 */
			BigInteger denominator = commonDenominator.divide(_lowerValues.get(i));
			result = result.add(u.multiply(denominator));
			i++;
		}
		/*
		 * This division should be round too
		 */
		return (result.divide(commonDenominator)).mod(_modulus);
	}

	/**
	 * 
	 * @param i
	 * 
	 *            Calculate separately the upper part and the lower part of the
	 *            i-th term of the sum, to avoid float division
	 */
	private void fillValues(int i) {
		Point pi = _points.get(i);
		BigInteger xi = pi.getX();
		BigInteger yi = pi.getY();
		BigInteger upperValue = BigInteger.ONE;
		BigInteger lowerValue = BigInteger.ONE;
		for (int j = 0; j < _points.size(); j++) {
			Point pj = _points.get(j);
			BigInteger xj = pj.getX();
			if (i != j) {
				upperValue = upperValue.multiply(xj);
				lowerValue = lowerValue.multiply(xj.subtract(xi));
			}
		}
		_upperValues.add(upperValue.multiply(yi));
		_lowerValues.add(lowerValue);
	}

}
