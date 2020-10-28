package com.srijith.redditclone.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srijith.redditclone.dto.VoteDto;
import com.srijith.redditclone.exceptions.PostNotFoundException;
import com.srijith.redditclone.exceptions.SpringRedditException;
import com.srijith.redditclone.model.Post;
import com.srijith.redditclone.model.Vote;
import com.srijith.redditclone.repository.PostRepository;
import com.srijith.redditclone.repository.VoteRepository;

import lombok.AllArgsConstructor;

import static com.srijith.redditclone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
	
	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;
	
	
	@Transactional
	public void vote(VoteDto voteDto) {
		
		Post post = postRepository.findById(voteDto.getPostId())
						.orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - "));
		
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		
		if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVotetype().equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already " + voteDto.getVoteType().toString() + "'d for this post");
		}
		
		if(UPVOTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
		}else {
			post.setVoteCount(post.getVoteCount() - 1);
		}
		
		voteRepository.save(mapToVote(voteDto, post));
		
		postRepository.save(post);
		
	}
	
	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder()
					.votetype(voteDto.getVoteType())
					.post(post)
					.user(authService.getCurrentUser())
					.build();
	}
}
