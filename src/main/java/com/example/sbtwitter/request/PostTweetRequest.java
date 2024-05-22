package com.example.sbtwitter.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class PostTweetRequest {
    @NotEmpty(message = "Tweet body cannot be empty")
    @NotNull(message = "Tweet body cannot be empty")
    @Size(max = 320, message = "Tweet body cannot be more than 320 characters")
    private String tweetBody;


    @Size(max = 5, message = "Maximum 5 hashTags are allowed")
    private List<
            @Pattern(regexp = "^#[a-zA-Z]{2,16}$", message = "Invalid HashTag format")
            String> hashTags;

}
