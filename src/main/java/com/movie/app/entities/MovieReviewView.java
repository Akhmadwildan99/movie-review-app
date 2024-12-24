package com.movie.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
@Data
@Builder
@RequiredArgsConstructor
@Entity
@Table(name = "movie_reviews")
@AllArgsConstructor
public class MovieReviewView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Integer rating;
    private Instant createdAt;
    private Instant updatedAt;
}
