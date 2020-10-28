package com.srijith.redditclone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.srijith.redditclone.dto.CommentsDto;
import com.srijith.redditclone.exceptions.PostNotFoundException;
import com.srijith.redditclone.exceptions.UsernameNotFoundException;
import com.srijith.redditclone.mapper.CommentsMapper;
import com.srijith.redditclone.model.Comment;
import com.srijith.redditclone.model.NotificationEmail;
import com.srijith.redditclone.model.Post;
import com.srijith.redditclone.model.User;
import com.srijith.redditclone.repository.CommentRepository;
import com.srijith.redditclone.repository.PostRepository;
import com.srijith.redditclone.repository.UserRepository;

import lombok.AllArgsConstructor;
import static java.util.stream.Collectors.toList;


@Service
@AllArgsConstructor
public class CommentsService {
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentsMapper commentsMapper;
	private final CommentRepository commentRepository;
	
	private final MailService mailService;
	private final MailContentBuilder mailContentBuilder;

	public void save(CommentsDto commentsDto) {
		Post post = postRepository.findById(commentsDto.getPostId())
			.orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
		
		Comment comment = commentsMapper.map(commentsDto, post, authService.getCurrentUser());
		
		commentRepository.save(comment);
		
		String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your Post." );
		sendCommentNotification(message, post.getUser());
	}
	
	
	private void sendCommentNotification(String message, User user) {
		//mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
		String username = user.getUsername();
		mailService.sendMail(new NotificationEmail(username + " Commented on your post", user.getEmail(), message));
	}


	public List<CommentsDto> getAllCommentsForPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
		
		return commentRepository.findByPost(post)
									.stream()
									.map(commentsMapper::mapToDto)
									.collect(toList());
	}


	public List<CommentsDto> getAllCommentsForUser(String username) {
		User user = userRepository.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException(username));
		
		return commentRepository.findAllByUser(user)
					.stream()
					.map(commentsMapper::mapToDto)
					.collect(toList());
	}
	
	
}
