package com.example.sbtwitter.controller;

import com.example.sbtwitter.exception.TweetNotFoundException;
import com.example.sbtwitter.model.Tweet;
import com.example.sbtwitter.request.PostTweetReq;
import com.example.sbtwitter.request.TweetsSearchRequest;
import com.example.sbtwitter.service.TweetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.sbtwitter.adapter.HashTagAdapter.toHashTagSet;
import static com.example.sbtwitter.adapter.TweetAdapter.toTweetResp;

@RestController
@RequestMapping(
        value = "/v1/tweets",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping
    ResponseEntity<?> getTweets(@ModelAttribute @Valid TweetsSearchRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(tweetService.queryTweets(request));
    }

    @PostMapping
    ResponseEntity<?> postTweets(@RequestBody @Valid PostTweetReq tweet, @RequestHeader("X-Username") String username) {

        Tweet createdTweet = tweetService.createTweet(Tweet.builder()
                .createdBy(username)
                .tweetBody(tweet.getTweetBody())
                .hashTags(toHashTagSet(tweet.getHashTags()))
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(toTweetResp(createdTweet));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTweet(@PathVariable Long id, @RequestHeader("X-Username") String username) throws TweetNotFoundException {
        tweetService.deleteTweet(id, username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
