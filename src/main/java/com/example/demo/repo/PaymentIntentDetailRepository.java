package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PaymentIntentDetailEntity;

@Repository
public interface PaymentIntentDetailRepository extends JpaRepository<PaymentIntentDetailEntity, Long>{

	PaymentIntentDetailEntity findByPaymentIntentId(String paymentIntentId);
	
	PaymentIntentDetailEntity findByRefundId(String refundId);
	
	PaymentIntentDetailEntity findByChargeId(String chargeId);
	
}
