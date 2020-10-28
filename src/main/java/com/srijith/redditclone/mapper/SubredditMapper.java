package com.srijith.redditclone.mapper;

import java.time.Instant;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.srijith.redditclone.dto.SubredditDto;
import com.srijith.redditclone.model.Post;
import com.srijith.redditclone.model.Subreddit;
import com.srijith.redditclone.model.User;
import com.srijith.redditclone.repository.SubredditRepository;

@Mapper(componentModel="spring")
public abstract class SubredditMapper {
	
	@Autowired
	private SubredditRepository subredditRepository;
	
	@Mapping(target = "numberOfPosts", expression="java(mapPosts(subreddit.getPosts()))")
	public abstract SubredditDto mapSubredditToDto(Subreddit subreddit);

	Integer mapPosts(List<Post> numberOfPosts) { 
		return numberOfPosts.size();
	}
	
	@Mapping(target="posts", ignore=true)
	@Mapping(target="createdDate", expression="java(getCreatedDate(subredditDto.getId()))")
	@Mapping(target="user", expression="java(getTheUser(subredditDto.getId()))")
	public abstract Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
	
	Instant getCreatedDate(Long id) {
		return subredditRepository.findById(id).get().getCreatedDate();
	}
	
	User getTheUser(Long id) {
		return subredditRepository.findById(id).get().getUser();
	}
	
}