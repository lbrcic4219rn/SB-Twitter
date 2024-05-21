package com.example.sbtwitter.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.List;

@Data
public class TweetsSearchRequest {

    private List<String> hashTag;
    private List<String> username;

    @Min(value = 0, message = "Minimum offset is 0")
    private Integer offset = 0;

    @Min(value = 1, message = "Minimum limit is 1")
    @Max(value = 100, message = "Maximum limit is 100")
    private Integer limit = 50;

}
