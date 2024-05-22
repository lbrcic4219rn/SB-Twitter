package com.example.sbtwitter.service.impl;

import com.example.sbtwitter.model.HashTag;
import com.example.sbtwitter.repository.HashTagRepository;
import com.example.sbtwitter.service.HashTagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HashTagServiceImp implements HashTagService {

    private final HashTagRepository hashTagRepository;

    @Override
    public HashTag getByHasTag(String hashTag) {
        return hashTagRepository.findByHashTag(hashTag).orElse(null);
    }
}
