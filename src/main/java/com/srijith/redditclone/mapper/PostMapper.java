package com.srijith.redditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.srijith.redditclone.dto.PostRequest;
import com.srijith.redditclone.dto.PostResponse;
import com.srijith.redditclone.model.Post;
import com.srijith.redditclone.model.Subreddit;
import com.srijith.redditclone.model.User;

@Mapper(componentModel="spring")
public interface PostMapper {

	
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target = "user", source = "user")
	@Mapping(target = "subreddit", source = "subreddit")
	@Mapping(target = "voteCount", constant = "0")
	Post map(PostRequest postRequest, Subreddit subreddit, User user);
	
	
	@Mapping(target = "id", source="postId")
	@Mapping(target = "subredditName", source="subreddit.name")
	@Mapping(target = "username", source="user.username")
	PostResponse mapToDto(Post post); 
	
}
