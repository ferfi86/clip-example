package com.example.clip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.clip.model.dtos.DisbursementDto;
import com.example.clip.model.dtos.PaymentDto;
import com.example.clip.model.dtos.ReportDto;
import com.example.clip.model.dtos.UserDto;
import com.example.clip.model.enums.TransactionStatusEnum;
import com.example.clip.request.PaymentRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TransactionIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate template;

	private static double FEE = 0.025;

	@Test
	void getAllUsersTest() throws Exception {

		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(getUrl("users")).build();

		HttpEntity<String> entity = new HttpEntity<>(null, null);
		ResponseEntity<List<UserDto>> response = template.exchange(uriComponents.toString(), HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<UserDto>>() {
				});

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(6, response.getBody().size());
	}

	@Test
	void createPayloadTest() throws Exception {

		PaymentRequest paymentRequest = PaymentRequest.builder().amount(BigDecimal.valueOf(100)).userId(4).build();

		RequestEntity<PaymentRequest> request = RequestEntity.post(new URI(getUrl("createPayload")))
				.accept(MediaType.APPLICATION_JSON).body(paymentRequest);

		ResponseEntity<PaymentDto> response = template.exchange(request, new ParameterizedTypeReference<PaymentDto>() {
		});

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(4, response.getBody().getUser().getId());
		assertEquals(TransactionStatusEnum.NEW, response.getBody().getStatus());
	}

	@Test
	void usersWithPaymentsTest() throws Exception {

		PaymentRequest paymentRequest = PaymentRequest.builder().amount(BigDecimal.valueOf(100)).userId(4).build();

		RequestEntity<PaymentRequest> request = RequestEntity.post(new URI(getUrl("createPayload")))
				.accept(MediaType.APPLICATION_JSON).body(paymentRequest);

		ResponseEntity<PaymentDto> paymentResponse = template.exchange(request,
				new ParameterizedTypeReference<PaymentDto>() {
				});

		ResponseEntity<List<UserDto>> userResponse = template.exchange(getUrl("usersWithPayments"), HttpMethod.GET,
				new HttpEntity<>(null, null), new ParameterizedTypeReference<List<UserDto>>() {
				});

		assertEquals(HttpStatus.CREATED, paymentResponse.getStatusCode());
		assertEquals(4, paymentResponse.getBody().getUser().getId());
		assertEquals(paymentResponse.getBody().getUser().getId(), userResponse.getBody().get(0).getId());

	}

	@Test
	void paymetsTest() throws Exception {

		for (int i = 1; i <= 5; i++) {
			PaymentRequest paymentRequest = PaymentRequest.builder().amount(BigDecimal.valueOf(i * 100)).userId(4)
					.build();

			RequestEntity<PaymentRequest> request = RequestEntity.post(new URI(getUrl("createPayload")))
					.accept(MediaType.APPLICATION_JSON).body(paymentRequest);

			template.exchange(request, new ParameterizedTypeReference<PaymentDto>() {
			});

		}

		ResponseEntity<List<PaymentDto>> response = template.exchange(getUrl("payments"), HttpMethod.GET,
				new HttpEntity<>(null, null), new ParameterizedTypeReference<List<PaymentDto>>() {
				});

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(5, response.getBody().size());

	}

	@Test
	void reportTest() throws Exception {
		double sumAmount = 0;
		for (int i = 1; i <= 5; i++) {
			sumAmount += (i * 100);
			PaymentRequest paymentRequest = PaymentRequest.builder().amount(BigDecimal.valueOf(i * 100)).userId(2)
					.build();

			RequestEntity<PaymentRequest> request = RequestEntity.post(new URI(getUrl("createPayload")))
					.accept(MediaType.APPLICATION_JSON).body(paymentRequest);
			template.exchange(request, new ParameterizedTypeReference<PaymentDto>() {
			});
		}

		ResponseEntity<ReportDto> reportResponse = template.exchange(getUrl("usersWithPayments/2/report"),
				HttpMethod.GET, new HttpEntity<>(null, null), new ParameterizedTypeReference<ReportDto>() {
				});

		assertEquals(HttpStatus.OK, reportResponse.getStatusCode());
		assertEquals(5, reportResponse.getBody().getNew_payments());
		assertEquals(sumAmount, reportResponse.getBody().getNew_payments_amount());

	}

	@Test
	void disbursementProcessTest() throws Exception {
		ResponseEntity<List<PaymentDto>> paymentsResponse = template.exchange(getUrl("payments"), HttpMethod.GET,
				new HttpEntity<>(null, null), new ParameterizedTypeReference<List<PaymentDto>>() {
				});

		ResponseEntity<List<DisbursementDto>> disburmentResponse = template.withBasicAuth("admin", "password")
				.exchange(RequestEntity.post(new URI(getUrl("disbursementProcess"))).accept(MediaType.APPLICATION_JSON)
						.body(null), new ParameterizedTypeReference<List<DisbursementDto>>() {
						});

		ResponseEntity<List<PaymentDto>> paymentsResponseProcessed = template.exchange(getUrl("payments"),
				HttpMethod.GET, new HttpEntity<>(null, null), new ParameterizedTypeReference<List<PaymentDto>>() {
				});

		for (PaymentDto dto : paymentsResponse.getBody()) {
			assertEquals(TransactionStatusEnum.NEW, dto.getStatus());
		}
		for (PaymentDto dto : paymentsResponseProcessed.getBody()) {
			assertEquals(TransactionStatusEnum.PROCESSED, dto.getStatus());
		}

		for (DisbursementDto dto : disburmentResponse.getBody()) {
			BigDecimal discount = BigDecimal
					.valueOf(dto.getPayment().doubleValue() - (dto.getPayment().doubleValue() * FEE));

			assertEquals(discount, dto.getDisbursementAmount());
		}

	}

	private String getUrl(String endPoint) {
		return String.format("http://localhost:%d/api/clip/", port).concat(endPoint);
	}

}
