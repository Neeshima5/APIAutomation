package com.paytm.smartretail_api_automation.dto.orderapi;

public class Customer {

	private int anniversary = 0;
	private int birth_date = 0;
	private Contact contact;
	private String name = "API Automation";
	private boolean email_enabled = false;
	private String feedback = null;
	private String firstname = "API Automation";
	private String gender = "Female";
	private String gstin;
	private String image = null;
	private boolean is_active = true;
	private String lastname = null;
	private String middlename = null;
	private int rating = 0;
	private boolean sms_enabled = false;
	private String store_uuid;
	private String title = null;
	private String uuid;
	private String year_of_birth = null;

	public Customer(Contact contact, String gstin, String store_uuid, String uuid) {
		super();
		this.contact = contact;
		this.gstin = gstin;
		this.store_uuid = store_uuid;
		this.uuid = uuid;
	}

	public int getAnniversary() {
		return anniversary;
	}

	public void setAnniversary(int anniversary) {
		this.anniversary = anniversary;
	}

	public int getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(int birth_date) {
		this.birth_date = birth_date;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEmail_enabled() {
		return email_enabled;
	}

	public void setEmail_enabled(boolean email_enabled) {
		this.email_enabled = email_enabled;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public boolean isSms_enabled() {
		return sms_enabled;
	}

	public void setSms_enabled(boolean sms_enabled) {
		this.sms_enabled = sms_enabled;
	}

	public String getStore_uuid() {
		return store_uuid;
	}

	public void setStore_uuid(String store_uuid) {
		this.store_uuid = store_uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getYear_of_birth() {
		return year_of_birth;
	}

	public void setYear_of_birth(String year_of_birth) {
		this.year_of_birth = year_of_birth;
	}

}
