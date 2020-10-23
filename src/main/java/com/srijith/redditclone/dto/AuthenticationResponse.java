package com.srijith.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {
	private String authenticationToken;
	private String username;
	private String expiresAt;
	private String refreshToken;
}
