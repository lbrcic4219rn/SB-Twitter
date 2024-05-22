package com.example.sbtwitter.adapter;

import com.example.sbtwitter.model.HashTag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HashTagAdapter {

    public static Set<HashTag> toHashTagSet(List<String> hashTags) {
        if(hashTags == null)
            return Set.of();
        return hashTags.stream().map(
                hashTag -> HashTag.builder().hashTag(hashTag).build()
            ).collect(Collectors.toSet());
    }

    public static List<String> toHashTagStringList(Set<HashTag> hashTags) {
        if(hashTags == null)
            return List.of();
        return hashTags.stream().map(HashTag::getHashTag).collect(Collectors.toList());
    }
}
