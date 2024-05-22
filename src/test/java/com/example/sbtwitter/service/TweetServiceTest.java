package com.example.sbtwitter.service;

import com.example.sbtwitter.exception.TweetNotFoundException;
import com.example.sbtwitter.exception.UnauthorisedTweetDeletionException;
import com.example.sbtwitter.model.Tweet;
import com.example.sbtwitter.repository.TweetRepository;
import com.example.sbtwitter.request.TweetsSearchRequest;
import com.example.sbtwitter.response.TweetsPageResponse;
import com.example.sbtwitter.service.impl.TweetServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetServiceImpl tweetService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTweet() {
        Tweet tweet = new Tweet();
        when(tweetRepository.save(any(Tweet.class))).thenReturn(tweet);

        Tweet result = tweetService.createTweet(tweet);

        assertEquals(tweet, result);
    }

    @Test
    public void testDeleteTweet() throws TweetNotFoundException, UnauthorisedTweetDeletionException {
        Tweet tweet = new Tweet();
        tweet.setCreatedBy("testUser");
        when(tweetRepository.findById(anyLong())).thenReturn(Optional.of(tweet));

        tweetService.deleteTweet(1L, "testUser");

        verify(tweetRepository, times(1)).delete(tweet);
    }

    @Test(expected = TweetNotFoundException.class)
    public void testDeleteTweetNotFound() throws TweetNotFoundException, UnauthorisedTweetDeletionException {
        when(tweetRepository.findById(anyLong())).thenReturn(Optional.empty());

        tweetService.deleteTweet(1L, "testUser");
    }

    @Test(expected = UnauthorisedTweetDeletionException.class)
    public void testDeleteTweetUnauthorised() throws TweetNotFoundException, UnauthorisedTweetDeletionException {
        Tweet tweet = new Tweet();
        tweet.setCreatedBy("testUser");
        when(tweetRepository.findById(anyLong())).thenReturn(Optional.of(tweet));

        tweetService.deleteTweet(1L, "anotherUser");
    }

    @Test
    public void testQueryTweets() {
        TweetsSearchRequest request = new TweetsSearchRequest();
        Tweet tweet = new Tweet();
        Page<Tweet> tweetPage = new PageImpl<>(Collections.singletonList(tweet));

        Specification<Tweet> spec = any();
        when(tweetRepository.findAll(spec, any(PageRequest.class))).thenReturn(tweetPage);

        TweetsPageResponse result = tweetService.queryTweets(request);

        assertEquals(1, result.getTweets().size());
    }

    @Test
    public void testQueryTweetsTweetsSearchRequest() {
        TweetsSearchRequest request = prepareTweetsSearchRequest();

        Tweet tweet = new Tweet();
        Page<Tweet> tweetPage = new PageImpl<>(List.of(tweet), PageRequest.of(request.getOffset(), request.getLimit()), 2);
        Specification<Tweet> spec = any();

        when(tweetRepository.findAll(spec, any(PageRequest.class))).thenReturn(tweetPage);

        ServletRequestAttributes servletRequestAttributes = mock(ServletRequestAttributes.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);

        when(servletRequestAttributes.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/api/tweets"));
        when(httpServletRequest.getQueryString()).thenReturn("hashTag=test1&hashTag=test2&username=user1&username=user2&limit=1&offset=0");

        TweetsPageResponse result = tweetService.queryTweets(request);

        assertEquals(1, result.getTweets().size());
        assertNotNull(result.getNextPage());
    }

    private TweetsSearchRequest prepareTweetsSearchRequest() {
        TweetsSearchRequest request = new TweetsSearchRequest();
        request.setHashTag(List.of("test1", "test2"));
        request.setUsername(List.of("user1", "user2"));
        request.setLimit(1);
        request.setOffset(0);
        return request;
    }
}
