package com.srijith.redditclone.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.srijith.redditclone.dto.SubredditDto;
import com.srijith.redditclone.model.Post;
import com.srijith.redditclone.model.Subreddit;

@Mapper(componentModel="spring")
public interface SubredditMapper {
	
	@Mapping(target = "numberOfPosts", expression="java(mapPosts(subreddit.getPosts()))")
	SubredditDto mapSubredditToDto(Subreddit subreddit);

	default Integer mapPosts(List<Post> numberOfPosts) { return numberOfPosts.size(); }
	
	@InheritInverseConfiguration
	@Mapping(target="posts", ignore=true)
	@Mapping(target="createdDate", ignore=true)
	@Mapping(target="user", ignore=true)
	Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}
