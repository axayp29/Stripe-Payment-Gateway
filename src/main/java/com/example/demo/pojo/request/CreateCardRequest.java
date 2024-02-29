package com.example.demo.pojo.request;

import lombok.Data;

@Data
public class CreateCardRequest {

	private String name;

	private String cardNumber;

	private Long expiryMonth;

	private Long expiryYear;

	private String cvv;
}
