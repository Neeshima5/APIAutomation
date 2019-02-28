package com.paytm.smartretail_api_automation.dto.orderapi;

import java.util.ArrayList;
import java.util.List;

import com.paytm.smartretail_api_automation.utilities.Constants;

public class Order {

	private String channel_uuid = Constants.CHANNEL_UUID;
	private String charge_uuid;
	private List<ChargeSplit> chargesplit = new ArrayList<>();
	private List<Comment> comments = new ArrayList<Comment>();
	private long createdat;
	private String currency = "INR";
	private List<CustomerDto> customers = new ArrayList<>();
	private OrderDetails details;
	private long device_id = Constants.DEVICE_ID;
	private int discard_reason = 0;
	private List<Discount> discounts = new ArrayList<>();
	private List<Employee> employees;
	private boolean inter_state = false;
	private List<Item> items;
	private List<Kot> kots;
	private String loyalty_partner_uuid = null;
	private long modifiedat;
	private boolean no_service_charge = false;
	private int online_transaction_id = 0;
	private int orderid;
	private String otp = null;
	private long owner_device_id;
	private String parent_uuid = null;
	private String partner_checkout = null;
	private String partner_ref_number = null;
	private String partner_uuid = null;
	private List<Payment> payments;
	private int printcount = 0;
	private int refund_reason = 0;
	private List<Schedule> schedules = new ArrayList<>();
	private String splitspec = null;
	private int status;
	private OrderSummary summary;
	private List<Table> tables = new ArrayList<>();
	private int token_number = 0;
	private int type;
	private String uuid;

	public Order(String charge_uuid, List<ChargeSplit> chargesplit, List<Comment> comments, long createdat,
			List<CustomerDto> customers, OrderDetails details, List<Discount> discounts, List<Employee> employees,
			List<Item> items, List<Kot> kots, long modifiedat, int orderid, long owner_device_id,
			List<Payment> payments, List<Schedule> schedules, int status, OrderSummary summary, List<Table> tables,
			int type, String uuid) {
		super();
		this.charge_uuid = charge_uuid;
		this.chargesplit = chargesplit;
		this.comments = comments;
		this.createdat = createdat;
		this.customers = customers;
		this.details = details;
		this.discounts = discounts;
		this.employees = employees;
		this.items = items;
		this.kots = kots;
		this.modifiedat = modifiedat;
		this.orderid = orderid;
		this.owner_device_id = owner_device_id;
		this.payments = payments;
		this.schedules = schedules;
		this.status = status;
		this.summary = summary;
		this.tables = tables;
		this.type = type;
		this.uuid = uuid;
	}

	public String getChannel_uuid() {
		return channel_uuid;
	}

	public void setChannel_uuid(String channel_uuid) {
		this.channel_uuid = channel_uuid;
	}

	public String getCharge_uuid() {
		return charge_uuid;
	}

	public void setCharge_uuid(String charge_uuid) {
		this.charge_uuid = charge_uuid;
	}

	public List<ChargeSplit> getChargesplit() {
		return chargesplit;
	}

	public void setChargesplit(List<ChargeSplit> chargesplit) {
		this.chargesplit = chargesplit;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
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

	public List<CustomerDto> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerDto> customers) {
		this.customers = customers;
	}

	public OrderDetails getDetails() {
		return details;
	}

	public void setDetails(OrderDetails details) {
		this.details = details;
	}

	public long getDevice_id() {
		return device_id;
	}

	public void setDevice_id(long device_id) {
		this.device_id = device_id;
	}

	public int getDiscard_reason() {
		return discard_reason;
	}

	public void setDiscard_reason(int discard_reason) {
		this.discard_reason = discard_reason;
	}

	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public boolean isInter_state() {
		return inter_state;
	}

	public void setInter_state(boolean inter_state) {
		this.inter_state = inter_state;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Kot> getKots() {
		return kots;
	}

	public void setKots(List<Kot> kots) {
		this.kots = kots;
	}

	public String getLoyalty_partner_uuid() {
		return loyalty_partner_uuid;
	}

	public void setLoyalty_partner_uuid(String loyalty_partner_uuid) {
		this.loyalty_partner_uuid = loyalty_partner_uuid;
	}

	public long getModifiedat() {
		return modifiedat;
	}

	public void setModifiedat(long modifiedat) {
		this.modifiedat = modifiedat;
	}

	public boolean isNo_service_charge() {
		return no_service_charge;
	}

	public void setNo_service_charge(boolean no_service_charge) {
		this.no_service_charge = no_service_charge;
	}

	public int getOnline_transaction_id() {
		return online_transaction_id;
	}

	public void setOnline_transaction_id(int online_transaction_id) {
		this.online_transaction_id = online_transaction_id;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public long getOwner_device_id() {
		return owner_device_id;
	}

	public void setOwner_device_id(long owner_device_id) {
		this.owner_device_id = owner_device_id;
	}

	public String getParent_uuid() {
		return parent_uuid;
	}

	public void setParent_uuid(String parent_uuid) {
		this.parent_uuid = parent_uuid;
	}

	public String getPartner_checkout() {
		return partner_checkout;
	}

	public void setPartner_checkout(String partner_checkout) {
		this.partner_checkout = partner_checkout;
	}

	public String getPartner_ref_number() {
		return partner_ref_number;
	}

	public void setPartner_ref_number(String partner_ref_number) {
		this.partner_ref_number = partner_ref_number;
	}

	public String getPartner_uuid() {
		return partner_uuid;
	}

	public void setPartner_uuid(String partner_uuid) {
		this.partner_uuid = partner_uuid;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public int getPrintcount() {
		return printcount;
	}

	public void setPrintcount(int printcount) {
		this.printcount = printcount;
	}

	public int getRefund_reason() {
		return refund_reason;
	}

	public void setRefund_reason(int refund_reason) {
		this.refund_reason = refund_reason;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public String getSplitspec() {
		return splitspec;
	}

	public void setSplitspec(String splitspec) {
		this.splitspec = splitspec;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public OrderSummary getSummary() {
		return summary;
	}

	public void setSummary(OrderSummary summary) {
		this.summary = summary;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public int getToken_number() {
		return token_number;
	}

	public void setToken_number(int token_number) {
		this.token_number = token_number;
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
