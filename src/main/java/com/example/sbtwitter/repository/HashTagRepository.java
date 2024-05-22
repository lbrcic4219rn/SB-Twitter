package com.example.sbtwitter.repository;

import com.example.sbtwitter.model.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link HashTag} entities.
 * Provides basic CRUD operations and additional query methods.
 */

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    /**
     * Returns Optional object for hashtags
     *
     * @param hashTag the hashtag to filter by
     * @return Optional containing found tweets, or empty if no hashtags found
     */
    Optional<HashTag> findByHashTag(String hashTag);

}
