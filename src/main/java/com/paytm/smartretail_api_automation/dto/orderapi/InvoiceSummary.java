package com.paytm.smartretail_api_automation.dto.orderapi;

public class InvoiceSummary {

	private double grand_total;
	private boolean is_edited = false;
	private boolean is_refunded = false;
	private int order_count;
	private double payment_balance;
	private double sub_total;
	private double total_charge;
	private double total_dis;
	private double total_tax;

	public InvoiceSummary(double grand_total, int order_count, double payment_balance, double sub_total,
			double total_charge, double total_dis, double total_tax) {
		super();
		this.grand_total = grand_total;
		this.order_count = order_count;
		this.payment_balance = payment_balance;
		this.sub_total = sub_total;
		this.total_charge = total_charge;
		this.total_dis = total_dis;
		this.total_tax = total_tax;
	}

	public double getGrand_total() {
		return grand_total;
	}

	public void setGrand_total(double grand_total) {
		this.grand_total = grand_total;
	}

	public boolean isIs_edited() {
		return is_edited;
	}

	public void setIs_edited(boolean is_edited) {
		this.is_edited = is_edited;
	}

	public boolean isIs_refunded() {
		return is_refunded;
	}

	public void setIs_refunded(boolean is_refunded) {
		this.is_refunded = is_refunded;
	}

	public int getOrder_count() {
		return order_count;
	}

	public void setOrder_count(int order_count) {
		this.order_count = order_count;
	}

	public double getPayment_balance() {
		return payment_balance;
	}

	public void setPayment_balance(double payment_balance) {
		this.payment_balance = payment_balance;
	}

	public double getSub_total() {
		return sub_total;
	}

	public void setSub_total(double sub_total) {
		this.sub_total = sub_total;
	}

	public double getTotal_charge() {
		return total_charge;
	}

	public void setTotal_charge(double total_charge) {
		this.total_charge = total_charge;
	}

	public double getTotal_dis() {
		return total_dis;
	}

	public void setTotal_dis(double total_dis) {
		this.total_dis = total_dis;
	}

	public double getTotal_tax() {
		return total_tax;
	}

	public void setTotal_tax(double total_tax) {
		this.total_tax = total_tax;
	}

}
