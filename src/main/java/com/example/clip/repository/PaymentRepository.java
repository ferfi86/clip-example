package com.example.clip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clip.model.entities.PaymentEntity;
import com.example.clip.model.enums.TransactionStatusEnum;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
	
	public List<PaymentEntity> findByUserId(Long id);
	
	public List<PaymentEntity> findByStatus(TransactionStatusEnum status);
	
}
