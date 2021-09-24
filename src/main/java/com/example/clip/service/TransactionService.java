package com.example.clip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clip.model.Payment;
import com.example.clip.repository.PaymentRepository;
import com.example.clip.request.PaymentRequest;

@Service
public class TransactionService {

	@Autowired
	private PaymentRepository paymentRepository;

	public Payment createPayment(PaymentRequest paymentRequest) {
      Payment payment = new Payment();
      payment.setAmount(paymentRequest.getAmount());
      payment.setUserId(paymentRequest.getUserId());
      paymentRepository.save(payment);
      return payment;
	}
}
