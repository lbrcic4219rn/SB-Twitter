package com.example.sbtwitter.adapter;

import com.example.sbtwitter.model.HashTag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HashTagAdapter {

    public static Set<HashTag> toHashTagSet(List<String> hashTags) {
        return hashTags.stream().map(
                hashTag -> HashTag.builder().hashTag(hashTag).build()
            ).collect(Collectors.toSet());
    }

    public static List<String> toHashTagStringList(Set<HashTag> hashTags) {
        return hashTags.stream().map(HashTag::getHashTag).collect(Collectors.toList());
    }
}
