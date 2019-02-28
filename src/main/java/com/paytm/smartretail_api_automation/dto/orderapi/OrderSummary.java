package com.paytm.smartretail_api_automation.dto.orderapi;

import java.util.List;

public class OrderSummary {

	private List<ChargeSplit> chargesplit;
	private List<ChargeTaxSplit> chargetaxsplit;
	private String comment = null;
	private List<DiscountSplit> discountsplit;
	private List<ExclusiveChargeSplit> exclusiveChargesplit;
	private List<ExclusiveTaxSplit> exclusiveTaxsplit;
	private double grand_total;
	private int number_of_items;
	private int pending_items;
	private double sub_total;
	private List<TaxSplit> taxsplit;
	private double total_charges = 0;
	private double total_discounts = 0;
	private double total_exclu_discounts = 0;
	private double total_order_charge_n_tax = 0;
	private double total_order_exclu_charge = 0;
	private double total_tax;

	public OrderSummary(List<ChargeSplit> chargesplit, List<ChargeTaxSplit> chargetaxsplit,
			List<DiscountSplit> discountsplit, List<ExclusiveChargeSplit> exclusiveChargesplit,
			List<ExclusiveTaxSplit> exclusiveTaxsplit, double grand_total, int number_of_items, int pending_items,
			double sub_total, List<TaxSplit> taxsplit, double total_tax) {
		super();
		this.chargesplit = chargesplit;
		this.chargetaxsplit = chargetaxsplit;
		this.discountsplit = discountsplit;
		this.exclusiveChargesplit = exclusiveChargesplit;
		this.exclusiveTaxsplit = exclusiveTaxsplit;
		this.grand_total = grand_total;
		this.number_of_items = number_of_items;
		this.pending_items = pending_items;
		this.sub_total = sub_total;
		this.taxsplit = taxsplit;
		this.total_tax = total_tax;
	}

	public List<ChargeSplit> getChargesplit() {
		return chargesplit;
	}

	public void setChargesplit(List<ChargeSplit> chargesplit) {
		this.chargesplit = chargesplit;
	}

	public List<ChargeTaxSplit> getChargetaxsplit() {
		return chargetaxsplit;
	}

	public void setChargetaxsplit(List<ChargeTaxSplit> chargetaxsplit) {
		this.chargetaxsplit = chargetaxsplit;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<DiscountSplit> getDiscountsplit() {
		return discountsplit;
	}

	public void setDiscountsplit(List<DiscountSplit> discountsplit) {
		this.discountsplit = discountsplit;
	}

	public List<ExclusiveChargeSplit> getExclusiveChargesplit() {
		return exclusiveChargesplit;
	}

	public void setExclusiveChargesplit(List<ExclusiveChargeSplit> exclusiveChargesplit) {
		this.exclusiveChargesplit = exclusiveChargesplit;
	}

	public List<ExclusiveTaxSplit> getExclusiveTaxsplit() {
		return exclusiveTaxsplit;
	}

	public void setExclusiveTaxsplit(List<ExclusiveTaxSplit> exclusiveTaxsplit) {
		this.exclusiveTaxsplit = exclusiveTaxsplit;
	}

	public double getGrand_total() {
		return grand_total;
	}

	public void setGrand_total(double grand_total) {
		this.grand_total = grand_total;
	}

	public int getNumber_of_items() {
		return number_of_items;
	}

	public void setNumber_of_items(int number_of_items) {
		this.number_of_items = number_of_items;
	}

	public int getPending_items() {
		return pending_items;
	}

	public void setPending_items(int pending_items) {
		this.pending_items = pending_items;
	}

	public double getSub_total() {
		return sub_total;
	}

	public void setSub_total(double sub_total) {
		this.sub_total = sub_total;
	}

	public List<TaxSplit> getTaxsplit() {
		return taxsplit;
	}

	public void setTaxsplit(List<TaxSplit> taxsplit) {
		this.taxsplit = taxsplit;
	}

	public double getTotal_charges() {
		return total_charges;
	}

	public void setTotal_charges(double total_charges) {
		this.total_charges = total_charges;
	}

	public double getTotal_discounts() {
		return total_discounts;
	}

	public void setTotal_discounts(double total_discounts) {
		this.total_discounts = total_discounts;
	}

	public double getTotal_exclu_discounts() {
		return total_exclu_discounts;
	}

	public void setTotal_exclu_discounts(double total_exclu_discounts) {
		this.total_exclu_discounts = total_exclu_discounts;
	}

	public double getTotal_order_charge_n_tax() {
		return total_order_charge_n_tax;
	}

	public void setTotal_order_charge_n_tax(double total_order_charge_n_tax) {
		this.total_order_charge_n_tax = total_order_charge_n_tax;
	}

	public double getTotal_order_exclu_charge() {
		return total_order_exclu_charge;
	}

	public void setTotal_order_exclu_charge(double total_order_exclu_charge) {
		this.total_order_exclu_charge = total_order_exclu_charge;
	}

	public double getTotal_tax() {
		return total_tax;
	}

	public void setTotal_tax(double total_tax) {
		this.total_tax = total_tax;
	}

}
