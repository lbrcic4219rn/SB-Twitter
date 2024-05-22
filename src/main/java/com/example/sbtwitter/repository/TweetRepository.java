package com.example.sbtwitter.repository;

import com.example.sbtwitter.model.HashTag;
import com.example.sbtwitter.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for managing {@link Tweet} entities.
 * Provides basic CRUD operations and additional query methods.
 */
public interface TweetRepository extends JpaRepository<Tweet, Long>, JpaSpecificationExecutor<Tweet> {
}
