package com.example.clip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.clip.model.dtos.DisbursementDto;
import com.example.clip.model.dtos.ReportDto;
import com.example.clip.model.dtos.UserDto;
import com.example.clip.model.entities.Payment;
import com.example.clip.request.PaymentRequest;
import com.example.clip.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/clip")
public class TransactionController {

	@Autowired
	private TransactionService service;

	@PostMapping(value = "/createPayload")
	public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequest paymentRequest) {
		return new ResponseEntity<>(service.createPayment(paymentRequest), HttpStatus.CREATED);
	}

	@GetMapping(value = "/usersWithPayments")
	public ResponseEntity<List<UserDto>> getAllUsersWithPayments() {
		log.info("getting all users with payments");
		return new ResponseEntity<>(service.getAllUsersWithPayments(), HttpStatus.OK);
	}

	@GetMapping(value = "/usersWithPayments/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") long userId) {
		log.info("getting user with userId:{}", userId);
		return new ResponseEntity<>(service.getUserById(userId), HttpStatus.OK);
	}

	@GetMapping(value = "/usersWithPayments/{userId}/report")
	public ResponseEntity<ReportDto> getReportByUserId(@PathVariable("userId") long userId) {
		log.info("getting report by userId:{}", userId);
		return new ResponseEntity<>(service.getReportByUserId(userId), HttpStatus.OK);
	}

	@PostMapping(value = "/disbursementProcess")
	public ResponseEntity<List<DisbursementDto>> disbursementProcess() {
		return new ResponseEntity<>(service.disbursementProcess(), HttpStatus.CREATED);
	}

}
