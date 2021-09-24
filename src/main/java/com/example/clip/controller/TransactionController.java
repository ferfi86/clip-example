package com.example.clip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.clip.model.Payment;
import com.example.clip.request.PaymentRequest;
import com.example.clip.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/clip")
public class TransactionController {

	@Autowired
	private TransactionService service;

	// TODO GET allUsers

	// TODO GET allUsers with payments

	// TODO POST disbursement process

	// TODO GET report by user

	@PostMapping(value = "/createPayload")
	public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequest paymentRequest) {
		return new ResponseEntity<>(service.createPayment(paymentRequest), HttpStatus.CREATED);
	}

}
