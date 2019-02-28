package com.paytm.smartretail_api_automation.storecreationapi;

import org.testng.annotations.Test;

import com.paytm.smartretail_api_automation.utilities.CommonUtil;

public class TestClass {

//	@Test
	public void test() {
		System.out.println("GST : " + CommonUtil.generateValidGST());
	}
	
	@Test
	public void calcaulateCheckSum() {
		System.out.println("Checksum : "+CommonUtil.calculateCheckSum("29AAACB5343E2Z"));
	}

}
