package com.rpecebou.update;

import java.util.LinkedList;
import java.util.List;

import com.rpecebou.structures.Constants;
import com.rpecebou.structures.LoginAttempt;

/**
 * 
 * @author rpecebou
 *
 *         This class provides all the functions to select the new
 *         distinguishing features once a log in attempt has been successful
 */
public class DistinguishingFeaturesSelector {

	private List<LoginAttempt> _loginHistory;

	private LoginAttempt _loginAttempt;

	/**
	 * 
	 * @param instructionTable
	 *            the instruction table to update
	 * @param loginHistory
	 *            at most 5 previous successful logins
	 * @param loginAttempt
	 *            is assumed to be successful
	 */
	public DistinguishingFeaturesSelector(List<LoginAttempt> loginHistory, LoginAttempt loginAttempt) {
		_loginHistory = loginHistory;
		_loginAttempt = loginAttempt;
	}

	/**
	 * 
	 * @return a list of Integers :
	 * 
	 *         - -1 means we have to update the left column of the instruction
	 *         table.
	 * 
	 *         - 1 means we have to update the right column of the instruction
	 *         table.
	 * 
	 *         - 0 means the feature is not distinguishing
	 */
	public List<Integer> process() {
		List<Integer> distinguishingFeatures = new LinkedList<>();
		for (int i = 0; i < _loginAttempt.getNumberOfFeatures(); i++) {
			double mean = calculateMean(i);
			double standardDeviation = calculateStandardDeviation(i, mean);
			if (Math.abs(mean - Constants.T) > Constants.K * standardDeviation) {
				/*
				 * This is a distinguishing feature !
				 */
				distinguishingFeatures.add(mean < Constants.T ? -1 : 1);
			} else {
				distinguishingFeatures.add(0);
			}
		}
		return distinguishingFeatures;
	}

	/**
	 * 
	 * @param index
	 * @return the mean of the feature of index index
	 */
	private double calculateMean(int index) {
		double result = _loginAttempt.getFeature(index);
		for (LoginAttempt l : _loginHistory) {
			result += l.getFeature(index);
		}
		return result / (1 + _loginHistory.size());
	}

	/**
	 * 
	 * @param index
	 * @param mean
	 * @return the standard deviation of the feature at index index
	 */
	private double calculateStandardDeviation(int index, double mean) {
		double result = _loginAttempt.getFeature(index) - mean;
		result *= result;
		for (LoginAttempt l : _loginHistory) {
			double temp = l.getFeature(index) - mean;
			result += temp * temp;
		}
		return Math.sqrt(result / (1 + _loginHistory.size()));
	}
}
