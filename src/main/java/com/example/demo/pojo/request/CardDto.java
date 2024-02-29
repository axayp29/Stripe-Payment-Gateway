package com.example.demo.pojo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class CardDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("card_number")
	private String cardNumber;
	
	@JsonProperty("date")
	private String date;
	
	@JsonProperty("cvv")
	private String cvv;
	
	@JsonProperty("is_default")
	private Boolean  isDefault;
	
	@JsonProperty("card_type")
	private String cardType;
	
	@JsonProperty("image")
	private String image;
	
	@JsonProperty("id")
	private String id;

}
