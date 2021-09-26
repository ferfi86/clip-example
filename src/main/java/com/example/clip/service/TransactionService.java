package com.example.clip.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.clip.model.dtos.DisbursementDto;
import com.example.clip.model.dtos.PaymentDto;
import com.example.clip.model.dtos.ReportDto;
import com.example.clip.model.dtos.UserDto;
import com.example.clip.model.entities.PaymentEntity;
import com.example.clip.model.entities.UserEntity;
import com.example.clip.model.enums.TransactionStatusEnum;
import com.example.clip.repository.PaymentRepository;
import com.example.clip.repository.UserRepository;
import com.example.clip.request.PaymentRequest;

@Service
public class TransactionService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;

	private static double FEE = 0.025;

	public PaymentDto createPayment(PaymentRequest paymentRequest) {
		UserEntity userEntity = userRepository.findById(paymentRequest.getUserId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("User %d not found", paymentRequest.getUserId())));

		PaymentEntity payment = new PaymentEntity();
		payment.setAmount(paymentRequest.getAmount());
		payment.setUser(userEntity);
		payment.setStatus(TransactionStatusEnum.NEW);
		return mapper.map(paymentRepository.save(payment), PaymentDto.class);
	}

	public List<UserDto> getAllUsers() {
		return userRepository.findAll().stream().map(entity -> mapper.map(entity, UserDto.class))
				.collect(Collectors.toList());
	}

	public List<UserDto> getAllUsersWithPayments() {
		return userRepository.getUsersWithPayment().stream().map(entity -> mapper.map(entity, UserDto.class))
				.collect(Collectors.toList());
	}

	public UserDto getUserById(long userId) {
		UserEntity userEntity = userRepository.findById(userId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %d not found", userId)));
		return mapper.map(userEntity, UserDto.class);
	}

	public ReportDto getReportByUserId(long userId) {
		UserEntity userEntity = userRepository.findById(userId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %d not found", userId)));

		List<PaymentEntity> payments = paymentRepository.findByUserId(userId);

		int newPayments = (int) payments.stream().filter(p -> p.getStatus() == TransactionStatusEnum.NEW).count();

		double newPaymentsAmount = payments.stream().filter(p -> p.getStatus() == TransactionStatusEnum.NEW)
				.mapToDouble(p -> p.getAmount().doubleValue()).sum();

		return ReportDto.builder().user(mapper.map(userEntity, UserDto.class)).new_payments(newPayments)
				.payments_sum(payments.size()).new_payments_amount(newPaymentsAmount).build();
	}

	public List<DisbursementDto> disbursementProcess() {
		List<DisbursementDto> disbursementlist = new ArrayList<>();

		List<PaymentEntity> newPayments = paymentRepository.findByStatus(TransactionStatusEnum.NEW);
		for (PaymentEntity paymentEntity : newPayments) {
			BigDecimal discount = BigDecimal
					.valueOf(paymentEntity.getAmount().doubleValue() - (paymentEntity.getAmount().doubleValue() * FEE));

			disbursementlist.add(DisbursementDto.builder().userId(paymentEntity.getUser().getId())
					.payment(paymentEntity.getAmount()).disbursementAmount(discount).build());

			paymentEntity.setStatus(TransactionStatusEnum.PROCESSED);
			paymentEntity.setAmount(discount);
		}
		paymentRepository.saveAll(newPayments);
		return disbursementlist;
	}

	public List<PaymentDto> getPayments() {
		return paymentRepository.findAll().stream().map(entity -> mapper.map(entity, PaymentDto.class))
				.collect(Collectors.toList());
	}
}
