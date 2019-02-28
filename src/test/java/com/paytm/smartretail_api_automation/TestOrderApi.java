package com.paytm.smartretail_api_automation;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.paytm.smartretail_api_automation.dto.orderapi.Address;
import com.paytm.smartretail_api_automation.dto.orderapi.ChargeSplit;
import com.paytm.smartretail_api_automation.dto.orderapi.ChargeTaxSplit;
import com.paytm.smartretail_api_automation.dto.orderapi.Comment;
import com.paytm.smartretail_api_automation.dto.orderapi.Contact;
import com.paytm.smartretail_api_automation.dto.orderapi.Customer;
import com.paytm.smartretail_api_automation.dto.orderapi.CustomerDto;
import com.paytm.smartretail_api_automation.dto.orderapi.Discount;
import com.paytm.smartretail_api_automation.dto.orderapi.DiscountSplit;
import com.paytm.smartretail_api_automation.dto.orderapi.Email;
import com.paytm.smartretail_api_automation.dto.orderapi.Employee;
import com.paytm.smartretail_api_automation.dto.orderapi.ExclusiveChargeSplit;
import com.paytm.smartretail_api_automation.dto.orderapi.ExclusiveTaxSplit;
import com.paytm.smartretail_api_automation.dto.orderapi.Invoice;
import com.paytm.smartretail_api_automation.dto.orderapi.InvoiceSummary;
import com.paytm.smartretail_api_automation.dto.orderapi.Item;
import com.paytm.smartretail_api_automation.dto.orderapi.Kot;
import com.paytm.smartretail_api_automation.dto.orderapi.Order;
import com.paytm.smartretail_api_automation.dto.orderapi.OrderDetails;
import com.paytm.smartretail_api_automation.dto.orderapi.OrderSummary;
import com.paytm.smartretail_api_automation.dto.orderapi.Payment;
import com.paytm.smartretail_api_automation.dto.orderapi.Phone;
import com.paytm.smartretail_api_automation.dto.orderapi.Price;
import com.paytm.smartretail_api_automation.dto.orderapi.Properties;
import com.paytm.smartretail_api_automation.dto.orderapi.Schedule;
import com.paytm.smartretail_api_automation.dto.orderapi.Table;
import com.paytm.smartretail_api_automation.dto.orderapi.TaxDetails;
import com.paytm.smartretail_api_automation.dto.orderapi.TaxSplit;
import com.paytm.smartretail_api_automation.dto.orderapi.UserProperties;
import com.paytm.smartretail_api_automation.utilities.CommonUtil;
import com.paytm.smartretail_api_automation.utilities.Constants;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import junit.framework.Assert;
import net.javacrumbs.jsonunit.JsonAssert;

public class TestOrderApi {

	private static final String DEVICE_HW_ID = Constants.DEVICE_HW_ID;
	private static final String DEVICE_NAME = Constants.DEVICE_NAME;
	private static final String USERNAME = Constants.STORE_USERNAME;
	private static final String PASSWORD = Constants.STORE_PASSWORD;
	private static final String BRANCH_UUID = Constants.STORE_BRANCH_UUID;
	private static final String TEST_JSON_FILE_PATH = Constants.TEST_ORDER_JSON_FILE_PATH;

	public static int orderId;
	public static int invoiceId;
	public static String invoicePrefix;
	public static int deviceId;
	private String token;

//	@BeforeClass
	public void init() {

		RestAssured.baseURI = CommonUtil.getEnvironmentPropertyValue("url");

		// register the device
		RestAssured.basePath = "branch/" + BRANCH_UUID + "/device/register/" + DEVICE_HW_ID + "/" + DEVICE_NAME + "/";
		System.out.println("Base url : " + RestAssured.baseURI);
		System.out.println("Basepath : " + RestAssured.basePath);

		// get the response token
		Response responseFromRegisterDevice = RestAssured.given().auth().basic(USERNAME, PASSWORD).when().get();
		System.out.println("Response status from register device : " + responseFromRegisterDevice.statusCode());
		System.out.println("Response from register device : " + responseFromRegisterDevice.getBody().asString());
		JSONObject responseObject = new JSONObject(responseFromRegisterDevice.getBody().asString());
		token = responseObject.getString("token");
		System.out.println("Authorization token : " + token);

		setDeviceStatus();

	}

//	@BeforeMethod
	public void beforeMethod(Method method) {
		System.out.println("\n================================ Executing test : " + method.getName()
				+ " ================================");
	}

