package com.example.demo.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCustomerRequest {

	private String name;
	
	private String email;
	
	private String countryCode;
	
	private String mobileNo;
}
