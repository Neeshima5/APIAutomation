package com.paytm.smartretail_api_automation.dto.orderapi;

public class Employee {

	private String name;
	private int rank;
	private String uuid;

	public Employee(String name, int rank, String uuid) {
		super();
		this.name = name;
		this.rank = rank;
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