	@Test
	public void testOrder_SingleItem() throws IOException {

		// create invoice with single item
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);

//		JSONObject orderObject = modifyRequestJson();
//		System.out.println(orderObject.getJSONArray("orders").getJSONObject(0).getJSONArray("items"));

	}

	// @Test
	public void testOrder_MultipleItems() throws IOException {

//	   test sample json with multiple items 
		testOrderJson(TEST_JSON_FILE_PATH + "order_multipleitems.txt");

	}

	@Test
	public void testOrderWithCustomerInfo() throws IOException {

		// create invoice with customer info
		String invoiceUUID = UUID.randomUUID().toString();
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);

		// add customer info
		List<CustomerDto> customerList = new ArrayList<>();
		CustomerDto customer = getCustomer();
		customerList.add(customer);

		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, customerList, details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);

		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, invoiceUUID);

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);

		// post the order json
		RestAssured.basePath = "branch/" + BRANCH_UUID + "/orders/";

		// headers for create order
		Map<String, String> headersMap = new HashMap<>();
		headersMap.put("Authorization", "Token " + token);
		headersMap.put("Content-Type", "application/json");

		Response orderResponse = RestAssured.given().headers(headersMap).body(requestJson).when().post();
		System.out.println("Response status of create order API : " + orderResponse.statusCode());
		System.out.println("Response of create order API : " + orderResponse.getBody().asString());

		// hit the invoice API
		RestAssured.basePath = "new/branch/" + BRANCH_UUID + "/order";

		Response invoiceResponse = RestAssured.given().auth().basic(USERNAME, PASSWORD).when().get();
		System.out.println("Response status of get Invoice API : " + invoiceResponse.statusCode());
		System.out.println("Response of invoice API : " + invoiceResponse.getBody().asString());

		// validate the invoice API response
		JSONObject invoiceObject = new JSONArray(invoiceResponse.getBody().asString()).getJSONObject(0);
		JSONObject orderObject = invoiceObject.getJSONArray("orders").getJSONObject(0);
		JSONObject customerObject = orderObject.getJSONArray("customers").getJSONObject(0).getJSONObject("customer");

		// validate customer info
		Assert.assertEquals(customerObject.getString("name"), customer.getCustomer().getName());
		Assert.assertEquals(customerObject.getBoolean("email_enabled"), customer.getCustomer().getName());
		Assert.assertEquals(customerObject.getString("gender"), customer.getCustomer().getGender());
		Assert.assertEquals(customerObject.getBoolean("is_active"), customer.getCustomer().isIs_active());
		Assert.assertEquals(customerObject.getString("gstin"), customer.getCustomer().getGstin());

		// validate contact details
		// validate address
		JSONObject address = customerObject.getJSONObject("contact").getJSONArray("address").getJSONObject(0);
		Assert.assertEquals(address.getString("addr1"),
				customer.getCustomer().getContact().getAddress().get(0).getAddr1());
		Assert.assertEquals(address.getString("addr2"),
				customer.getCustomer().getContact().getAddress().get(0).getAddr2());
		Assert.assertEquals(address.getString("country"),
				customer.getCustomer().getContact().getAddress().get(0).getCountry());
		Assert.assertEquals(address.getString("city"),
				customer.getCustomer().getContact().getAddress().get(0).getCity());
		Assert.assertEquals(address.getBoolean("is_active"),
				customer.getCustomer().getContact().getAddress().get(0).isIs_active());
		Assert.assertEquals(address.getBoolean("is_default"),
				customer.getCustomer().getContact().getAddress().get(0).isIs_default());
		Assert.assertEquals(address.getString("near"),
				customer.getCustomer().getContact().getAddress().get(0).getNear());
		Assert.assertEquals(address.getString("nickname"),
				customer.getCustomer().getContact().getAddress().get(0).getNickname());
		Assert.assertEquals(address.getString("state"),
				customer.getCustomer().getContact().getAddress().get(0).getState());
		Assert.assertEquals(address.getString("street"),
				customer.getCustomer().getContact().getAddress().get(0).getStreet());
		Assert.assertEquals(address.getInt("type"), customer.getCustomer().getContact().getAddress().get(0).getType());
		Assert.assertEquals(address.getString("zip"), customer.getCustomer().getContact().getAddress().get(0).getZip());

		// validate email
		JSONObject email = customerObject.getJSONObject("contact").getJSONArray("email").getJSONObject(0);
		Assert.assertEquals(email.getString("email"), customer.getCustomer().getContact().getEmail().get(0).getEmail());
		Assert.assertEquals(email.getBoolean("is_active"),
				customer.getCustomer().getContact().getEmail().get(0).isIs_active());
		Assert.assertEquals(email.getBoolean("is_default"),
				customer.getCustomer().getContact().getEmail().get(0).isIs_default());
		Assert.assertEquals(email.getInt("type"), customer.getCustomer().getContact().getEmail().get(0).getType());

		// validate phone
		JSONObject phone = customerObject.getJSONObject("contact").getJSONArray("phone").getJSONObject(0);
		Assert.assertEquals(phone.getString("number"),
				customer.getCustomer().getContact().getPhone().get(0).getNumber());
		Assert.assertEquals(phone.getBoolean("is_active"),
				customer.getCustomer().getContact().getPhone().get(0).isIs_active());
		Assert.assertEquals(phone.getBoolean("is_default"),
				customer.getCustomer().getContact().getPhone().get(0).isIs_default());
		Assert.assertEquals(phone.getInt("type"), customer.getCustomer().getContact().getPhone().get(0).getType());

	}

	@Test
	public void testOrder_WithoutTaxDetails() throws JsonGenerationException, JsonMappingException, IOException {

		// create order without tax details
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
		
		// post the order
		RestAssured.basePath = "branch/" + BRANCH_UUID + "/orders/";

		// headers for create order
		Map<String, String> headersMap = new HashMap<>();
		headersMap.put("Authorization", "Token " + token);
		headersMap.put("Content-Type", "application/json");

		Response orderResponse = RestAssured.given().headers(headersMap).body(requestJson).when().post();
		System.out.println("Response status of create order API : " + orderResponse.statusCode());
		System.out.println("Response of create order API : " + orderResponse.getBody().asString());

		// hit the invoice API
		RestAssured.basePath = "new/branch/" + BRANCH_UUID + "/order";
		
		Response invoiceResponse = RestAssured.given().auth().basic(USERNAME, PASSWORD).when().get();
		System.out.println("Response status of get Invoice API : " + invoiceResponse.statusCode());
		System.out.println("Response of invoice API : " + invoiceResponse.getBody().asString());

		// validate the invoice API response
		JSONObject invoiceObject = new JSONArray(invoiceResponse.getBody().asString()).getJSONObject(0);
		JSONObject orderObject = invoiceObject.getJSONArray("orders").getJSONObject(0);
		JSONObject taxDetailObject = orderObject.getJSONObject("details").getJSONArray("taxdetails").getJSONObject(0);
		

		
	}

	@Test
	public void testOrder_With_ExclusiveTax() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with exclusive tax
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_With_InclusiveTax() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with inclusive tax
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_With_Inclusive_And_ExclusiveTax()
			throws JsonGenerationException, JsonMappingException, IOException {

		// create order with inclusive and exclusive tax
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_Without_Charges() throws JsonGenerationException, JsonMappingException, IOException {

		// create order without charges
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_With_Multiple_Charges() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with multiple charges
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_With_ChangeInPaymentMode() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with change in payment mode
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_Multiple_PaymentModes() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with exclusive tax
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	/*
	 * @Test public void testDiscardedOrder_AfterPayment() {
	 * 
	 * // test sample json with discarded order after payment
	 * testOrderJson(TEST_JSON_FILE_PATH + "order_discarded_afterpayment.txt"); }
	 */

	@Test
	public void testOrder_WithFlatDiscount() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with flat discount
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_WithPercentageDiscount() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with percentage discount
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_SingleItem_WithFlatDiscount()
			throws JsonGenerationException, JsonMappingException, IOException {

		// create order with single item having flat discount
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_SingleItem_WithPercentDiscount()
			throws JsonGenerationException, JsonMappingException, IOException {

		// create order with single item having percentage discount
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_MultipleItems_WithFlatDiscount()
			throws JsonGenerationException, JsonMappingException, IOException {

		// create order with multiple items having flat discount
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_MultipleItems_WithPercentageDiscount()
			throws JsonGenerationException, JsonMappingException, IOException {

		// create order with multiple items having percentage discount
		long modifiedat = System.currentTimeMillis();
		long createdat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_With_CashPayment() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with cash as payment mode
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_With_CardPayment() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with card as payment mode
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_With_CouponPayment() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with coupon as payment mode
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_With_CreditPayment() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with credit as payment mode
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_SingleItemRefund() throws JsonGenerationException, JsonMappingException, IOException {

		// create order and do single item refund
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_AllItemRefund() throws JsonGenerationException, JsonMappingException, IOException {

		// create order and do all item refund
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_ItemPriceEdited() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with price of item edited
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_ItemsWithAddons() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with items having addons
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);

	}

	@Test
	public void testOrder_ItemsWithVariants() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with items having addons
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);

	}

	@Test
	public void testOrder_ItemWithProperties() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with items having properties
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);

	}

	@Test
	public void testOrder_Comments_ItemLevel() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with item level comments
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_Comments_OrderLevel() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with order level comments
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrderWithDeletedItems() throws JsonGenerationException, JsonMappingException, IOException {

		// create order with deleted items
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_BalancePaidAsCashToCustomer()
			throws JsonGenerationException, JsonMappingException, IOException {
		// create order with balance paid as cash to customer
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	@Test
	public void testOrder_BalancePaidAsCreditToCustomer()
			throws JsonGenerationException, JsonMappingException, IOException {

		// create order with balance paid as credit to customer
		long createdat = System.currentTimeMillis();
		long modifiedat = System.currentTimeMillis();
		List<Order> orders = new ArrayList<>();
		List<TaxDetails> taxDetails = new ArrayList<>();
		TaxDetails taxDetail = new TaxDetails(new ArrayList<>(), "Tax0", null);
		taxDetails.add(taxDetail);
		OrderDetails details = new OrderDetails(new ArrayList<>(), new ArrayList<>(), taxDetails);
		List<Employee> employees = new ArrayList<>();
		Employee employee = getEmployee("APIAutomation", 0, UUID.randomUUID().toString());
		employees.add(employee);
		List<Payment> payments = new ArrayList<>();
		Payment payment = getPayment(53000, 10000, System.currentTimeMillis(), deviceId, 1, 1,
				System.currentTimeMillis(), 4, 1, UUID.randomUUID().toString());
		payments.add(payment);
		List<Item> items = new ArrayList<>();
		Price price = new Price(Constants.CHANNEL_UUID, 52989, 53000, 0, 53000, null, 7600);
		Item item = getItem(new ArrayList<>(), new ArrayList<>(), "TestItem1", new ArrayList<>(), 1, 5,
				new ArrayList<>(), price, "TST0001", "Test Item 1", new ArrayList<>(), 1000, "004UEFXILSA00",
				new ArrayList<>(), new ArrayList<>(), "Test Item 1");
		items.add(item);
		OrderSummary orderSummary = getOrderSummary(new ArrayList<>(), null, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), 53048, 1000, 0, 53000, new ArrayList<>(), 0);
		Order order = getOrder(null, new ArrayList<>(), new ArrayList<>(), createdat, new ArrayList<>(), details,
				new ArrayList<>(), employees, items, new ArrayList<>(), modifiedat, orderId, deviceId, payments,
				new ArrayList<>(), Constants.ORDER_STATUS, orderSummary, new ArrayList<>(), Constants.ORDER_TYPE,
				UUID.randomUUID().toString());
		orders.add(order);
		InvoiceSummary invoiceSummary = getInvoiceSummary(53048, 1, 0, 53000, 0, 0, 48);
		Invoice invoice = getInvoice(createdat, invoiceId, modifiedat, orders, invoicePrefix, invoiceSummary,
				Constants.INVOICE_TYPE, UUID.randomUUID().toString());

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(invoice);
		System.out.println("Invoice : " + requestJson);
	}

	private void testOrderJson(String jsonFileName) {

		String sampleJson;
		try {
			// get sample json
			sampleJson = CommonUtil.getJsonFromFile(jsonFileName);
			JSONObject orderObject = new JSONObject(sampleJson);

			// modify invoice uuid
			orderObject.put("uuid", UUID.randomUUID().toString());

			// modify invoice id
			orderObject.put("invoiceId", ++invoiceId);

			// modify prefix
			orderObject.put("prefix", invoicePrefix);

			// modify order uuid
			JSONObject order = orderObject.getJSONArray("orders").getJSONObject(0);
			order.put("uuid", UUID.randomUUID().toString());

			// modify order id
			order.put("orderid", ++orderId);

			// modify device id
			order.put("device_id", deviceId);
			order.put("owner_device_id", deviceId);
			JSONArray payments = order.getJSONArray("payments");
			for (int i = 0; i < payments.length(); i++) {
				payments.getJSONObject(i).put("device_id", deviceId);
			}
			JSONArray kots = order.getJSONArray("kots");
			for (int i = 0; i < kots.length(); i++) {
				kots.getJSONObject(i).put("kot_device_id", deviceId);
			}

			System.out.println("Json after modifying uuid : " + orderObject.toString());

			// post the order json
			RestAssured.basePath = "branch/" + BRANCH_UUID + "/orders/";

			// headers for create order
			Map<String, String> headersMap = new HashMap<>();
			headersMap.put("Authorization", "Token " + token);
			headersMap.put("Content-Type", "application/json");

			Response response = RestAssured.given().headers(headersMap).body(orderObject.toString()).when().post();
			System.out.println("Response status of create order API : " + response.statusCode());
			System.out.println("Response of create order API : " + response.getBody().asString());

			// get the order json using uuid
			RestAssured.basePath = "";
			Response expectedResponse = RestAssured.given().when().get();
			String expectedJson = expectedResponse.getBody().asString();
			System.out.println("Expected response json of the order : " + expectedJson);

			// compare the 2 jsons
//			JsonAssert.assertJsonEquals(expectedJson, actualJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setDeviceStatus() {

		RestAssured.basePath = "branch/" + BRANCH_UUID + "/devices/";
		Response response = RestAssured.given().auth().basic(USERNAME, PASSWORD).when().get();
		System.out.println("Response from getDevices : " + response.getBody().asString());
		System.out.println("Response status from getDevices : " + response.statusCode());
		JSONArray deviceArray = new JSONArray(response.getBody().asString());
		for (int i = 0; i < deviceArray.length(); i++) {
			if (deviceArray.getJSONObject(i).getString("device_hw_id").equalsIgnoreCase(DEVICE_HW_ID)) {
				JSONObject deviceObject = deviceArray.getJSONObject(i);
				orderId = deviceObject.getInt("order_id");
				invoiceId = deviceObject.getInt("invoice_id");
				invoicePrefix = deviceObject.getString("invoice_prefix");
				deviceId = deviceObject.getInt("device_id");
			}
		}

		System.out.println("orderId : " + orderId);
		System.out.println("invoiceId : " + invoiceId);
		System.out.println("invoicePrefix : " + invoicePrefix);
		System.out.println("deviceId : " + deviceId);
	}

	private JSONObject modifyRequestJson() {

		String sampleJson;
		JSONObject orderObject;

		// get sample json
		try {
			sampleJson = CommonUtil.getJsonFromFile(TEST_JSON_FILE_PATH + "samplejson.txt");
			orderObject = new JSONObject(sampleJson);

			// modify invoice uuid
			orderObject.put("uuid", UUID.randomUUID().toString());

			// modify invoice id
			orderObject.put("invoiceId", ++invoiceId);

			// modify prefix
			orderObject.put("prefix", invoicePrefix);

			// modify order uuid
			JSONObject order = orderObject.getJSONArray("orders").getJSONObject(0);
			order.put("uuid", UUID.randomUUID().toString());

			// modify order id
			order.put("orderid", ++orderId);

			// modify device id
			order.put("device_id", deviceId);
			order.put("owner_device_id", deviceId);
			JSONArray payments = order.getJSONArray("payments");
			for (int i = 0; i < payments.length(); i++) {
				payments.getJSONObject(i).put("device_id", deviceId);
			}
			JSONArray kots = order.getJSONArray("kots");
			for (int i = 0; i < kots.length(); i++) {
				kots.getJSONObject(i).put("kot_device_id", deviceId);
			}

			System.out.println("Json after modifying uuid : " + orderObject.toString());

			return orderObject;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Invoice getInvoice(long createdat, int invoiceId, long modifiedat, List<Order> orders, String prefix,
			InvoiceSummary summary, int type, String uuid) {

		Invoice invoice = new Invoice(createdat, invoiceId, modifiedat, orders, prefix, summary, type, uuid);
		return invoice;

	}

	private InvoiceSummary getInvoiceSummary(double grand_total, int order_count, double payment_balance,
			double sub_total, double total_charge, double total_dis, double total_tax) {

		InvoiceSummary invoiceSummary = new InvoiceSummary(grand_total, order_count, payment_balance, sub_total,
				total_charge, total_dis, total_tax);
		return invoiceSummary;
	}

	private Order getOrder(String charge_uuid, List<ChargeSplit> chargesplit, List<Comment> comments, long createdat,
			List<CustomerDto> customers, OrderDetails details, List<Discount> discounts, List<Employee> employees,
			List<Item> items, List<Kot> kots, long modifiedat, int orderid, long owner_device_id,
			List<Payment> payments, List<Schedule> schedules, int status, OrderSummary summary, List<Table> tables,
			int type, String uuid) {

		Order order = new Order(charge_uuid, chargesplit, comments, createdat, customers, details, discounts, employees,
				items, kots, modifiedat, orderid, owner_device_id, payments, schedules, status, summary, tables, type,
				uuid);
		return order;
	}

	private Payment getPayment(long amount, double conversion_factor, long createdat, long device_id, int method,
			int mode, long modifiedat, int status, int type, String uuid) {

		Payment payment = new Payment(amount, conversion_factor, createdat, device_id, method, mode, modifiedat, status,
				type, uuid);
		return payment;
	}

	private CustomerDto getCustomer() {
		List<Address> addressList = new ArrayList<>();
		Address address = new Address();
		addressList.add(address);

		List<Email> emailList = new ArrayList<>();
		Email email = new Email();
		emailList.add(email);

		List<Phone> phoneList = new ArrayList<>();
		Phone phone = new Phone();
		phoneList.add(phone);

		Contact contact = new Contact(addressList, emailList, phoneList);
		Customer customer = new Customer(contact, null, null, null);
		CustomerDto customerDto = new CustomerDto(customer);
		return customerDto;
	}

	private Employee getEmployee(String name, int rank, String uuid) {

		Employee employee = new Employee(name, rank, uuid);
		return employee;
	}

	private OrderSummary getOrderSummary(List<ChargeSplit> chargesplit, List<ChargeTaxSplit> chargetaxsplit,
			List<DiscountSplit> discountsplit, List<ExclusiveChargeSplit> exclusiveChargesplit,
			List<ExclusiveTaxSplit> exclusiveTaxsplit, double grand_total, int number_of_items, int pending_items,
			double sub_total, List<TaxSplit> taxsplit, double total_tax) {

		OrderSummary orderSummary = new OrderSummary(chargesplit, chargetaxsplit, discountsplit, exclusiveChargesplit,
				exclusiveTaxsplit, grand_total, number_of_items, pending_items, sub_total, taxsplit, total_tax);
		return orderSummary;
	}

	private Item getItem(List<ChargeSplit> chargesplit, List<String> comments, String description,
			List<String> ingredients, int line_item_id, int metric, List<String> modifiers, Price price,
			String product_code, String product_name, List<Properties> properties, int qty, String sku,
			List<TaxSplit> taxsplit, List<UserProperties> user_properties, String variant_name) {

		Item item = new Item(chargesplit, comments, description, ingredients, line_item_id, metric, modifiers, price,
				product_code, product_name, properties, qty, sku, taxsplit, user_properties, variant_name);
		return item;
	}

	@AfterMethod
	public void afterMethod() {
		System.out.println(
				"=================================================================================================================================\n");
	}

}
