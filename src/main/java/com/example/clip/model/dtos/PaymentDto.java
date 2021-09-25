package com.example.clip.model.dtos;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentDto {
	private long id;
	private BigDecimal amount;
	private String userId;
}
