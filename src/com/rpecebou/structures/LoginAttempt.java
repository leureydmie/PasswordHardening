package com.rpecebou.structures;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author rpecebou
 *
 */
public class LoginAttempt {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_features == null) ? 0 : _features.hashCode());
		result = prime * result + ((_typedPassword == null) ? 0 : _typedPassword.hashCode());
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
		LoginAttempt other = (LoginAttempt) obj;
		if (_features == null) {
			if (other._features != null)
				return false;
		} else if (!_features.equals(other._features))
			return false;
		if (_typedPassword == null) {
			if (other._typedPassword != null)
				return false;
		} else if (!_typedPassword.equals(other._typedPassword))
			return false;
		return true;
	}

	public static final LoginAttempt DUMMY_ATTEMPT = new LoginAttempt("XXX", Arrays.asList(new Integer[] { 0, 0 }));

	private String _typedPassword;

	private List<Integer> _features;

	/**
	 * 
	 */
	public LoginAttempt() {
		this("", new LinkedList<>());
	}

	/**
	 * 
	 * @param password
	 * @param features
	 */
	public LoginAttempt(String password, List<Integer> features) {
		_typedPassword = password;
		_features = features;
	}

	/**
	 * 
	 * @return the password
	 */
	public String getTypedPassword() {
		return _typedPassword;
	}

	/**
	 * 
	 * @return the features
	 */
	public List<Integer> getFeatures() {
		return _features;
	}

	/**
	 * 
	 * @return the number of features
	 */
	public int getNumberOfFeatures() {
		return _features.size();
	}

	/**
	 * 
	 * @param index
	 * @return the feature of index index
	 */
	public int getFeature(int index) {
		return _features.get(index);
	}

	@Override
	public String toString() {
		StringBuilder bd = new StringBuilder();
		bd.append(_typedPassword);
		bd.append('\n');
		for (Integer i : _features.subList(0, _features.size() - 1)) {
			bd.append(i);
			bd.append(',');
		}
		bd.append(_features.get(_features.size() - 1));
		bd.append("\n");
		return bd.toString();
	}

}
