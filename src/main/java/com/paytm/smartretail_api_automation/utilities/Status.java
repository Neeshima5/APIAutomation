package com.paytm.smartretail_api_automation.utilities;

public enum Status {

	OK(200), 
	CREATED(201), 
	BAD_REQUEST(400),
	UNAUTHORIZED(401),	
	NOT_FOUND(404), 
	INTERNAL_SERVER_ERROR(500);

	int statusCode;

	Status(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {

		return this.statusCode;
	}

}
