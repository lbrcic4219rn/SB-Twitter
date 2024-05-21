package com.example.sbtwitter.adapter;

import com.example.sbtwitter.model.Tweet;
import com.example.sbtwitter.response.TweetResp;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.sbtwitter.adapter.HashTagAdapter.toHashTagStringList;

public class TweetAdapter {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static TweetResp toTweetResp(Tweet tweet) {
        TweetResp tweetResp = new TweetResp();
        tweetResp.setId(tweet.getId());
        tweetResp.setTweetBody(tweet.getTweetBody());
        tweetResp.setHashTags(toHashTagStringList(tweet.getHashTags()));
        tweetResp.setCreatedBy(tweet.getCreatedBy());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        tweetResp.setCreatedAt(tweet.getCreatedAt().format(formatter));
        return tweetResp;
    }

    public static List<TweetResp> toTweetRespList(List<Tweet> tweets) {
        return tweets.stream().map(TweetAdapter::toTweetResp).collect(Collectors.toList());
    }
}
