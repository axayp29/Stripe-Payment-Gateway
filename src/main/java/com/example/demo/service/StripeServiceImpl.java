package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.PaymentDetails;
import com.example.demo.entity.PaymentIntentDetailEntity;
import com.example.demo.pojo.request.AddCustomerRequest;
import com.example.demo.pojo.request.CardDto;
import com.example.demo.pojo.request.CreateCardRequest;
import com.example.demo.repo.PaymentDetailsRepository;
import com.example.demo.repo.PaymentIntentDetailRepository;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.model.Refund;
import com.stripe.model.Token;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.CustomerUpdateParams.InvoiceSettings;
import com.stripe.param.PaymentIntentCancelParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentCreateParams.CaptureMethod;
import com.stripe.param.PaymentIntentUpdateParams;
import com.stripe.param.PaymentMethodAttachParams;
import com.stripe.param.PaymentMethodListParams;
import com.stripe.param.RefundCreateParams;

import jakarta.annotation.PostConstruct;

@Service
public class StripeServiceImpl implements StripeService {
	
	@Value("${stripe.secretKey}")
	String stripeKey;
	
	@PostConstruct
	private void setStripeKey() {
		Stripe.apiKey = stripeKey;
	}
	
	@Autowired
	private PaymentIntentDetailRepository intentDetailRepository;
	
	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;

