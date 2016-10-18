package com.rpecebou.structures;

import java.math.BigInteger;

/**
 * 
 * @author rpecebou
 *
 */
public class Point {

	private BigInteger _x;

	private BigInteger _y;

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Point(BigInteger x, BigInteger y) {
		_x = x;
		_y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_x == null) ? 0 : _x.hashCode());
		result = prime * result + ((_y == null) ? 0 : _y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (_x == null) {
			if (other._x != null)
				return false;
		} else if (!_x.equals(other._x))
			return false;
		if (_y == null) {
			if (other._y != null)
				return false;
		} else if (!_y.equals(other._y))
			return false;
		return true;
	}

	/**
	 * 
	 * @return the ordinate
	 */
	public BigInteger getY() {
		return _y;
	}

	/**
	 * 
	 * @return the abscissa
	 */
	public BigInteger getX() {
		return _x;
	}

	@Override
	public String toString() {
		return _x.toString() + ";" + _y.toString();
	}

	public Point mod(BigInteger modulus) {
		return new Point(_x.mod(modulus), _y.mod(modulus));
	}

}
