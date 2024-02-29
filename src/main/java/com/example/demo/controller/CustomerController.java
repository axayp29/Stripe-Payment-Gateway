package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.pojo.request.AddCustomerRequest;
import com.example.demo.repo.CustomerRepository;
import com.example.demo.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;

@RestController
public class CustomerController {

	@Autowired
	StripeService stripeService;

	@Autowired
	CustomerRepository customerRepository;

	@PostMapping("/createCustomer")
	public CustomerEntity createCustomer(@RequestBody AddCustomerRequest request) {

		Customer customer = stripeService.createCustomer(request);

		CustomerEntity customerEntity = new CustomerEntity();

		BeanUtils.copyProperties(request, customerEntity);

		customerEntity.setStripeCustomerId(customer.getId());

		return customerRepository.save(customerEntity);

	}
	
	
	@PostMapping("/updateCustomer")
	public CustomerEntity updateCustomer(@RequestBody AddCustomerRequest request,@RequestParam("customerId") String customerId) throws StripeException {

		stripeService.updateCustomer(request,customerId);

		CustomerEntity customerEntity =customerRepository.findByStripeCustomerId(customerId);

		BeanUtils.copyProperties(request, customerEntity);

		return customerRepository.save(customerEntity);

	}

	@GetMapping("/getAllCustomers")
	public List<CustomerEntity> getAllCustomers() throws StripeException {

		return stripeService.getAllCustomer();

	}
	
	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam("customerId") String customerId) throws StripeException {

		stripeService.deleteCustomer(customerId);

		CustomerEntity customerEntity = customerRepository.findByStripeCustomerId(customerId);

		customerRepository.deleteById(customerEntity.getId());

		return "Customer Delete With Id : " + customerId;

	}
	

	
	

}
