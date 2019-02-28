package com.paytm.smartretail_api_automation.storecreationapi;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.paytm.smartretail_api_automation.dto.storecreation.AddressDto;
import com.paytm.smartretail_api_automation.dto.storecreation.BranchDto;
import com.paytm.smartretail_api_automation.dto.storecreation.LoginCredentials;
import com.paytm.smartretail_api_automation.dto.storecreation.StoreCreationDto;
import com.paytm.smartretail_api_automation.dto.storecreation.StoreDto;
import com.paytm.smartretail_api_automation.utilities.CommonUtil;
import com.paytm.smartretail_api_automation.utilities.Constants;
import com.paytm.smartretail_api_automation.utilities.Status;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestStoreCreationApi {

	private static final String USER_NAME = Constants.STORE_USERNAME;
	private static final String PASSWORD = Constants.STORE_PASSWORD;
	private static final String TEST_REQUEST_JSON_FILEPATH = Constants.TEST_STORE_API_JSON_FILE_PATH;

	String ownerName;
	String storeName;
	String branchName;
	String email;

	Long startTime;
	Long endTime;

	String request = "";

	@BeforeClass
	public void init() {

		RestAssured.baseURI = CommonUtil.getEnvironmentPropertyValue("url");
	}

	@BeforeMethod
	public void beforeMethod(Method method) {

		System.out.println(
				"\n================ Executing test : " + method.getName() + " ================================");
		RestAssured.basePath = "v1/owner/create/";
	}

	@AfterMethod
	public void afterMethod() {

		System.out.println("API response time elapse : " + (endTime - startTime));
		System.out.println(
				"============================================================================================");
	}

	@Test(priority = 0)
	public void testStoreCreation() throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		request = requestJson;
		System.out.println("Request json : " + requestJson);

		startTime = System.currentTimeMillis();
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		endTime = System.currentTimeMillis();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.OK.getStatusCode(), "Incorrect response code!!!");

		// validating the response
		JSONObject responseObject = new JSONObject(response.getBody().asString());
		Assert.assertEquals(responseObject.getString("email"), email, "Mismatch in email!");
		Assert.assertNotNull(responseObject.getString("external_id"), "external_id should not be null");
		Assert.assertEquals(responseObject.getString("name"), ownerName, "Mismatch in owner name!");
		JSONObject storeObject = responseObject.getJSONArray("stores").getJSONObject(0);
		Assert.assertEquals(storeObject.getString("name"), storeName, "Mismatch in store name!");
		Assert.assertNotNull(storeObject.getString("external_id"), "external_id should not be null");
		JSONObject branchObject = storeObject.getJSONArray("branches").getJSONObject(0);
		Assert.assertEquals(branchObject.getString("name"), branchName, "Mismatch in branch name!");
		Assert.assertNotNull(branchObject.getString("external_id"), "external_id should not be null!");

		// verifying the data
		boolean found = false;
		RestAssured.basePath = "/activate";
		Response responseFromActivate = RestAssured.given().auth().basic(USER_NAME, PASSWORD).when().get();
		JSONArray activateObject = new JSONArray(responseFromActivate.getBody().asString());
		for (int i = 0; i < activateObject.length(); i++) {

			JSONObject storeObj = activateObject.getJSONObject(i);
			if (storeObj.getString("name").equalsIgnoreCase(storeName)) {
				JSONArray branchList = storeObj.getJSONArray("branches");
				for (int j = 0; j < branchList.length(); j++) {
					JSONObject branchObj = branchList.getJSONObject(j);
					if (branchObj.getString("name").equalsIgnoreCase(branchName)) {
						found = true;
					}
				}
			}
		}

		if (!found) {
			Assert.fail("Branch not found!!!");
		}

	}

	@Test
	public void testStoreCreation_ExistingOwner_StoreDetailsUpdated()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());
		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);

		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(), "Incorrect response code!!!");

		// validating error response

	}

	@Test
	public void testStoreCreation_DuplicateStore() throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		String randomString = CommonUtil.randomString(15);
		System.out.println("Random string : " + randomString);

		ownerName = Constants.OWNER_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());
		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);

		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(), "Incorrect response code!!!");

		// validating error response

	}

	@Test
	public void testStoreCreation_DuplicateBranch() throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());
		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);

		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(), "Incorrect response code!!!");

		// validating error response
	}

	@Test
	public void testStoreCreation_InvalidRequest() {

		String invalidRequest = request + "]";
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(invalidRequest).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(), "Incorrect response code!!!");

		// validating error response

	}

	@Test
	public void testStoreCreation_BlankJsonRequest() {

		String request = Constants.BLANK_JSON_REQUEST;
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(request).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(), "Incorrect response code!!!");

		// validating error response
	}

	@Test
	public void testStoreCreation_InvalidJsonRequest_MissingKeys()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());
		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(request).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(), "Incorrect response code!!!");

		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject branchErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner")
				.getJSONArray("stores").getJSONObject(0).getJSONArray("branches").getJSONObject(0)
				.getJSONObject("branch_errors");
		JSONArray errorList = branchErrors.getJSONArray("other_errors");
		Assert.assertEquals(errorList.getString(0), "Missing keys: invoice_header_line_1.paytm_merchant_id",
				"Error description not valid!!!");

		// validating error response
	}

	@Test
	public void testStoreCreation_InvalidJsonRequest_ExtraKeys() {

		String request = getStoreCreationRequest("storecreation_invalidjsonrequest_extrakeys.txt");
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(request).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(), "Incorrect response code!!!");

		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject branchErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner")
				.getJSONArray("stores").getJSONObject(0).getJSONArray("branches").getJSONObject(0)
				.getJSONObject("branch_errors");
		JSONArray errorList = branchErrors.getJSONArray("other_errors");
		Assert.assertEquals(errorList.getString(0),
				"Extra keys passed: ['invoice_header_line1', 'invoice_footer_line1']",
				"Error description not valid!!!");

		// validating error response

	}

	@Test
	public void testStoreCreation_BlankOwnerName() throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = "";
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Owner name should not be blank!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject ownerErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner_errors");
		JSONArray errorList = ownerErrors.getJSONArray("name");
		Assert.assertEquals(errorList.getString(0), "Field cannot be blank", "Error description not valid!!!");

		// validating error response

	}

	@Test
	public void testStoreCreation_BlankOwnerGst() throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, "");

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Owner GST should not be blank!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject ownerErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner_errors");
		JSONArray errorList = ownerErrors.getJSONArray("gst");
		Assert.assertEquals(errorList.getString(0), "Invalid value for GST", "Error description not valid!!!");

		// validating error response

	}

	@Test
	public void testStoreCreation_InvalidOwnerEmailId()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.INVALID_EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Owner emailId should be valid!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject ownerErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner_errors");
		JSONArray errorList = ownerErrors.getJSONArray("email");
		Assert.assertEquals(errorList.getString(0), "Not a valid email address", "Error description not valid!!!");

		// validating error response
	}

	@Test
	public void testStoreCreation_BlankOwnerEmailId()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = "";

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Owner emailId should not be blank!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject ownerErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner_errors");
		JSONArray errorList = ownerErrors.getJSONArray("email");
		Assert.assertEquals(errorList.getString(0), "Not a valid email address", "Error description not valid!!!");

		// validating error response
	}

	@Test
	public void testStoreCreation_BlankOwnerRegisteredAddress()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Owner registered address should not be blank!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject ownerErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner_errors");
		JSONArray errorList = ownerErrors.getJSONArray("registered_address");
		Assert.assertEquals(errorList.getString(0), "Invalid pincode ", "Error description not valid!!!");
	}

	@Test
	public void testStoreCreation_BlankOwnerName_BlankOwnerGst()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = "";
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, "");

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Owner registered address should not be blank!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject ownerErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner_errors");
		JSONArray errorList = ownerErrors.getJSONArray("name");
		Assert.assertEquals(errorList.getString(0), "Field cannot be blank", "Error description not valid!!!");

		errorList = ownerErrors.getJSONArray("gst");
		Assert.assertEquals(errorList.getString(0), "Invalid value for GST", "Error description not valid!!!");

	}

	@Test
	public void testStoreCreation_BlankStoreName() throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = "";
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Store name should not be blank!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject storeErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner")
				.getJSONArray("stores").getJSONObject(0).getJSONObject("store_errors");
		JSONArray errorList = storeErrors.getJSONArray("name");
		Assert.assertEquals(errorList.getString(0), "Field cannot be blank", "Error description not valid!!!");
	}

	@Test
	public void testStoreCreation_BlankBranchName() throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = "";
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Branch name should not be blank!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject branchErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner")
				.getJSONArray("stores").getJSONObject(0).getJSONArray("branches").getJSONObject(0)
				.getJSONObject("branch_errors");
		JSONArray errorList = branchErrors.getJSONArray("name");
		Assert.assertEquals(errorList.getString(0), "Field cannot be blank", "Error description not valid!!!");
	}

	@Test
	public void testStoreCreation_BlankStoreAddress()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("", "", "", "", "", "", "", "");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Branch address should not be blank!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject storeErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner")
				.getJSONArray("stores").getJSONObject(0).getJSONObject("store_errors");
		JSONArray errorList = storeErrors.getJSONArray("address");
		Assert.assertEquals(errorList.getString(0), "Invalid pincode ", "Error description not valid!!!");
	}

	@Test
	public void testStoreCreation_BlankBranchAddress()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("", "", "", "", "", "", "", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Branch address should not be blank!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject branchErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner")
				.getJSONArray("stores").getJSONObject(0).getJSONArray("branches").getJSONObject(0)
				.getJSONObject("branch_errors");
		JSONArray errorList = branchErrors.getJSONArray("name");
		Assert.assertEquals(errorList.getString(0), "Field cannot be blank", "Error description not valid!!!");
	}

	@Test
	public void testStoreCreation_InvalidBranchAffiliate()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "Paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Branch affiliate should be valid!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject branchErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner")
				.getJSONArray("stores").getJSONObject(0).getJSONArray("branches").getJSONObject(0)
				.getJSONObject("branch_errors");
		JSONArray errorList = branchErrors.getJSONArray("affiliate");
		Assert.assertEquals(errorList.getString(0), "Invalid affiliate type", "Unexpected error description!!!");
	}

	@Test
	public void testStoreCreation_BlankBranchAffiliate()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Branch affiliate should not be blank!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject branchErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner")
				.getJSONArray("stores").getJSONObject(0).getJSONArray("branches").getJSONObject(0)
				.getJSONObject("branch_errors");
		JSONArray errorList = branchErrors.getJSONArray("affiliate");
		Assert.assertEquals(errorList.getString(0), "Invalid affiliate type", "Error description not valid!!!");
	}

	public void testStoreCreation_MultipleInvalidFields()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkataa", phoneNumbers, "Electricals", "",
				branchName, "", "Welcome!", "Thank you!!!", "", "Paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(), "Unexpected status code!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject branchErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner")
				.getJSONArray("stores").getJSONObject(0).getJSONArray("branches").getJSONObject(0)
				.getJSONObject("branch_errors");
		JSONArray errorList = branchErrors.getJSONArray("affiliate");

		// TODO check for the behaviour, assert error desc for all invalid fields
