package com.example.sbtwitter.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tweets")
@NamedEntityGraph(name = "Tweet.hashTags",
        attributeNodes = @NamedAttributeNode("hashTags"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tweetBody;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "tweet_hash_tag",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hash_tag_id")
    )
    @JsonManagedReference
    private Set<HashTag> hashTags;

    private String createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
