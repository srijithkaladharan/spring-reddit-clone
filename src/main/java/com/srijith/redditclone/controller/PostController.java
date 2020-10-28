package com.srijith.redditclone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srijith.redditclone.dto.PostRequest;
import com.srijith.redditclone.dto.PostResponse;
import com.srijith.redditclone.service.PostService;

import static org.springframework.http.ResponseEntity.status;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
		postService.save(postRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<PostResponse>> getAllPosts() {
		return status(HttpStatus.OK).body(postService.getAllPosts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
		return status(HttpStatus.OK).body(postService.getPost(id));
	}
	
	@GetMapping("by-subreddit/{id}")
	public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id) {
		return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
	}
	
	@GetMapping("by-username/{username}")
	public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
		return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
	}
	
	
}
