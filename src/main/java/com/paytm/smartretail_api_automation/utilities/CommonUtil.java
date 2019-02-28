package com.paytm.smartretail_api_automation.utilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class CommonUtil {

	public static String getEnvironmentPropertyValue(String propertyKey) {

		String env = System.getProperty("env");
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("src/main/resources/" + env + "_ENV.properties"));
			return properties.getProperty(propertyKey);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getJsonFromFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {

			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}

			return sb.toString();
		} finally {
			br.close();
		}
	}

	public static String randomString(int length) {

		return RandomStringUtils.random(length, Constants.CHARACTER_SET);
	}

	public static String randomStringForEmailId(int length) {

		return RandomStringUtils.random(length, Constants.EMAIL_CHARACTER_SET);
	}

	public static String randomNumberString(int length) {

		return RandomStringUtils.random(length, Constants.NUMBER_SET);
	}

	public static String randomCharacterString(int length) {

		return RandomStringUtils.random(length, Constants.CHARACTER_STRING_SET);
	}

	public static String generateValidGST() {

		String gst = "";

		String stateCode = "";
		Random random = new Random();
		int randomInt = random.nextInt(37);
		if (randomInt < 10) {
			stateCode = "0" + Integer.toString(randomInt);
		} else {
			stateCode = Integer.toString(randomInt);
		}

		String panNumber = randomCharacterString(5) + randomNumberString(4) + randomCharacterString(1);

		// calculate checksum
		String gstWithoutChecksum = stateCode + panNumber + randomNumberString(1) + "Z";
		System.out.println("GST without checksum : " + gstWithoutChecksum);
		String checkSum = calculateCheckSum(gstWithoutChecksum);

		gst = gstWithoutChecksum + checkSum;

		return gst;
	}

	public static String calculateCheckSum(String gstWithoutCheckDigit) {

		String gst = gstWithoutCheckDigit;
		String characterArray = Constants.ALPHANUMERIC_SET;
		String checkSum = "";
		int array[] = new int[36];
		int factor[] = new int[36];
		for (int i = 0; i < array.length; i++) {
			if (i % 2 == 0) {
				factor[i] = 2;
			} else {
				factor[i] = 1;
			}
		}

		int sum = 0;
		for (int i = 0; i < gst.length(); i++) {

			char ch = gst.charAt(i);
			for (int j = 0; j < characterArray.length(); j++) {
				if (ch == characterArray.charAt(j)) {
					int a = j * factor[j];
					int q = a / 36;
					int r = a % 36;
					int s = q + r;
					sum = sum + s;
				}
			}
		}

		int rem = sum % 36;
		int remainder = (36 - rem) % 36;

		for (int i = 0; i < characterArray.length(); i++) {
			if (remainder == i) {
				checkSum = String.valueOf(characterArray.charAt(i));
				return checkSum;
			}
		}

		return checkSum;
	}

}
