package com.srijith.redditclone.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote {
	@Id
	@GeneratedValue(strategy= SEQUENCE)
	private Long voteId;
	private VoteType votetype;
	@NotNull
	@ManyToOne(fetch=LAZY)
	@JoinColumn(name="postId", referencedColumnName="postId")
	private Post post;
	@ManyToOne(fetch=LAZY)
	@JoinColumn(name="userId", referencedColumnName="userId")
	private User user;
}
