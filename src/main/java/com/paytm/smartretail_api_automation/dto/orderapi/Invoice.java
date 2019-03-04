package com.paytm.smartretail_api_automation.dto.orderapi;

import java.util.ArrayList;
import java.util.List;

public class Invoice {

	private long createdat;
	private int invoiceId;
	private long modifiedat;
	private List<Order> orders = new ArrayList<>();
	private String prefix;
	private InvoiceSummary summary;
	private int type;
	private String uuid;

	public Invoice(long createdat, int invoiceId, long modifiedat, List<Order> orders, String prefix,
			InvoiceSummary summary, int type, String uuid) {
		super();
		this.createdat = createdat;
		this.invoiceId = invoiceId;
		this.modifiedat = modifiedat;
		this.orders = orders;
		this.prefix = prefix;
		this.summary = summary;
		this.type = type;
		this.uuid = uuid;
	}

	public long getCreatedat() {
		return createdat;
	}

	public void setCreatedat(long createdat) {
		this.createdat = createdat;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public long getModifiedat() {
		return modifiedat;
	}

	public void setModifiedat(long modifiedat) {
		this.modifiedat = modifiedat;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public InvoiceSummary getSummary() {
		return summary;
	}

	public void setSummary(InvoiceSummary summary) {
		this.summary = summary;
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

	@Override
	public String toString() {
		return "Invoice [createdat=" + createdat + ", invoiceId=" + invoiceId + ", modifiedat=" + modifiedat
				+ ", orders=" + orders + ", prefix=" + prefix + ", summary=" + summary + ", type=" + type + ", uuid="
				+ uuid + "]";
	}
	

}
