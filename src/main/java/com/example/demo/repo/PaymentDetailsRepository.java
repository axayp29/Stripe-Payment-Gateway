package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PaymentDetails;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long>{
	
	
	PaymentDetails findByCustomerIdAndIsDefaultPaymentMethodTrue(String customerId);
	
	PaymentDetails findByPaymentMethodId(String paymentMethodId);


}
