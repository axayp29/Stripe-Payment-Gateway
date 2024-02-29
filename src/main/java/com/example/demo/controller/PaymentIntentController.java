package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PaymentIntentDetailEntity;
import com.example.demo.service.StripeService;
import com.stripe.exception.StripeException;

@RestController
public class PaymentIntentController {

	@Autowired
	StripeService stripeService;
	
	@GetMapping("/createPaymentIntent")
	public PaymentIntentDetailEntity createPayment(@RequestParam("customerId") String customerId,@RequestParam("amount") long amount) throws StripeException {

		return stripeService.createPaymentIntent(customerId,amount);

	}
	
	@GetMapping("/cancelPaymentIntent")
	public String cancelPaymentIntent(@RequestParam("paymentIntentId") String paymentIntentId) throws StripeException {

		return stripeService.cancelPaymentIntent(paymentIntentId);

	}
	
	@GetMapping("/updatePaymentIntent")
	public String updatePaymentIntent(@RequestParam("paymentIntentId") String paymentIntentId,@RequestParam("amount") long amount) throws StripeException {

		return stripeService.updatePaymentIntent(paymentIntentId,amount);

	}
	
	@GetMapping("/capturePaymentIntent")
	public PaymentIntentDetailEntity capturePaymentIntent(@RequestParam("paymentIntentId") String paymentIntentId,
			@RequestParam("amount") long amount) throws StripeException {

		return stripeService.capturePaymentIntent(paymentIntentId, amount);

	}
	
	
	/*
	 * @GetMapping("/confirmPaymentIntent") public PaymentIntentDetailEntity
	 * confirmPaymentIntent(@RequestParam("paymentIntentId") String paymentIntentId)
	 * throws StripeException {
	 * 
	 * return stripeService.confirmPaymentIntent(paymentIntentId);
	 * 
	 * }
	 */
	
	
	
	
}
