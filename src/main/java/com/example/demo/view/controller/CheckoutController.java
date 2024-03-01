package com.example.demo.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.pojo.request.ChargeRequest;
import com.example.demo.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CheckoutController {
	
	@Autowired
	private StripeService stripeService;
	
	
	@RequestMapping("/addProduct")
	private String addProductPage() {

		return "/addProduct";
	}

	@RequestMapping("/checkout")
	private String getCheckoutPage(HttpServletRequest request,Model model,@RequestParam("checkOutAmount") String amount) {
	
		model.addAttribute("amount", Long.parseLong(amount) * 100);
		return "/checkout";
	}
	
	@RequestMapping("/success")
	private String getSuccessPage() {

		return "/success";
	}
	
	@PostMapping("/createPaymentIntent")
	private String createCharge(ChargeRequest chargeRequest, Model model,
			@RequestParam("totalAmount") String totalAmount
			) throws StripeException {
		
		PaymentIntent paymentIntent = stripeService.processPaymentUsingWeb(chargeRequest, totalAmount);
		
		model.addAttribute("amount", totalAmount);
		model.addAttribute("paymentIntentId", paymentIntent.getId());

		return "/success";
	}



}
