package com.example.sbtwitter.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class TweetsPageResponse {
    private List<TweetResponse> tweets;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nextPage = null;
}
