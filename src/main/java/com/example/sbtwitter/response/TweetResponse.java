package com.example.sbtwitter.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TweetResponse {
    private Long id;
    private String tweetBody;
    private List<String> hashTags;
    private String createdBy;
    private String createdAt;
}
