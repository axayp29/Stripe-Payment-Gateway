package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.PaymentDetails;
import com.example.demo.entity.PaymentIntentDetailEntity;
import com.example.demo.pojo.request.AddCustomerRequest;
import com.example.demo.pojo.request.CardDto;
import com.example.demo.pojo.request.ChargeRequest;
import com.example.demo.pojo.request.CreateCardRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;

public interface StripeService {

	Customer createCustomer(AddCustomerRequest request);
	
	Customer updateCustomer(AddCustomerRequest request,String CustomerId) throws StripeException;
	
	List<CustomerEntity> getAllCustomer() throws StripeException;
	
	void deleteCustomer(String customerId) throws StripeException ;
	
	PaymentDetails createCustomerPaymentMethod(String testingToken,String customerId,CreateCardRequest createCardRequest) throws StripeException;

	List<CardDto> getAllPaymentMethods(String customerId) throws StripeException;
	
	void setPaymentMethodToDefault(String customerId,String paymentMethodId) throws StripeException;
	
	void deletePaymentMethod(String paymentMethodId) throws StripeException;
	
	PaymentIntentDetailEntity createPaymentIntent(String customerId,long amount) throws StripeException;
	
	String cancelPaymentIntent(String paymentIntentId) throws StripeException;

	String updatePaymentIntent(String paymentIntentId,long amount) throws StripeException ;

	PaymentIntentDetailEntity capturePaymentIntent(String paymentIntentId,long amount) throws StripeException;

	// PaymentIntentDetailEntity confirmPaymentIntent(String paymentIntentId) throws StripeException;
	
	PaymentIntentDetailEntity createRefund(String paymentIntentId) throws StripeException ;
	
	PaymentIntentDetailEntity updateRefund(String refundId,long amount) throws StripeException ;
	
	PaymentIntentDetailEntity cancelRefund(String refundId) throws StripeException;
	
	String createInvoice(String customerId) throws StripeException;
	
	PaymentIntent processPaymentUsingWeb(ChargeRequest chargeRequest,String amount) throws StripeException, NumberFormatException;
	
	// PayoutEntity createPayOut(long payOut) throws StripeException ;
}
