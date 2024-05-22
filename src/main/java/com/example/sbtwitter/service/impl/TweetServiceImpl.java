package com.example.sbtwitter.service.impl;

import com.example.sbtwitter.exception.TweetNotFoundException;
import com.example.sbtwitter.exception.UnauthorisedTweetDeletionException;
import com.example.sbtwitter.model.HashTag;
import com.example.sbtwitter.model.Tweet;
import com.example.sbtwitter.repository.TweetRepository;
import com.example.sbtwitter.repository.TweetSpecification;
import com.example.sbtwitter.request.TweetsSearchRequest;
import com.example.sbtwitter.response.TweetsPageResponse;
import com.example.sbtwitter.service.HashTagService;
import com.example.sbtwitter.service.TweetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.sbtwitter.adapter.TweetAdapter.toTweetRespList;

@Service
@AllArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final HashTagService hashTagService;

    @Override
    @Transactional
    public Tweet createTweet(Tweet tweet) {
        if(tweet.getHashTags() == null) {
            tweet.setHashTags(Set.of());
        }

        Set<HashTag> tags = tweet.getHashTags().stream().map(hashTag -> {
            HashTag existingHashTag = this.hashTagService.getByHasTag(hashTag.getHashTag());
            return Objects.requireNonNullElse(existingHashTag, hashTag);
        }).collect(Collectors.toSet());

        tweet.setHashTags(tags);

        return tweetRepository.save(tweet);
    }

    @Override
    public void deleteTweet(Long id, String username) throws TweetNotFoundException, UnauthorisedTweetDeletionException {
        Tweet tweet = tweetRepository.findById(id).orElseThrow(TweetNotFoundException::new);

        if(!tweet.getCreatedBy().equals(username)) {
            throw new UnauthorisedTweetDeletionException();
        }

        tweetRepository.delete(tweet);
    }

    @Override
    public TweetsPageResponse queryTweets(TweetsSearchRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        //Better would be pageNumber and pageSize format
        Pageable pageable = PageRequest.of(request.getOffset() / request.getLimit(), request.getLimit(), sort);

        Specification<Tweet> spec = generateSpec(request);

        Page<Tweet> tweets = tweetRepository.findAll(spec, pageable);

        TweetsPageResponse resp = new TweetsPageResponse();
        resp.setTweets(toTweetRespList(tweets.getContent()));

        if(tweets.hasNext()) {
            resp.setNextPage(calculateNextPage(request));
        }

        return resp;
    }

    private Specification<Tweet> generateSpec(TweetsSearchRequest request) {
        List<String> hashTags = request.getHashTag();
        List<String> users = request.getUsername();

        Specification<Tweet> spec = Specification.where(null);

        if(hashTags != null && !hashTags.isEmpty()) {
            spec = spec.and(TweetSpecification.hashTagIn(hashTags));
        }

        if(users != null && !users.isEmpty()) {
            spec = spec.and(TweetSpecification.usernameIn(users));
        }

        return spec;
    }

    private String calculateNextPage(TweetsSearchRequest request) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if(sra == null) {
            return null;
        }

        HttpServletRequest req = sra.getRequest();
        String url = req.getRequestURL().toString();
        String queryString = req.getQueryString();

        if (queryString != null) {
            url += "?" + queryString;
        }

        int nextPageNumber = request.getOffset() + request.getLimit();
        return url.replace("offset=" + request.getOffset(), "offset=" + nextPageNumber);
    }
}
