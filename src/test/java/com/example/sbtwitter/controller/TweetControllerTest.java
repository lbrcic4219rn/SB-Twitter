package com.example.sbtwitter.controller;

import com.example.sbtwitter.exception.TweetNotFoundException;
import com.example.sbtwitter.exception.UnauthorisedTweetDeletionException;
import com.example.sbtwitter.model.Tweet;
import com.example.sbtwitter.request.PostTweetRequest;
import com.example.sbtwitter.request.TweetsSearchRequest;
import com.example.sbtwitter.response.TweetResponse;
import com.example.sbtwitter.response.TweetsPageResponse;
import com.example.sbtwitter.service.TweetService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.sbtwitter.adapter.TweetAdapter.toTweetResp;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TweetControllerTest {

    @Mock
    private TweetService tweetService;

    @InjectMocks
    private TweetController tweetController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getTweets() {
        TweetsSearchRequest request = new TweetsSearchRequest();
        TweetsPageResponse response = new TweetsPageResponse();
        when(tweetService.queryTweets(request)).thenReturn(response);

        ResponseEntity<TweetsPageResponse> result = tweetController.getTweets(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    public void postTweets() {
        PostTweetRequest request = new PostTweetRequest();
        Tweet tweet = new Tweet();
        request.setHashTags(List.of("test"));
        when(tweetService.createTweet(any(Tweet.class))).thenReturn(tweet);

        ResponseEntity<TweetResponse> result = tweetController.postTweets(request, "testUser");

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(toTweetResp(tweet), result.getBody());
    }

    @Test
    public void deleteTweet() throws TweetNotFoundException, UnauthorisedTweetDeletionException {
        doNothing().when(tweetService).deleteTweet(anyLong(), anyString());

        ResponseEntity<?> result = tweetController.deleteTweet(1L, "testUser");

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test(expected = TweetNotFoundException.class)
    public void deleteTweetNotFound() throws TweetNotFoundException, UnauthorisedTweetDeletionException {
        doThrow(new TweetNotFoundException()).when(tweetService).deleteTweet(anyLong(), anyString());

        ResponseEntity<?> result = tweetController.deleteTweet(1L, "testUser");

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test(expected = UnauthorisedTweetDeletionException.class)
    public void deleteTweetUnauthorised() throws TweetNotFoundException, UnauthorisedTweetDeletionException {
        doThrow(new UnauthorisedTweetDeletionException()).when(tweetService).deleteTweet(anyLong(), anyString());

        ResponseEntity<?> result = tweetController.deleteTweet(1L, "testUser");
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }
}