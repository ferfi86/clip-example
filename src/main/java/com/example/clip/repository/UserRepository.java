package com.example.clip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clip.model.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query(value = "SELECT DISTINCT(USER.ID),  USER.NAME FROM USER "
			+ "JOIN PAYMENT ON PAYMENT.USER_ID=USER.ID", nativeQuery = true)
	List<UserEntity> getUsersWithPayment();

}
