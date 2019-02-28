package com.paytm.smartretail_api_automation.utilities;

public class Constants {

	public static final String STORE_USERNAME = "admin@weavedin.com";
	public static final String STORE_PASSWORD = "weavedindemo76k";
	public static final String STORE_BRANCH_UUID = "9fc06101-022d-4d4f-8833-b3c73551bfb3";
	public static final String CHANNEL_UUID = "7d95ec94-69c7-41c9-856a-e0031b2d7cc6";

	public static final String DEVICE_HW_ID = "4EH80000OP8932YZ";
	public static final String DEVICE_NAME = "AutomationTest";
	public static final long DEVICE_ID = 826925553;

	public static final String TEST_ORDER_JSON_FILE_PATH = "src/test/resources/orderjson/";
	public static final String TEST_STORE_API_JSON_FILE_PATH = "src/test/resources/storeapijson/";

	public static final String BLANK_JSON_REQUEST = "[]";

	public static final String CHARACTER_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=`~[]{};':\\\",./<>?";
	public static final String EMAIL_CHARACTER_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-.";
	public static final String NUMBER_SET = "0123456789";
	public static final String CHARACTER_STRING_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String ALPHANUMERIC_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String OWNER_NAME = "TestAPIAutomation_Owner_" + CommonUtil.randomString(15);
	public static final String STORE_NAME = "TestAPIAutomation_Store_" + CommonUtil.randomString(15);
	public static final String BRANCH_NAME = "TestAPIAutomation_Branch_" + CommonUtil.randomString(15);
	public static final String EMAIL = "TestAPIAutomation_Email" + CommonUtil.randomStringForEmailId(10) + "@gmail.com";
	public static final String INVALID_EMAIL = "test123";

	public static final int INVOICE_TYPE = 2;
	public static final int ORDER_TYPE = 1;
	public static final int ORDER_STATUS = 7;

}
