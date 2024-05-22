package com.example.sbtwitter.service;

import com.example.sbtwitter.model.HashTag;

/**
 * Service interface for HashTag related operations.
 */
public interface HashTagService {
    /**
     * Fetches a HashTag by its tag string.
     *
     * @param hashTag the string representation of the HashTag to fetch.
     * @return the HashTag object associated with the provided tag string, returns null if not found.
     */
    HashTag getByHasTag(String hashTag);
}
