package com.paytm.smartretail_api_automation.storecreationapi;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paytm.smartretail_api_automation.utilities.CommonUtil;
import com.paytm.smartretail_api_automation.utilities.Constants;
import com.paytm.smartretail_api_automation.utilities.Status;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestAddEditBranch {

	private static final String USER_NAME = "";
	private static final String PASSWORD = "";
	private static final String TEST_REQUEST_JSON_FILEPATH = Constants.TEST_STORE_API_JSON_FILE_PATH;

	@BeforeClass
	public void init() {

		RestAssured.baseURI = "";
		RestAssured.basePath = "";
	}

	@Test
	public void testAddBranchForStore() {

		String request = getRequestForBranchEditApi("addbranchrequest.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();
		Assert.assertEquals(response.statusCode(), Status.OK.getStatusCode(), "Incorrect response code!!!");
		
		// validate whether data is persisted
	}

	@Test
	public void testEditBranchForStore() {

		String request = getRequestForBranchEditApi("editbranchrequest.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();
		Assert.assertEquals(response.statusCode(), Status.OK.getStatusCode(), "Incorrect response code!!!");
		
		// validate whether data is persisted
	}

	@Test
	public void testAddBranch_EmptyAddress() {

		String request = getRequestForBranchEditApi("addbranch_without_address.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();

		// assert valid error code
	}

	@Test
	public void testEditBranch_EmptyAddress() {

		String request = getRequestForBranchEditApi("editbranch_without_address.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();

		// assert valid error code
	}

	@Test
	public void testAddBranchForStore_InvalidStoreExternalId() {

		String invalidStoreExternalId = "";
		String request = getRequestForBranchEditApi("addbranch_invalid_store_externalid.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();
		Assert.assertEquals(response.statusCode(), Status.OK.getStatusCode(), "Incorrect response code!!!");
	}

	@Test
	public void testEditBranchForStore_InvalidStoreExternalId() {

		String invalidStoreExternalId = "";
		String request = getRequestForBranchEditApi("editbranch_invalid_store_externalid.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();
		Assert.assertEquals(response.statusCode(), Status.OK.getStatusCode(), "Incorrect response code!!!");
	}

	@Test
	public void testAddStoreBranchForOwner() {

		String request = getRequestForBranchEditApi("addstoreforowner.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();
		Assert.assertEquals(response.statusCode(), Status.OK.getStatusCode(), "Incorrect response code!!!");
	}

	@Test
	public void testEditStoreBranchForOwner() {

		String request = getRequestForBranchEditApi("editstoreforowner.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();
		Assert.assertEquals(response.statusCode(), Status.OK.getStatusCode(), "Incorrect response code!!!");
	}

	@Test
	public void testAddStoreBranchForOwner_BlankStoreAddress() {

		String request = getRequestForBranchEditApi("addstoreforowner_blankstoreaddress.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();

		// validate error code
	}

	@Test
	public void testEditStoreBranchForOwner_BlankStoreAddress() {

		String request = getRequestForBranchEditApi("editstoreforowner_blankstoreaddress.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();

		// validate error code
	}

	@Test
	public void testAddStoreBranchForOwner_BlankBranchAddress() {

		String request = getRequestForBranchEditApi("addstoreforowner_blankbranchaddress.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();

		// validate error code
	}

	@Test
	public void testEditStoreBranchForOwner_BlankBranchAddress() {

		String request = getRequestForBranchEditApi("editstoreforowner_blankbranchaddress.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();

		// validate error code
	}

	@Test
	public void testEditStoreBranchForOwner_InvalidOwnerExternalId() {

		String invalidOwnerExternalId = "";
		String request = getRequestForBranchEditApi("editstoreforowner_invalid_owner_externalid.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();
		Assert.assertEquals(response.statusCode(), Status.OK.getStatusCode(), "Incorrect response code!!!");
	}

	@Test
	public void testAddStoreBranchForOwner_InvalidOwnerExternalId() {

		String invalidOwnerExternalId = "";
		String request = getRequestForBranchEditApi("addstoreforowner_invalid_owner_externalid.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).given().body(request).post();
		Assert.assertEquals(response.statusCode(), Status.OK.getStatusCode(), "Incorrect response code!!!");
	}

	private String getRequestForBranchEditApi(String fileName) {

		try {
			return CommonUtil.getJsonFromFile(TEST_REQUEST_JSON_FILEPATH + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
