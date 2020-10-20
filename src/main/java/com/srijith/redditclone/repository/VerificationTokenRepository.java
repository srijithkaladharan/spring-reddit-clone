package com.srijith.redditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srijith.redditclone.model.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
	Optional<VerificationToken> findByToken(String token);
	
}
