package com.srijith.redditclone.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import  javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Comment {
	@Id
	@GeneratedValue(strategy=SEQUENCE)
	private Long commentId;
	@NotEmpty
	private String text;
	@ManyToOne(fetch=LAZY)
	@JoinColumn(name="postId", referencedColumnName="postId")
	private Post post;
	private Instant createdDate;
	@ManyToOne(fetch=LAZY)
	@JoinColumn(name="userId", referencedColumnName="userId")
	private User user;
}
