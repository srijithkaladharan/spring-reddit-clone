package com.srijith.redditclone.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.FetchType.LAZY;


import java.util.List;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddit {
	@Id
	@GeneratedValue(strategy = SEQUENCE)
	private Long id;
	@NotBlank(message = "Community Name is Required")
	private String name;
	@NotBlank(message = "Description is Required")
	private String description;
	@OneToMany(fetch = LAZY)
	private List<Post> posts;
	private Instant createdDate;
	@ManyToOne(fetch = LAZY)
	private User user;
}
