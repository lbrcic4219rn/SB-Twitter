package com.example.sbtwitter.repository;

import com.example.sbtwitter.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TweetRepo extends JpaRepository<Tweet, Long>, JpaSpecificationExecutor<Tweet> {
}
