package com.example.sbtwitter.controller;

import com.example.sbtwitter.exception.TweetNotFoundException;
import com.example.sbtwitter.exception.UnauthorisedTweetDeletionException;
import com.example.sbtwitter.model.Tweet;
import com.example.sbtwitter.request.PostTweetRequest;
import com.example.sbtwitter.request.TweetsSearchRequest;
import com.example.sbtwitter.response.TweetResponse;
import com.example.sbtwitter.response.TweetsPageResponse;
import com.example.sbtwitter.service.TweetService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.sbtwitter.adapter.HashTagAdapter.toHashTagSet;
import static com.example.sbtwitter.adapter.TweetAdapter.toTweetResp;

@RestController
@RequestMapping(
        value = "/v1/tweets",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@AllArgsConstructor
public class TweetController {
    private final TweetService tweetService;
    @GetMapping
    ResponseEntity<TweetsPageResponse> getTweets(@ModelAttribute @Valid TweetsSearchRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(tweetService.queryTweets(request));
    }

    @PostMapping
    ResponseEntity<TweetResponse> postTweets(@RequestBody @Valid PostTweetRequest tweet, @RequestHeader("X-Username") String username) {

        Tweet createdTweet = tweetService.createTweet(Tweet.builder()
                .createdBy(username)
                .tweetBody(tweet.getTweetBody())
                .hashTags(toHashTagSet(tweet.getHashTags()))
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(toTweetResp(createdTweet));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTweet(@PathVariable Long id, @RequestHeader("X-Username") String username) throws TweetNotFoundException, UnauthorisedTweetDeletionException {
        tweetService.deleteTweet(id, username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
