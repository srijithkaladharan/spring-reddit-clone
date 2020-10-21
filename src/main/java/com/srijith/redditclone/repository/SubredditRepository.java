package com.srijith.redditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srijith.redditclone.model.Subreddit;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
	Optional<Subreddit> findByName(String subredditName);
}
