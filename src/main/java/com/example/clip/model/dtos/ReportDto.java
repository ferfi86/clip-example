package com.example.clip.model.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReportDto {
	private UserDto user;
	private int payments_sum;
	private int new_payments;
	private double new_payments_amount;
	
}
