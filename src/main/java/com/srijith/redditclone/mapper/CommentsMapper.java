package com.srijith.redditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.srijith.redditclone.dto.CommentsDto;
import com.srijith.redditclone.model.Comment;
import com.srijith.redditclone.model.Post;
import com.srijith.redditclone.model.User;

@Mapper(componentModel="spring")
public interface CommentsMapper {
	
	@Mapping(target="commentId", ignore=true)
	@Mapping(target="text", source="commentsDto.text")
	@Mapping(target="createdDate", expression="java(java.time.Instant.now())")
	@Mapping(target="post", source="post")
	Comment map(CommentsDto commentsDto, Post post, User user);
	
	@Mapping(target="id", source="comment.commentId")
	@Mapping(target="postId", expression="java(comment.getPost().getPostId())")
	@Mapping(target="username", expression="java(comment.getUser().getUsername())")
	CommentsDto mapToDto(Comment comment);
}
