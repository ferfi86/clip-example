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
import com.example.clip.model.dtos.PaymentDto;
import com.example.clip.model.dtos.ReportDto;
import com.example.clip.model.dtos.UserDto;
import com.example.clip.request.PaymentRequest;
import com.example.clip.service.TransactionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/clip")
public class TransactionController {

	@Autowired
	private TransactionService service;

	@PostMapping(value = "/createPayload")
	@ApiOperation(value = "Create a payment", response = PaymentDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Payment was created"),
			@ApiResponse(code = 404, message = "user not found"),
	})
	public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentRequest paymentRequest) {
		return new ResponseEntity<>(service.createPayment(paymentRequest), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/users")
	@ApiOperation(value = "Get all users in db", response = UserDto.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "get all users")
	})
	public ResponseEntity<List<UserDto>> getAllUsers() {
		log.info("getting all users with payments");
		return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping(value = "/usersWithPayments")
	@ApiOperation(value = "Get all users with payments", response = UserDto.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "get all users with payments")
	})
	public ResponseEntity<List<UserDto>> getAllUsersWithPayments() {
		log.info("getting all users with payments");
		return new ResponseEntity<>(service.getAllUsersWithPayments(), HttpStatus.OK);
	}

	@GetMapping(value={"/usersWithPayments/{userId}", "/users/{userId}"})
	@ApiOperation(value = "Get user info", response = UserDto.class)
	@ApiParam(name = "user Id", required = true)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "get user info")
	})
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") long userId) {
		log.info("getting user with userId:{}", userId);
		return new ResponseEntity<>(service.getUserById(userId), HttpStatus.OK);
	}

	
	@GetMapping(value = "/usersWithPayments/{userId}/report")
	@ApiOperation(value = "Report with total payments and sum of new payments", response = ReportDto.class)
	@ApiParam(name = "user Id", required = true)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "get report")
	})
	public ResponseEntity<ReportDto> getReportByUserId(@PathVariable("userId") long userId) {
		log.info("getting report by userId:{}", userId);
		return new ResponseEntity<>(service.getReportByUserId(userId), HttpStatus.OK);
	}

	@PostMapping(value = "/disbursementProcess")
	@ApiOperation(value = "Execute Disbursement process", response = DisbursementDto.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "results of disbursement process")
	})
	public ResponseEntity<List<DisbursementDto>> disbursementProcess() {
		return new ResponseEntity<>(service.disbursementProcess(), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/payments")
	@ApiOperation(value = "get all saved payments", response = PaymentDto.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "All the payments records")
	})
	public ResponseEntity<List<PaymentDto>> getPayments() {
		log.info("getting all users with payments");
		return new ResponseEntity<>(service.getPayments(), HttpStatus.OK);
	}

}
