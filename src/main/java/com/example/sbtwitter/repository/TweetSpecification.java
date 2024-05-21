package com.example.sbtwitter.repository;

import com.example.sbtwitter.model.HashTag;
import com.example.sbtwitter.model.Tweet;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class TweetSpecification {

    public static Specification<Tweet> hashTagIn(List<String> hashTags) {
        return (root, query, criteriaBuilder) -> {
            Join<Tweet, HashTag> hashTagsJoin = root.join("hashTags");
            return hashTagsJoin.get("hashTag").in(hashTags);
        };
    }

    public static Specification<Tweet> usernameIn(List<String> usernames) {
        return (root, query, criteriaBuilder) -> root.get("createdBy").in(usernames);
    }
}
