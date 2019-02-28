package com.paytm.smartretail_api_automation.dto.orderapi;

import java.util.List;

public class Item {

	private List<ChargeSplit> chargesplit;
	private List<String> comments;
	private int delete_reason = 0;
	private String description;
	private String hsc = null;
	private List<String> ingredients;
	private String label_name = null;
	private int line_item_id;
	private int machinecode = 0;
	private int metric;
	private List<String> modifiers;
	private int parent_line_item_id = 0;
	private Price price;
	private String product_code;
	private String product_name;
	private List<Properties> properties;
	private int qty;
	private int return_qty = 0;
	private String sku;
	private List<TaxSplit> taxsplit;
	private int unit_count = 0;
	private int unit_qty = 0;
	private List<UserProperties> user_properties;
	private String variant_name;

	public Item(List<ChargeSplit> chargesplit, List<String> comments, String description, List<String> ingredients,
			int line_item_id, int metric, List<String> modifiers, Price price, String product_code, String product_name,
			List<Properties> properties, int qty, String sku, List<TaxSplit> taxsplit,
			List<UserProperties> user_properties, String variant_name) {
		super();
		this.chargesplit = chargesplit;
		this.comments = comments;
		this.description = description;
		this.ingredients = ingredients;
		this.line_item_id = line_item_id;
		this.metric = metric;
		this.modifiers = modifiers;
		this.price = price;
		this.product_code = product_code;
		this.product_name = product_name;
		this.properties = properties;
		this.qty = qty;
		this.sku = sku;
		this.taxsplit = taxsplit;
		this.user_properties = user_properties;
		this.variant_name = variant_name;
	}

	public List<ChargeSplit> getChargesplit() {
		return chargesplit;
	}

	public void setChargesplit(List<ChargeSplit> chargesplit) {
		this.chargesplit = chargesplit;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public int getDelete_reason() {
		return delete_reason;
	}

	public void setDelete_reason(int delete_reason) {
		this.delete_reason = delete_reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHsc() {
		return hsc;
	}

	public void setHsc(String hsc) {
		this.hsc = hsc;
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	public String getLabel_name() {
		return label_name;
	}

	public void setLabel_name(String label_name) {
		this.label_name = label_name;
	}

	public int getLine_item_id() {
		return line_item_id;
	}

	public void setLine_item_id(int line_item_id) {
		this.line_item_id = line_item_id;
	}

	public int getMachinecode() {
		return machinecode;
	}

	public void setMachinecode(int machinecode) {
		this.machinecode = machinecode;
	}

	public int getMetric() {
		return metric;
	}

	public void setMetric(int metric) {
		this.metric = metric;
	}

	public List<String> getModifiers() {
		return modifiers;
	}

	public void setModifiers(List<String> modifiers) {
		this.modifiers = modifiers;
	}

	public int getParent_line_item_id() {
		return parent_line_item_id;
	}

	public void setParent_line_item_id(int parent_line_item_id) {
		this.parent_line_item_id = parent_line_item_id;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public List<Properties> getProperties() {
		return properties;
	}

	public void setProperties(List<Properties> properties) {
		this.properties = properties;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getReturn_qty() {
		return return_qty;
	}

	public void setReturn_qty(int return_qty) {
		this.return_qty = return_qty;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public List<TaxSplit> getTaxsplit() {
		return taxsplit;
	}

	public void setTaxsplit(List<TaxSplit> taxsplit) {
		this.taxsplit = taxsplit;
	}

	public int getUnit_count() {
		return unit_count;
	}

	public void setUnit_count(int unit_count) {
		this.unit_count = unit_count;
	}

	public int getUnit_qty() {
		return unit_qty;
	}

	public void setUnit_qty(int unit_qty) {
		this.unit_qty = unit_qty;
	}

	public List<UserProperties> getUser_properties() {
		return user_properties;
	}

	public void setUser_properties(List<UserProperties> user_properties) {
		this.user_properties = user_properties;
	}

	public String getVariant_name() {
		return variant_name;
	}

	public void setVariant_name(String variant_name) {
		this.variant_name = variant_name;
	}

}
