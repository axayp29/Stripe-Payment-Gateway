package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PaymentIntentDetailEntity;
import com.example.demo.service.StripeService;
import com.stripe.exception.StripeException;

@RestController
public class RefundController {
	
	@Autowired
	StripeService stripeService;
	
	@GetMapping("/refund")
	public PaymentIntentDetailEntity createRefund(
			@RequestParam("paymentIntentId") String paymentIntentId) throws StripeException {

		return stripeService.createRefund(paymentIntentId);
	}
	
	@GetMapping("/updateRefund")
	public PaymentIntentDetailEntity updateRefund(
			@RequestParam("refundId") String refundId,@RequestParam("amount") long amount) throws StripeException {

		return stripeService.updateRefund(refundId,amount);
	}
	
	@GetMapping("/cancelRefund")
	public PaymentIntentDetailEntity cancelRefund(
			@RequestParam("refundId") String refundId) throws StripeException {

		return stripeService.cancelRefund(refundId);
	}

}
