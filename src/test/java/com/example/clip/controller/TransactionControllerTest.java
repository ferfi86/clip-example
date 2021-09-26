package com.example.clip.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.clip.request.PaymentRequest;
import com.example.clip.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TransactionService service;

	private static final String PATH = "/api/clip";

	@Test
	void createPaymentSuccessTest() throws Exception {

		PaymentRequest paymentRequest = PaymentRequest.builder().amount(BigDecimal.valueOf(100)).userId(5).build();

		mockMvc.perform(post(PATH.concat("/createPayload")).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(paymentRequest))).andDo(print())
				.andExpect(status().isCreated());
	}

	@Test
	void getAllUsersSuccessTest() throws Exception {
		mockMvc.perform(get(PATH.concat("/users")).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void getAllUsersWithPaymentsSuccessTest() throws Exception {
		mockMvc.perform(get(PATH.concat("/usersWithPayments")).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void getUserSuccessTest() throws Exception {
		mockMvc.perform(get(PATH.concat("/usersWithPayments/{userId}"), 5).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
		mockMvc.perform(get(PATH.concat("/users/{userId}"), 5).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void getReportSuccessTest() throws Exception {
		mockMvc.perform(get(PATH.concat("/usersWithPayments/{userId}/report"), 5).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void disbursementProcessSuccessTest() throws Exception {
		mockMvc.perform(post(PATH.concat("/disbursementProcess")).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isCreated());
	}

	@Test
	void getPaymentsSuccessTest() throws Exception {
		mockMvc.perform(get(PATH.concat("/payments")).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}

}
