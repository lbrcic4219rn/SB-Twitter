package com.example.sbtwitter.service;

import com.example.sbtwitter.model.HashTag;
import com.example.sbtwitter.repository.HashTagRepository;
import com.example.sbtwitter.service.impl.HashTagServiceImp;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HashTagServiceTest {
    @Mock
    private HashTagRepository hashTagRepository;
    @InjectMocks
    private HashTagServiceImp hashTagService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetByHasTag() {
        HashTag hashTag = new HashTag();
        hashTag.setHashTag("test");
        when(hashTagRepository.findByHashTag("test")).thenReturn(Optional.of(hashTag));

        HashTag result = hashTagService.getByHasTag("test");

        assertEquals(hashTag, result);
    }

    @Test
    public void testGetByHasTagNotFound() {
        when(hashTagRepository.findByHashTag("test")).thenReturn(Optional.empty());

        HashTag result = hashTagService.getByHasTag("test");

        assertNull(result);
    }
}