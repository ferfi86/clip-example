package com.example.clip.model.dtos;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DisbursementDto {
	private long userId;
	private BigDecimal payment;
	private BigDecimal disbursementAmount;
}
