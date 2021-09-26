package com.example.clip.model.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class ClipServiceErrorMessage {
	private int httpStatus;
	private String developerMessage;
	private String message;

}
