package com.srijith.redditclone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srijith.redditclone.dto.AuthenticationResponse;
import com.srijith.redditclone.dto.LoginRequest;
import com.srijith.redditclone.dto.RefreshTokenRequest;
import com.srijith.redditclone.dto.RegisterRequest;
import com.srijith.redditclone.exceptions.SpringRedditException;
import com.srijith.redditclone.model.NotificationEmail;
import com.srijith.redditclone.model.User;
import com.srijith.redditclone.model.VerificationToken;
import com.srijith.redditclone.repository.UserRepository;
import com.srijith.redditclone.repository.VerificationTokenRepository;
import com.srijith.redditclone.security.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;
	
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		
		userRepository.save(user);
		
		String token = generateVerificationToken(user);
		
		mailService.sendMail(new NotificationEmail("Please Activate your Account", user.getEmail(), "Thank you for signing up to Spring Reddit, " + 
			"Please click on the below link to activate your account : " + 
			"http://localhost:8080/api/auth/accountVerification/" + token));
	}
	
	@Transactional
	public String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		
		verificationTokenRepository.save(verificationToken);
		return token;
	}	
	
	public void verifyAccount(String token) {
		verificationTokenRepository.findByToken(token);
		
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token); 
		verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token")); 
		
		fetchUserAndEnable(verificationToken.get());
		
	}
	
	public void fetchUserAndEnable(VerificationToken verificationToken){
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found with name - " + username));
		
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), 
				loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		
		String token = jwtProvider.generateToken(authenticate);
		
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(refreshTokenService.generateRefreshToken().getToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()).toString())
				.username(loginRequest.getUsername())
				.build();
	}
	
	@Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }
	
	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
		String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
		
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken("")
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()).toString())
				.username(refreshTokenRequest.getUsername())
				.build();
	}

	public boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}
}
