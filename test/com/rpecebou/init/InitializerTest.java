package com.rpecebou.init;

import java.io.File;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.rpecebou.structures.Constants;
import com.rpecebou.structures.LoginAttempt;

public class InitializerTest {

	public static final LoginAttempt INITIAL_LOGIN = new LoginAttempt("Toto",
			Arrays.asList(new Integer[] { 10, 2, 22 }));

	@Test
	public void process() {
		new Initializer(INITIAL_LOGIN, INITIAL_LOGIN.getTypedPassword()).process();
		File encryptedFile = new File(Constants.PATH_TO_HISTORY_FILE);
		Assert.assertTrue(encryptedFile.exists());
	}

}
