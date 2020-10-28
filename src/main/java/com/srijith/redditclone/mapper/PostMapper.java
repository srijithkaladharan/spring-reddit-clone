package com.srijith.redditclone.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.srijith.redditclone.dto.PostRequest;
import com.srijith.redditclone.dto.PostResponse;
import com.srijith.redditclone.model.Post;
import com.srijith.redditclone.model.Subreddit;
import com.srijith.redditclone.model.User;
import com.srijith.redditclone.model.Vote;
import com.srijith.redditclone.repository.CommentRepository;
import com.srijith.redditclone.repository.VoteRepository;
import com.srijith.redditclone.service.AuthService;

import com.srijith.redditclone.model.VoteType;

@Mapper(componentModel="spring")
public abstract class PostMapper {
	
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private AuthService authService;

	
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target = "user", source = "user")
	@Mapping(target = "subreddit", source = "subreddit")
	@Mapping(target = "voteCount", constant = "0")
	public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);
	
	
	@Mapping(target = "id", source="postId")
	@Mapping(target = "subredditName", source="subreddit.name")
	@Mapping(target = "username", source="user.username")
	@Mapping(target = "commentCount", expression="java(commentCount(post))")
	@Mapping(target = "duration", expression="java(getDuration(post))")
	@Mapping(target = "upVote", expression="java(isPostUpVoted(post))")
	@Mapping(target = "downVote", expression="java(isPostDownVoted(post))")
	public abstract PostResponse mapToDto(Post post); 
	
	Integer commentCount(Post post) {
		return commentRepository.findByPost(post).size();
	}
	
	String getDuration(Post post) {
		return TimeAgo.using(post.getCreatedDate().toEpochMilli());
	}
	
	boolean isPostUpVoted(Post post) {
		return checkVoteType(post, VoteType.UPVOTE);
	}
	
	boolean isPostDownVoted(Post post) {
		return checkVoteType(post, VoteType.DOWNVOTE);
	}
	
	private boolean checkVoteType(Post post, VoteType voteType) {
		if (authService.isLoggedIn()) {
			Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
			return voteForPostByUser.filter(vote -> vote.getVotetype().equals(voteType)).isPresent();
		}
		
		return false;
	}
	
	
}
