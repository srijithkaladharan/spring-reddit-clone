package com.srijith.redditclone.service;

import java.util.List;

import com.srijith.redditclone.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srijith.redditclone.dto.PostRequest;
import com.srijith.redditclone.dto.PostResponse;
import com.srijith.redditclone.exceptions.PostNotFoundException;
import com.srijith.redditclone.exceptions.SpringRedditException;
import com.srijith.redditclone.exceptions.SubredditNotFoundException;
import com.srijith.redditclone.mapper.PostMapper;
import com.srijith.redditclone.model.Post;
import com.srijith.redditclone.model.Subreddit;
import com.srijith.redditclone.repository.PostRepository;
import com.srijith.redditclone.repository.SubredditRepository;
import com.srijith.redditclone.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.toList;


@Service
@AllArgsConstructor
@Slf4j
public class PostService {
	
	private final SubredditRepository subredditRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Transactional
	public void save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
			.orElseThrow(() -> new SpringRedditException(postRequest.getSubredditName()));
		
		User currentUser = authService.getCurrentUser();
		
		Logger logger = LoggerFactory.getLogger(PostService.class);
		logger.info(currentUser.toString());
		postRepository.save(postMapper.map(postRequest, subreddit, currentUser));
	}
	
	
	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException(id.toString()));
		
		return postMapper.mapToDto(post);
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return postRepository.findAll()
				.stream()
				.map(postMapper::mapToDto)
				.collect(toList());
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long subredditId) {
		Subreddit subreddit = subredditRepository.findById(subredditId)
				.orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
		
		List<Post> posts = postRepository.findAllBySubreddit(subreddit);
		Logger logger = LoggerFactory.getLogger(PostService.class);
		logger.info(posts.toString());
		
		return posts
				.stream()
				.map(postMapper::mapToDto)
				.collect(toList());
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		
		return postRepository.findByUser(user)
				.stream()
				.map(postMapper::mapToDto)
				.collect(toList());
	}
}


