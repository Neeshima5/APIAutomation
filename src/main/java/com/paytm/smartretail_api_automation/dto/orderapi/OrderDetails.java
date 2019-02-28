package com.paytm.smartretail_api_automation.dto.orderapi;

import java.util.List;

public class OrderDetails {

	private List<ChargeDetails> chargedetails;
	private List<RuleDetails> ruledetails;
	private List<TaxDetails> taxdetails;

	public OrderDetails(List<ChargeDetails> chargedetails, List<RuleDetails> ruledetails, List<TaxDetails> taxdetails) {
		super();
		this.chargedetails = chargedetails;
		this.ruledetails = ruledetails;
		this.taxdetails = taxdetails;
	}

	public List<ChargeDetails> getChargedetails() {
		return chargedetails;
	}

	public void setChargedetails(List<ChargeDetails> chargedetails) {
		this.chargedetails = chargedetails;
	}

	public List<RuleDetails> getRuledetails() {
		return ruledetails;
	}

	public void setRuledetails(List<RuleDetails> ruledetails) {
		this.ruledetails = ruledetails;
	}

	public List<TaxDetails> getTaxdetails() {
		return taxdetails;
	}

	public void setTaxdetails(List<TaxDetails> taxdetails) {
		this.taxdetails = taxdetails;
	}

}
