package com.example.sbtwitter.service;

import com.example.sbtwitter.model.HashTag;
import com.example.sbtwitter.repository.HashTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashTagServiceImp implements HashTagService {

    private final HashTagRepository hashTagRepository;

    @Autowired
    public HashTagServiceImp(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }

    @Override
    public HashTag getByHasTag(String hashTag) {
        return hashTagRepository.findByHashTag(hashTag).orElse(null);
    }
}
