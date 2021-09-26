package com.example.clip.model.dtos;

import java.math.BigDecimal;

import com.example.clip.model.entities.UserEntity;
import com.example.clip.model.enums.TransactionStatusEnum;

import lombok.Data;

@Data
public class PaymentDto {
	private long id;
	private BigDecimal amount;
	private TransactionStatusEnum status;
	private UserEntity user;
}
