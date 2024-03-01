package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.StripeService;
import com.stripe.exception.StripeException;

@RestController
public class InvoiceController {
	
	@Autowired
	StripeService stripeService;
	
	@GetMapping("/createInvoice")
	public String createInvoice(@RequestParam("customerId") String customerId) throws StripeException {

		return stripeService.createInvoice(customerId);


	}

}
