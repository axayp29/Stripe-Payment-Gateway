package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PaymentIntentDetailEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String paymentIntentId;
	
	private String customerId;
	
	private long amount;
	
	private long capturedAmount = 0;
	
	private LocalDateTime createdAt;
	
	private String status;
	
	private String refundId;
	
	private long refundedAmount;
	
	private String chargeId;
}
