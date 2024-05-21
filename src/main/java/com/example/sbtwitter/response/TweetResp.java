package com.example.sbtwitter.response;

import com.example.sbtwitter.model.Tweet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.sbtwitter.adapter.TweetAdapter.toTweetResp;

@Data
@NoArgsConstructor
public class TweetResp {
    private Long id;
    private String tweetBody;
    private List<String> hashTags;
    private String createdBy;
    private String createdAt;

    public TweetResp(Tweet tweet) {
        TweetResp tweetResp = toTweetResp(tweet);
        this.id = tweetResp.getId();
        this.tweetBody = tweetResp.getTweetBody();
        this.hashTags = tweetResp.getHashTags();
        this.createdBy = tweetResp.getCreatedBy();
        this.createdAt = tweetResp.getCreatedAt();
    }
}
