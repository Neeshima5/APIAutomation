package com.paytm.smartretail_api_automation.dto.storecreation;

import java.util.List;

public class BranchDto {

	private String paytm_mall_dummy_pid = "";
	private String paytm_mall_warehouse_id = "";
	private AddressDto address;
	private String timezone = "Asia/Kolkata";
	private List<String> phone_numbers;
	private String type;
	private String gst;
	private String name;
	private String paytm_merchant_id = "";
	private String paytm_mall_merchant_id = "";
	private String invoice_header_line_1 = "Welcome!";
	private String invoice_footer_line_1 = "Th@nkYou !!!";
	private String affiliate;

	public BranchDto() {

	}

	public BranchDto(String paytm_mall_dummy_pid, String paytm_mall_warehouse_id, AddressDto address, String timezone,
			List<String> phone_numbers, String type, String gst, String name, String paytm_merchant_id,
			String paytm_mall_merchant_id, String affiliate) {
		super();
		this.paytm_mall_dummy_pid = paytm_mall_dummy_pid;
		this.paytm_mall_warehouse_id = paytm_mall_warehouse_id;
		this.address = address;
		this.timezone = timezone;
		this.phone_numbers = phone_numbers;
		this.type = type;
		this.gst = gst;
		this.name = name;
		this.paytm_merchant_id = paytm_merchant_id;
		this.paytm_mall_merchant_id = paytm_mall_merchant_id;
		this.affiliate = affiliate;
	}

	public BranchDto(String paytm_mall_dummy_pid, String paytm_mall_warehouse_id, AddressDto address, String timezone,
			List<String> phone_numbers, String type, String gst, String name, String paytm_merchant_id,
			String paytm_mall_merchant_id, String invoice_header_line_1, String invoice_footer_line_1,
			String affiliate) {
		super();
		this.paytm_mall_dummy_pid = paytm_mall_dummy_pid;
		this.paytm_mall_warehouse_id = paytm_mall_warehouse_id;
		this.address = address;
		this.timezone = timezone;
		this.phone_numbers = phone_numbers;
		this.type = type;
		this.gst = gst;
		this.name = name;
		this.paytm_merchant_id = paytm_merchant_id;
		this.paytm_mall_merchant_id = paytm_mall_merchant_id;
		this.invoice_header_line_1 = invoice_header_line_1;
		this.invoice_footer_line_1 = invoice_footer_line_1;
		this.affiliate = affiliate;
	}

	public String getPaytm_mall_dummy_pid() {
		return paytm_mall_dummy_pid;
	}

	public void setPaytm_mall_dummy_pid(String paytm_mall_dummy_pid) {
		this.paytm_mall_dummy_pid = paytm_mall_dummy_pid;
	}

	public String getPaytm_mall_warehouse_id() {
		return paytm_mall_warehouse_id;
	}

	public void setPaytm_mall_warehouse_id(String paytm_mall_warehouse_id) {
		this.paytm_mall_warehouse_id = paytm_mall_warehouse_id;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public List<String> getPhone_numbers() {
		return phone_numbers;
	}

	public void setPhone_numbers(List<String> phone_numbers) {
		this.phone_numbers = phone_numbers;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPaytm_merchant_id() {
		return paytm_merchant_id;
	}

	public void setPaytm_merchant_id(String paytm_merchant_id) {
		this.paytm_merchant_id = paytm_merchant_id;
	}

	public String getPaytm_mall_merchant_id() {
		return paytm_mall_merchant_id;
	}

	public void setPaytm_mall_merchant_id(String paytm_mall_merchant_id) {
		this.paytm_mall_merchant_id = paytm_mall_merchant_id;
	}

	public String getInvoice_header_line_1() {
		return invoice_header_line_1;
	}

	public void setInvoice_header_line_1(String invoice_header_line_1) {
		this.invoice_header_line_1 = invoice_header_line_1;
	}

	public String getInvoice_footer_line_1() {
		return invoice_footer_line_1;
	}

	public void setInvoice_footer_line_1(String invoice_footer_line_1) {
		this.invoice_footer_line_1 = invoice_footer_line_1;
	}

	public String getAffiliate() {
		return affiliate;
	}

	public void setAffiliate(String affiliate) {
		this.affiliate = affiliate;
	}

}
