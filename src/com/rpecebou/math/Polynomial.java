package com.rpecebou.math;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Polynomial {

	public Polynomial() {
		this(new LinkedList<>());
	}

	public Polynomial(List<BigInteger> coefficients) {
		if (null == coefficients || coefficients.isEmpty()) {
			coefficients = Arrays.asList(new BigInteger[] { BigInteger.ZERO });
		}
		_coefficients = coefficients;
	}

	/*
	 * First value is degree 0 coefficient
	 */
	private List<BigInteger> _coefficients;

	/**
	 * 
	 * @param polynomial
	 *            the polynomial to evaluate
	 * @param value
	 * @return polynomial(value)
	 */
	public BigInteger evaluate(BigInteger value) {
		BigInteger result = BigInteger.ZERO;
		BigInteger iterValue = BigInteger.ONE;
		for (BigInteger b : _coefficients) {
			result = result.add(iterValue.multiply(b));
			iterValue = iterValue.multiply(value);
		}
		return result;
	}

	public int getDegree() {
		return _coefficients.size() - 1;
	}

	public BigInteger getCoefByDegree(int i) {
		return i <= this.getDegree() ? _coefficients.get(i) : BigInteger.ZERO;
	}

	public Polynomial add(Polynomial p) {
		List<BigInteger> resultCoefficients = new LinkedList<>();
		for (int i = 0; i < Math.max(getDegree(), p.getDegree()); i++) {
			resultCoefficients.add(this.getCoefByDegree(i).add(p.getCoefByDegree(i)));
		}
		return new Polynomial(resultCoefficients);
	}

	public Polynomial subtract(Polynomial p) {
		List<BigInteger> resultCoefficients = new LinkedList<>();
		for (int i = 0; i < Math.max(getDegree(), p.getDegree()); i++) {
			resultCoefficients.add(this.getCoefByDegree(i).subtract(p.getCoefByDegree(i)));
		}
		return new Polynomial(resultCoefficients);
	}

	public Polynomial shift() {
		List<BigInteger> resultCoefficients = Arrays.asList(new BigInteger[] { BigInteger.ZERO });
		resultCoefficients.addAll(_coefficients);
		return new Polynomial(resultCoefficients);
	}

	public Polynomial multiplyByScalar(int i) {
		List<BigInteger> resultCoefficients = new LinkedList<>();
		BigInteger bigScalar = BigInteger.valueOf(i);
		for (BigInteger b : _coefficients) {
			resultCoefficients.add(b.multiply(bigScalar));
		}
		return new Polynomial(resultCoefficients);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (BigInteger b : _coefficients) {
			sb.append(b.toString());
			sb.append("X^");
			sb.append(i);
			sb.append(" + ");
			i++;
		}
		return sb.toString();
	}

}
