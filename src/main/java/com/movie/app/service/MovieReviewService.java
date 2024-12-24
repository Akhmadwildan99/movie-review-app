package com.movie.app.service;

import com.movie.app.entities.MovieReview;

public interface MovieReviewService {
    MovieReview create(MovieReview movieReview);
    MovieReview update(MovieReview movieReview);
    void delete(Long id);
}