	@Override
	public Customer createCustomer(AddCustomerRequest request) {
		try {
			
			CustomerCreateParams customerParam = CustomerCreateParams.builder()
					.setName(request.getName())
					.setEmail(request.getEmail())
					.setPhone(request.getCountryCode()+request.getMobileNo())
					.setDescription("Testing Account")
					.build();
			
			return Customer.create(customerParam);
	
		
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public PaymentDetails createCustomerPaymentMethod(String testingToken,String customerId, CreateCardRequest createCardRequest)
			throws StripeException {
		
		  // Create a map to represent the token request
//        Map<String, Object> tokenParams = new HashMap<>();
//        tokenParams.put("id", "tok_visa");

        
         
	           

		// getting finger print for new card to check if already exists
		/*
		 * Card newCard = Card.builder() .setCvc(createCardRequest.getCvv())
		 * .setNumber(createCardRequest.getCardNumber())
		 * .setExpMonth(String.valueOf(createCardRequest.getExpiryMonth()))
		 * .setExpYear(String.valueOf(createCardRequest.getExpiryYear())) .build();
		 * 
		 * TokenCreateParams tokenCreateParams = TokenCreateParams.builder()
		 * .setCard(newCard) .build();
		 * 
		 * Token newCardToken = Token.create(tokenCreateParams);
		 */
		
		  // Retrieve the token using the test token
        Token token = Token.retrieve(testingToken);

        // Print the token ID
        System.err.println("Token ID: " + token.getId());
		
		String newCardFingerPrint = token.getCard().getFingerprint();
		
		// getting all cards 
		PaymentMethodListParams paymentMethodListparams = PaymentMethodListParams
				.builder()
				.setCustomer(customerId)
				.setType(com.stripe.param.PaymentMethodListParams.Type.CARD)
				.build();
		PaymentMethodCollection paymentMethods = PaymentMethod.list(paymentMethodListparams);
		
		// matching card details
		Boolean isDuplicateCard = paymentMethods.getData().stream().anyMatch(method->
			method.getCard().getFingerprint().equals(newCardFingerPrint));
		
		// Duplicate Card
		if(isDuplicateCard) {
			throw new CardException("Duplicate Card Found Exception ",
					null, null, null, null, null, null, null);
		}
		
//		CardDetails cardDetails = CardDetails.builder()
//				.setCvc(createCardRequest.getCvv())
//				.setNumber(createCardRequest.getCardNumber())
//				.setExpMonth(createCardRequest.getExpiryMonth())
//				.setExpYear(createCardRequest.getExpiryYear())
//				.build();
//		
//		BillingDetails billingDetails = BillingDetails.builder()
//				.setName(createCardRequest.getName())
//				.build();
	
	
	
		
		 Map<String, Object> paymentMethodParams = new HashMap<>();
         paymentMethodParams.put("type", "card");
         paymentMethodParams.put("card", Map.of("token", token.getId()));

         // Create PaymentMethod
         PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);

//        
//		
//		PaymentMethodCreateParams paymentMethodParams = PaymentMethodCreateParams.builder()
//					.setCard(cardDetails)
//					.setBillingDetails(billingDetails)
//					.setType(Type.CARD)
//					.build();

	
		
		PaymentMethodAttachParams attchParams = PaymentMethodAttachParams.builder()
				.setCustomer(customerId)
				.build();
		
		paymentMethod.attach(attchParams);

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setCustomerId(customerId);
        paymentDetails.setPaymentMethodId(paymentMethod.getId());
        paymentDetails.setTokenId(token.getId());
        
        if(paymentDetailsRepository.findByCustomerIdAndIsDefaultPaymentMethodTrue(customerId) == null)
        {
        	paymentDetails.setDefaultPaymentMethod(true);
        	//setPaymentMethodToDefault(customerId, paymentMethod.getId());
        }
       
        
       
		return  paymentDetailsRepository.save(paymentDetails);
	}
	
	@Override
	public List<CardDto> getAllPaymentMethods(String customerId) throws StripeException {
		
		PaymentMethodListParams paymentMethodParams = PaymentMethodListParams
				.builder()
				.setCustomer(customerId)
				.setType(com.stripe.param.PaymentMethodListParams.Type.CARD)
				.build();
		
		Customer customer = Customer.retrieve(customerId);
		
		
		
		List<CardDto> cardDtos = new ArrayList<>();
		
		PaymentMethodCollection paymentMethods = PaymentMethod.list(paymentMethodParams);
		
		paymentMethods.getData().stream().forEach(method->{
			
			CardDto cardDto = new CardDto();
			cardDto.setId(method.getId());
			cardDto.setCardNumber(method.getCard().getLast4());
			cardDto.setCardType(method.getCard().getBrand());
			cardDto.setName(method.getBillingDetails().getName());
			cardDto.setIsDefault(customer.getInvoiceSettings().getDefaultPaymentMethod()==null?
					false
					:(customer.getInvoiceSettings().getDefaultPaymentMethod().equals(method.getId())));
			
			
			cardDtos.add(cardDto);
		});
		
		return cardDtos;
	}
	
	
	
	@Override
	public void setPaymentMethodToDefault(String customerId, String paymentMethodId) throws StripeException {

		Customer customer = Customer.retrieve(customerId);

		InvoiceSettings invoiceSettings = InvoiceSettings.builder().setDefaultPaymentMethod(paymentMethodId).build();

		CustomerUpdateParams customerUpdateParams = CustomerUpdateParams.builder().setInvoiceSettings(invoiceSettings)
				.build();
		
		customer.update(customerUpdateParams);

		PaymentDetails paymentDetails = paymentDetailsRepository.findByPaymentMethodId(paymentMethodId);

		paymentDetails.setDefaultPaymentMethod(true);

		paymentDetailsRepository.save(paymentDetails);

	}

	@Override
	public void deletePaymentMethod(String paymentMethodId) throws StripeException {
		
		PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
		
		paymentMethod.detach();
		
	}

	@Override
	public PaymentIntentDetailEntity createPaymentIntent(String customerId, long amount) throws StripeException {
		
		PaymentDetails method	= paymentDetailsRepository.
			findByCustomerIdAndIsDefaultPaymentMethodTrue(customerId);
		PaymentIntentCreateParams params =
				  PaymentIntentCreateParams
				    .builder()
				    .setCustomer(customerId)
				    .addPaymentMethodType("card")
				   // .setSetupFutureUsage(PaymentIntentCreateParams.SetupFutureUsage.OFF_SESSION)
				    .setAmount(amount * 100) // amount in paisa 
				    .setCurrency("inr")
				   .setPaymentMethod(method.getPaymentMethodId())
				   .setCaptureMethod(CaptureMethod.MANUAL)
				   .setConfirm(true)
				   .setOffSession(true)				  
				   .build();

				PaymentIntent paymentIntent = PaymentIntent.create(params);
				
				PaymentIntentDetailEntity entity = new PaymentIntentDetailEntity();
				
				entity.setPaymentIntentId(paymentIntent.getId());
				entity.setAmount(paymentIntent.getAmount()/100);
				entity.setCustomerId(customerId);
				entity.setCreatedAt(LocalDateTime.now());
				entity.setStatus("CREATED");
				
				return intentDetailRepository.save(entity);
				
				
			
	}

	@Override
	public String cancelPaymentIntent(String paymentIntentId) throws StripeException {

		PaymentIntent resource = PaymentIntent.retrieve(paymentIntentId);
		PaymentIntentCancelParams params = PaymentIntentCancelParams.builder().build();
		PaymentIntent paymentIntent = resource.cancel(params);
		
		PaymentIntentDetailEntity detailEntity	= intentDetailRepository.findByPaymentIntentId(paymentIntentId);
		detailEntity.setStatus("CANCELED");
		intentDetailRepository.save(detailEntity);

		return "Payment Intent Deleted with Id : " + paymentIntent.getId();
	}

	@Override
	public String updatePaymentIntent(String paymentIntentId, long amount) throws StripeException {

		PaymentIntent resource = PaymentIntent.retrieve(paymentIntentId);

		PaymentIntentUpdateParams params = PaymentIntentUpdateParams.builder().setAmount(amount * 100).build();
		PaymentIntent paymentIntent = resource.update(params);
		
		PaymentIntentDetailEntity detailEntity	= intentDetailRepository.findByPaymentIntentId(paymentIntentId);
		detailEntity.setAmount(amount);
		detailEntity.setStatus("UPDATED");
		intentDetailRepository.save(detailEntity);

		return "Payment Intent " + paymentIntentId + "with amount of " + amount;
	}

	@Override
	public PaymentIntentDetailEntity capturePaymentIntent(String paymentIntentId,long amount) throws StripeException {
		
		PaymentIntent resource = PaymentIntent.retrieve(paymentIntentId);
		
		resource.capture();
		
		PaymentIntentDetailEntity detailEntity	= intentDetailRepository.findByPaymentIntentId(paymentIntentId);
		detailEntity.setCapturedAmount(amount);
		detailEntity.setStatus("Success");
		return intentDetailRepository.save(detailEntity);
	}

	@Override
	public Customer updateCustomer(AddCustomerRequest request, String CustomerId) throws StripeException {
		Customer resource = Customer.retrieve(CustomerId);
		CustomerUpdateParams params = CustomerUpdateParams.builder()
				.setName(request.getName())
				.setEmail(request.getEmail())
				.setPhone(request.getCountryCode()+request.getMobileNo())
				.setDescription("Testing Account Updated")
				.build();
		
		
		Customer customer = resource.update(params);

		return customer;
	}

	@Override
	public List<CustomerEntity> getAllCustomer() throws StripeException {
		
		CustomerListParams params = CustomerListParams.builder().setLimit(3L).build();
		CustomerCollection customers = Customer.list(params);
		
		List<CustomerEntity> entity = new ArrayList<>();
		
		customers.getData().stream().forEach(x -> entity.add(CustomerEntity.builder()
				.stripeCustomerId(x.getId())
				.email(x.getEmail())
				.mobileNo(x.getPhone())
				.name(x.getName())
				.build()));
		
		return entity;
	}

	@Override
	public void deleteCustomer(String customerId) throws StripeException {
		
		Customer resource = Customer.retrieve(customerId);
		resource.delete();
		
	}

	@Override
	public PaymentIntentDetailEntity createRefund(String paymentIntentId) throws StripeException {

		RefundCreateParams params = RefundCreateParams.builder().setPaymentIntent(paymentIntentId).build();
		Refund.create(params);

		PaymentIntentDetailEntity paymentIntentDetailEntity = intentDetailRepository
				.findByPaymentIntentId(paymentIntentId);

		paymentIntentDetailEntity.setStatus("REFUNDED");
		
		return intentDetailRepository.save(paymentIntentDetailEntity);
	}

	/*
	 * @Override public PaymentIntentDetailEntity confirmPaymentIntent(String
	 * paymentIntentId) throws StripeException {
	 * 
	 * PaymentIntent resource = PaymentIntent.retrieve(paymentIntentId);
	 * PaymentIntentConfirmParams params = PaymentIntentConfirmParams.builder()
	 * .setPaymentMethod("pm_card_visa") .setReturnUrl("https://www.example.com")
	 * .build(); PaymentIntent paymentIntent = resource.confirm(params);
	 * 
	 * PaymentIntentDetailEntity detailEntity =
	 * intentDetailRepository.findByPaymentIntentId(paymentIntentId);
	 * detailEntity.setStatus("Amount Confirmed"); return
	 * intentDetailRepository.save(detailEntity);
	 * 
	 * }
	 */

	

}
