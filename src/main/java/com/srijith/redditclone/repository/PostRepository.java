package com.srijith.redditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srijith.redditclone.model.Post;
import com.srijith.redditclone.model.Subreddit;
import com.srijith.redditclone.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{
	List<Post> findAllBySubreddit(Subreddit subreddit);
	
	List<Post> findByUser(User user);
}
