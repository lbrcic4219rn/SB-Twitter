package com.example.sbtwitter.service;

import com.example.sbtwitter.exception.TweetNotFoundException;
import com.example.sbtwitter.exception.UnauthorisedTweetDeletionException;
import com.example.sbtwitter.model.Tweet;
import com.example.sbtwitter.request.TweetsSearchRequest;
import com.example.sbtwitter.response.TweetsPageResponse;

/**
 * Service for Tweet operations.
 */
public interface TweetService {
    /**
     * Creates a new Tweet.
     *
     * @param tweet the Tweet object to create.
     * @return the created Tweet object.
     */
    Tweet createTweet(Tweet tweet);

    /**
     * Deletes a Tweet.
     *
     * @param id the ID of the Tweet to delete.
     * @param username the username of the user attempting to delete the Tweet.
     * @throws TweetNotFoundException if the Tweet with the provided ID does not exist.
     * @throws UnauthorisedTweetDeletionException if the user attempting to delete the Tweet is not the creator of the Tweet.
     */
    void deleteTweet(Long id, String username) throws TweetNotFoundException, UnauthorisedTweetDeletionException;

    /**
     * Fetches Tweets based on the provided search criteria. \
     * Criteria includes offset, limit, username, and hashTags.
     *
     * @param request the search criteria.
     * @return the TweetsPageResponse object containing the search results.
     */
    TweetsPageResponse queryTweets(TweetsSearchRequest request);
}
