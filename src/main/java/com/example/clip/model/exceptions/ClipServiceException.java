package com.example.clip.model.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ClipServiceException extends Exception {
	
	private static final long serialVersionUID = 6042405982919022887L;
	private ClipServiceErrorMessage clipServiceErrorMessage;
}
