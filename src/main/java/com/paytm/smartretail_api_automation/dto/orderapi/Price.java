package com.paytm.smartretail_api_automation.dto.orderapi;

public class Price {

	private int addonsprice = 0;
	private String channel_uuid;
	private String chargeobject_uuid = null;
	private int dis_percent = 0;
	private int editedsellingprice = 0;
	private int excl_dis = 0;
	private int excl_discounted_price_per_qty;
	private int excl_item_dis_amt = 0;
	private int excl_price_per_qty;
	private int excl_share_dis_amt = 0;
	private int exclusive_selling_price = 0;
	private int inc_item_dis_amt = 0;
	private int inc_price_per_qty;
	private int inc_share_dis_amt = 0;
	private boolean isEdited = false;
	private int mrp;
	private int sellingprice;
	private String taxobject_uuid;
	private int total_excl_charge_per_qty = 0;
	private int totalprice;

	public Price(String channel_uuid, int excl_discounted_price_per_qty, int inc_price_per_qty, int mrp,
			int sellingprice, String taxobject_uuid, int totalprice) {
		super();
		this.channel_uuid = channel_uuid;
		this.excl_discounted_price_per_qty = excl_discounted_price_per_qty;
		this.inc_price_per_qty = inc_price_per_qty;
		this.mrp = mrp;
		this.sellingprice = sellingprice;
		this.taxobject_uuid = taxobject_uuid;
		this.totalprice = totalprice;
	}

	public int getAddonsprice() {
		return addonsprice;
	}

	public void setAddonsprice(int addonsprice) {
		this.addonsprice = addonsprice;
	}

	public String getChannel_uuid() {
		return channel_uuid;
	}

	public void setChannel_uuid(String channel_uuid) {
		this.channel_uuid = channel_uuid;
	}

	public String getChargeobject_uuid() {
		return chargeobject_uuid;
	}

	public void setChargeobject_uuid(String chargeobject_uuid) {
		this.chargeobject_uuid = chargeobject_uuid;
	}

	public int getDis_percent() {
		return dis_percent;
	}

	public void setDis_percent(int dis_percent) {
		this.dis_percent = dis_percent;
	}

	public int getEditedsellingprice() {
		return editedsellingprice;
	}

	public void setEditedsellingprice(int editedsellingprice) {
		this.editedsellingprice = editedsellingprice;
	}

	public int getExcl_dis() {
		return excl_dis;
	}

	public void setExcl_dis(int excl_dis) {
		this.excl_dis = excl_dis;
	}

	public int getExcl_discounted_price_per_qty() {
		return excl_discounted_price_per_qty;
	}

	public void setExcl_discounted_price_per_qty(int excl_discounted_price_per_qty) {
		this.excl_discounted_price_per_qty = excl_discounted_price_per_qty;
	}

	public int getExcl_item_dis_amt() {
		return excl_item_dis_amt;
	}

	public void setExcl_item_dis_amt(int excl_item_dis_amt) {
		this.excl_item_dis_amt = excl_item_dis_amt;
	}

	public int getExcl_price_per_qty() {
		return excl_price_per_qty;
	}

	public void setExcl_price_per_qty(int excl_price_per_qty) {
		this.excl_price_per_qty = excl_price_per_qty;
	}

	public int getExcl_share_dis_amt() {
		return excl_share_dis_amt;
	}

	public void setExcl_share_dis_amt(int excl_share_dis_amt) {
		this.excl_share_dis_amt = excl_share_dis_amt;
	}

	public int getExclusive_selling_price() {
		return exclusive_selling_price;
	}

	public void setExclusive_selling_price(int exclusive_selling_price) {
		this.exclusive_selling_price = exclusive_selling_price;
	}

	public int getInc_item_dis_amt() {
		return inc_item_dis_amt;
	}

	public void setInc_item_dis_amt(int inc_item_dis_amt) {
		this.inc_item_dis_amt = inc_item_dis_amt;
	}

	public int getInc_price_per_qty() {
		return inc_price_per_qty;
	}

	public void setInc_price_per_qty(int inc_price_per_qty) {
		this.inc_price_per_qty = inc_price_per_qty;
	}

	public int getInc_share_dis_amt() {
		return inc_share_dis_amt;
	}

	public void setInc_share_dis_amt(int inc_share_dis_amt) {
		this.inc_share_dis_amt = inc_share_dis_amt;
	}

	public boolean isEdited() {
		return isEdited;
	}

	public void setEdited(boolean isEdited) {
		this.isEdited = isEdited;
	}

	public int getMrp() {
		return mrp;
	}

	public void setMrp(int mrp) {
		this.mrp = mrp;
	}

	public int getSellingprice() {
		return sellingprice;
	}

	public void setSellingprice(int sellingprice) {
		this.sellingprice = sellingprice;
	}

	public String getTaxobject_uuid() {
		return taxobject_uuid;
	}

	public void setTaxobject_uuid(String taxobject_uuid) {
		this.taxobject_uuid = taxobject_uuid;
	}

	public int getTotal_excl_charge_per_qty() {
		return total_excl_charge_per_qty;
	}

	public void setTotal_excl_charge_per_qty(int total_excl_charge_per_qty) {
		this.total_excl_charge_per_qty = total_excl_charge_per_qty;
	}

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}

}
