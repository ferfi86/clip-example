package com.example.clip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clip.model.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
