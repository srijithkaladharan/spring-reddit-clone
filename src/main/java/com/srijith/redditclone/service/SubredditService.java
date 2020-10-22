package com.srijith.redditclone.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srijith.redditclone.dto.SubredditDto;
import com.srijith.redditclone.exceptions.SpringRedditException;
import com.srijith.redditclone.mapper.SubredditMapper;
import com.srijith.redditclone.model.Subreddit;
import com.srijith.redditclone.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
	
	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;
	
	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
		subredditDto.setId(save.getId());
		return subredditDto;
	}
	
	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll()
			.stream()
			.map(subredditMapper::mapSubredditToDto)
			.collect(toList());
	}
	
//	@Transactional(readOnly=true)
	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(() -> new SpringRedditException("No Subreddit found with id : " + id.toString()));
		
		return subredditMapper.mapSubredditToDto(subreddit);
	}
}