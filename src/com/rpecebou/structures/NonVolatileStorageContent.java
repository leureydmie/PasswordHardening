package com.rpecebou.structures;

import java.math.BigInteger;

/**
 * 
 * @author rpecebou
 *
 *         Wrapper for the structures required for checking a login attempt
 */
public class NonVolatileStorageContent {

	private BigInteger _q;

	private BigInteger _r;

	private InstructionTable _instructionTable;

	/**
	 * 
	 * @param q
	 *            the large prime modulus
	 * @param r
	 *            the key for the pseudo-random generators
	 * @param instructionTable
	 */
	public NonVolatileStorageContent(BigInteger q, BigInteger r, InstructionTable instructionTable) {
		_q = q;
		_r = r;
		_instructionTable = instructionTable;
	}

	/**
	 * 
	 * @return the modulus
	 */
	public BigInteger getQ() {
		return _q;
	}

	/**
	 * 
	 * @return the key for the pseudo-random generators
	 */
	public BigInteger getR() {
		return _r;
	}

	/**
	 * 
	 * @return the instruction table
	 */
	public InstructionTable getInstructionTable() {
		return _instructionTable;
	}

	@Override
	public String toString() {
		StringBuilder bd = new StringBuilder();
		bd.append(_q.toString());
		bd.append("\n");
		bd.append(_r.toString());
		bd.append("\n");
		bd.append(_instructionTable.toString());
		return bd.toString();
	}

}
