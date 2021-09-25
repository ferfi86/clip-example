package com.example.clip.model.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
	private long id;
	private String name;
}
