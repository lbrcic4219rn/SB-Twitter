package com.example.sbtwitter.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "hash_tags")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    @Pattern(regexp = "^#[a-zA-Z]{2,16}$")
    private String hashTag;


    @ManyToMany(mappedBy = "hashTags")
    @JsonBackReference
    private Set<Tweet> tweets = new HashSet<>();
}
