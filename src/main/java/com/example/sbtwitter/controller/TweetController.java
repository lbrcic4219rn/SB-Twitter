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


/**
 * Controller for handling Tweet related operations.
 */
@RestController
@RequestMapping(
        value = "/v1/tweets",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@AllArgsConstructor
public class TweetController {
    private final TweetService tweetService;

    /**
     * Fetches Tweets based on the provided search criteria.
     * Criteria includes offset, limit, username, and hashTags.
     *
     * @param request the search criteria.
     * @return the TweetsPageResponse object containing the search results.
     */
    @GetMapping
    ResponseEntity<TweetsPageResponse> getTweets(@ModelAttribute @Valid TweetsSearchRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(tweetService.queryTweets(request));
    }

    /**
     * Creates a new Tweet.
     *
     * @param tweet the Tweet object to create.
     * @param username the username of the user creating the Tweet.
     * @return the created Tweet object.
     */
    @PostMapping
    ResponseEntity<TweetResponse> postTweets(@RequestBody @Valid PostTweetRequest tweet, @RequestHeader("X-Username") String username) {

        Tweet createdTweet = tweetService.createTweet(Tweet.builder()
                .createdBy(username)
                .tweetBody(tweet.getTweetBody())
                .hashTags(toHashTagSet(tweet.getHashTags()))
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(toTweetResp(createdTweet));
    }


    /**
     * Deletes a tweet specified by id.
     *
     * @param id id of tweet to be deleted
     * @param username username of user requesting deletion
     * @return returns response OK if deletion was a success
     * @throws TweetNotFoundException if tweet with id does not exist
     * @throws UnauthorisedTweetDeletionException if user requesting deletion is not the creator of tweet
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTweet(@PathVariable Long id, @RequestHeader("X-Username") String username) throws TweetNotFoundException, UnauthorisedTweetDeletionException {
        tweetService.deleteTweet(id, username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
