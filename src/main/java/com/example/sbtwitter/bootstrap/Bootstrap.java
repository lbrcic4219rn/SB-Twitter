package com.example.sbtwitter.bootstrap;

import com.example.sbtwitter.model.HashTag;
import com.example.sbtwitter.model.Tweet;
import com.example.sbtwitter.request.PostTweetReq;
import com.example.sbtwitter.service.HashTagService;
import com.example.sbtwitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Bootstrap implements CommandLineRunner {

    private final TweetService tweetService;
    private final HashTagService hashTagService;

    @Autowired
    public Bootstrap(TweetService tweetService, HashTagService hashTagService) {
        this.tweetService = tweetService;
        this.hashTagService = hashTagService;
    }

    @Override
    public void run(String... args) {

        Tweet firstTweet = Tweet.builder()
                .createdBy("admin")
                .tweetBody("Hello World!")
                .build();

        HashTag helloTag =  HashTag.builder()
                    .hashTag("#hello")
                    .build();

        HashTag worldTag = HashTag.builder()
                    .hashTag("#world")
                    .build();

        firstTweet.setHashTags(Set.of(helloTag, worldTag));

        tweetService.createTweet(firstTweet);
        System.out.println("Data loaded!");
    }
}