//				Assert.assertEquals(errorList.getString(0), "Invalid value for affiliate. Must be paytm/blueface/others",
//						"Unexpected error description!!!");
	}

	@Test
	public void testStoreCreation_InvalidGst() throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, "12SDOKM3347S1Z3");

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Invalid GST! Should not be accepted!!!");

		// validate error response
		JSONObject errorResponse = new JSONObject(response.getBody().asString());
		JSONObject ownerErrors = errorResponse.getJSONArray("response").getJSONObject(0).getJSONObject("owner_errors");
		JSONArray errorList = ownerErrors.getJSONArray("gst");

		// TODO change the error desc
		Assert.assertEquals(errorList.getString(0), "Invalid value for GST", "Error description not valid!!!");
	}

	@Test
	public void testStoreCreation_BlankWebPassword() throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.OK.getStatusCode(), "Unexpected error code!!!");

		// TODO Validate error response

	}

	@Test
	public void testStoreCreation_BlankDevicePassword()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.OK.getStatusCode(), "Unexpected error code!!!");

		// TODO Validate error response
	}

	@Test
	public void testStoreCreation_InvalidDevicePassword()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "12345");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Invalid Device password! Should not be accepted!!!");

		// TODO Validate error response
	}

	@Test
	public void testStoreCreation_BlankPhoneNumber() throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Invalid value for user phone number");

		// TODO validate error response
	}

	@Test
	public void testStoreCreation_InvalidPhoneNumber()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("+9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		request = requestJson;
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(request).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Invalid value for user phone number");

		// TODO valdiate error response
	}

	@Test
	public void testStoreCreation_InvalidBranchPhoneNumbers()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("+9898989898", "+9090909000", "+8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.BAD_REQUEST.getStatusCode(),
				"Invalid value for branch phone numbers");

		// TODO validate error response

	}

	@Test
	public void testStoreCreation_BlankNonMandatoryFields_Owner()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.OK.getStatusCode(), "Unexpected error code!!!");

	}

	@Test
	public void testStoreCreation_BlankNonMandatoryFields_Branch()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "", "", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic(USER_NAME, PASSWORD).body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.OK.getStatusCode(), "Unexpected error code!!!");

	}

	@Test
	public void testStoreCreation_UnauthorizedAccess()
			throws JsonGenerationException, JsonMappingException, IOException {

		// creating request json
		ObjectMapper objectMapper = new ObjectMapper();

		ownerName = Constants.OWNER_NAME;
		storeName = Constants.STORE_NAME;
		branchName = Constants.BRANCH_NAME;
		email = Constants.EMAIL;

		LoginCredentials loginCredentials = new LoginCredentials("APIAutomation", "1234");
		AddressDto branchAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace",
				"India", "");
		List<String> phoneNumbers = Arrays.asList("9898989898", "9090909000", "8787909080");
		BranchDto branch = new BranchDto("", "", branchAddress, "Asia/Kolkata", phoneNumbers, "Pharma", "", branchName,
				"", "Welcome!", "Thank you!!!", "", "paytm");
		AddressDto storeAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560102", "BHIVE Workspace", "India",
				"");
		StoreDto store = new StoreDto(branch, storeName, storeAddress);
		AddressDto registeredAddress = new AddressDto("Bangalore", "Karnataka", "", "", "560103", "Panathur Road",
				"India", "near SBI");
		StoreCreationDto storeCreationDto = new StoreCreationDto("9489489999", loginCredentials, store, ownerName,
				registeredAddress, email, CommonUtil.generateValidGST());

		String requestJson = objectMapper.writeValueAsString(storeCreationDto);
		System.out.println("Request json : " + requestJson);
		Response response = RestAssured.given().auth().basic("username", "password").body(requestJson).when().post();
		System.out.println("Response : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		Assert.assertEquals(response.statusCode(), Status.UNAUTHORIZED.getStatusCode(), "Incorrect response code!!!");
	}

	private String getStoreCreationRequest(String fileName) {

		try {
			return CommonUtil.getJsonFromFile(TEST_REQUEST_JSON_FILEPATH + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
