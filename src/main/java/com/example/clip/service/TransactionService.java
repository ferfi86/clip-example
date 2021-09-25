package com.example.clip.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clip.model.dtos.DisbursementDto;
import com.example.clip.model.dtos.ReportDto;
import com.example.clip.model.dtos.UserDto;
import com.example.clip.model.entities.Payment;
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

	public List<UserDto> getAllUsersWithPayments() {
		// TODO implement user repository
		List<UserDto> users = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			users.add(UserDto.builder().name("fer").id(i).build());
		}
		return users;
	}

	public UserDto getUserById(long userId) {
		// TODO implement user repository
		return UserDto.builder().name("fer").id(9).build();
	}

	public ReportDto getReportByUserId(long userId) {
		// TODO implement report repository
		return ReportDto.builder().build();
	}
	
	public List<DisbursementDto> disbursementProcess() {
		// TODO implement logic to generate the disbursement Process
		List<DisbursementDto> list = new ArrayList<>();
		return list;
	}
}
