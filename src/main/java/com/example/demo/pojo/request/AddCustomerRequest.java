package com.example.demo.pojo.request;

import lombok.Data;

@Data
public class AddCustomerRequest {

	private String name;
	
	private String email;
	
	private String countryCode;
	
	private String mobileNo;
}
