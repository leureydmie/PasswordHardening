package com.rpecebou.structures;

import java.math.BigInteger;
import java.util.List;

/**
 * 
 * @author rpecebou
 *
 *         Representation of an instruction table
 */
public class InstructionTable {

	private List<BigInteger> _alphas;

	private List<BigInteger> _betas;

	/**
	 * 
	 * @param alphas
	 * @param betas
	 */
	public InstructionTable(List<BigInteger> alphas, List<BigInteger> betas) {
		_alphas = alphas;
		_betas = betas;
		assert (_alphas.size() == _betas.size());
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public BigInteger getAlpha(int i) {
		return _alphas.get(i);
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public BigInteger getBeta(int i) {
		return _betas.get(i);
	}

	/**
	 * 
	 * @return the number of features
	 */
	public int getSize() {
		return _alphas.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String alphaString = _alphas.toString();
		sb.append(alphaString.toString().substring(1, alphaString.length() - 1));
		sb.append("\n");
		String betaString = _betas.toString();
		sb.append(betaString.substring(1, betaString.length() - 1));
		return sb.toString();
	}
}
