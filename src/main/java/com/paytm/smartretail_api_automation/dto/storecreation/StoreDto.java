package com.paytm.smartretail_api_automation.dto.storecreation;

public class StoreDto {

	private BranchDto branch;
	private String name;
	private AddressDto address;

	public StoreDto() {

	}

	public StoreDto(BranchDto branch, String name, AddressDto address) {
		super();
		this.branch = branch;
		this.name = name;
		this.address = address;
	}

	public BranchDto getBranch() {
		return branch;
	}

	public void setBranch(BranchDto branch) {
		this.branch = branch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

}
