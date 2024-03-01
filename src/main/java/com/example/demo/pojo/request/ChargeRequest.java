package com.example.demo.pojo.request;

import lombok.Data;

@Data
public class ChargeRequest {
	
	public enum Currency {USD,INR}
	
	private String description;
	
	private String stripeEmail;
	
	private Currency currency;
	
	private int amount;
	
	private String stripeToken;

}
