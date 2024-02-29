package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PaymentDetails;
import com.example.demo.pojo.request.CardDto;
import com.example.demo.pojo.request.CreateCardRequest;
import com.example.demo.service.StripeService;
import com.stripe.exception.StripeException;

@RestController
public class PaymentMethodController {

	@Autowired
	StripeService stripeService;

	@PostMapping("/addCustomerPaymentMethod")
	public PaymentDetails createCustomerPaymentMethod(@RequestBody CreateCardRequest createCardRequest,
			@RequestParam("customerId") String customerId, @RequestParam("testingToken") String testingToken)
			throws StripeException {

		return stripeService.createCustomerPaymentMethod(testingToken, customerId, createCardRequest);

	}

	@GetMapping("/getAllPaymentMethod")
	public List<CardDto> getAllPaymentMethod(@RequestParam("customerId") String customerId) throws StripeException {

		return stripeService.getAllPaymentMethods(customerId);

	}

	@GetMapping("/setPaymentMethodToDefault")
	public String setPaymentMethodToDefault(@RequestParam("customerId") String customerId,
			@RequestParam("paymentMethodId") String paymentMethodId) throws StripeException {

		stripeService.setPaymentMethodToDefault(customerId, paymentMethodId);

		return "Default Method Save Successfully : " + paymentMethodId;
	}

}
