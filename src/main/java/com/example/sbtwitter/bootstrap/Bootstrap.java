package com.example.sbtwitter.bootstrap;

import com.example.sbtwitter.model.HashTag;
import com.example.sbtwitter.model.Tweet;
import com.example.sbtwitter.service.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final TweetService tweetService;

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
