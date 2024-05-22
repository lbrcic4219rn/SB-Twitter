package com.example.sbtwitter.service;

import com.example.sbtwitter.exception.TweetNotFoundException;
import com.example.sbtwitter.exception.UnauthorisedTweetDeletionException;
import com.example.sbtwitter.model.Tweet;
import com.example.sbtwitter.request.TweetsSearchRequest;
import com.example.sbtwitter.response.TweetsPageResponse;

public interface TweetService {
    Tweet createTweet(Tweet tweet);
    void deleteTweet(Long id, String username) throws TweetNotFoundException, UnauthorisedTweetDeletionException;
    TweetsPageResponse queryTweets(TweetsSearchRequest request);
}
