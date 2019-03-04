package com.paytm.smartretail_api_automation.dto.orderapi;

import com.paytm.smartretail_api_automation.utilities.Constants;

public class Payment {

	private long amount;
	private String card_num = "";
	private String checkout_uuid = null;
	private double conversion_factor;
	private int coupon_type = 0;
	private long createdat;
	private String currency = "INR";
	private long device_id = Constants.DEVICE_ID;
	private String edited_emp_uuid = null;
	private String email = null;
	private int method;
	private int mode;
	private long modifiedat;
	private double parent_amount = 0;
	private String parent_uuid = null;
	private String payment_init_uuid = null;
	private boolean payment_system_connected = false;
	private String phone_number = null;
	private String ref_id = "";
	private String reference_id = null;
	private int status;
	private String trnx_reference = null;
	private String trnx_uuid = null;
	private int type;
	private String uuid;

	public Payment(long amount, double conversion_factor, long createdat, int method, int mode,
			long modifiedat, int status, int type, String uuid) {
		super();
		this.amount = amount;
		this.conversion_factor = conversion_factor;
		this.createdat = createdat;
		this.method = method;
		this.mode = mode;
		this.modifiedat = modifiedat;
		this.status = status;
		this.type = type;
		this.uuid = uuid;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getCard_num() {
		return card_num;
	}

	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}

	public String getCheckout_uuid() {
		return checkout_uuid;
	}

	public void setCheckout_uuid(String checkout_uuid) {
		this.checkout_uuid = checkout_uuid;
	}

	public double getConversion_factor() {
		return conversion_factor;
	}

	public void setConversion_factor(double conversion_factor) {
		this.conversion_factor = conversion_factor;
	}

	public int getCoupon_type() {
		return coupon_type;
	}

	public void setCoupon_type(int coupon_type) {
		this.coupon_type = coupon_type;
	}

	public long getCreatedat() {
		return createdat;
	}

	public void setCreatedat(long createdat) {
		this.createdat = createdat;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public long getDevice_id() {
		return device_id;
	}

	public void setDevice_id(long device_id) {
		this.device_id = device_id;
	}

	public String getEdited_emp_uuid() {
		return edited_emp_uuid;
	}

	public void setEdited_emp_uuid(String edited_emp_uuid) {
		this.edited_emp_uuid = edited_emp_uuid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public long getModifiedat() {
		return modifiedat;
	}

	public void setModifiedat(long modifiedat) {
		this.modifiedat = modifiedat;
	}

	public double getParent_amount() {
		return parent_amount;
	}

	public void setParent_amount(double parent_amount) {
		this.parent_amount = parent_amount;
	}

	public String getParent_uuid() {
		return parent_uuid;
	}

	public void setParent_uuid(String parent_uuid) {
		this.parent_uuid = parent_uuid;
	}

	public String getPayment_init_uuid() {
		return payment_init_uuid;
	}

	public void setPayment_init_uuid(String payment_init_uuid) {
		this.payment_init_uuid = payment_init_uuid;
	}

	public boolean isPayment_system_connected() {
		return payment_system_connected;
	}

	public void setPayment_system_connected(boolean payment_system_connected) {
		this.payment_system_connected = payment_system_connected;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getRef_id() {
		return ref_id;
	}

	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}

	public String getReference_id() {
		return reference_id;
	}

	public void setReference_id(String reference_id) {
		this.reference_id = reference_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTrnx_reference() {
		return trnx_reference;
	}

	public void setTrnx_reference(String trnx_reference) {
		this.trnx_reference = trnx_reference;
	}

	public String getTrnx_uuid() {
		return trnx_uuid;
	}

	public void setTrnx_uuid(String trnx_uuid) {
		this.trnx_uuid = trnx_uuid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
