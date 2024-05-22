package com.example.sbtwitter.adapter;

import com.example.sbtwitter.model.Tweet;
import com.example.sbtwitter.response.TweetResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.sbtwitter.adapter.HashTagAdapter.toHashTagStringList;

public class TweetAdapter {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static TweetResponse toTweetResp(Tweet tweet) {
        TweetResponse tweetResponse = new TweetResponse();
        tweetResponse.setId(tweet.getId());
        tweetResponse.setTweetBody(tweet.getTweetBody());
        tweetResponse.setHashTags(toHashTagStringList(tweet.getHashTags()));
        tweetResponse.setCreatedBy(tweet.getCreatedBy());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        if(tweet.getCreatedAt() != null)
            tweetResponse.setCreatedAt(tweet.getCreatedAt().format(formatter));

        return tweetResponse;
    }

    public static List<TweetResponse> toTweetRespList(List<Tweet> tweets) {
        return tweets.stream().map(TweetAdapter::toTweetResp).collect(Collectors.toList());
    }
}
