package com.movie.app.service.impl;


import com.movie.app.entities.MovieReview;
import com.movie.app.entities.User;
import com.movie.app.repository.MovieReviewRepository;
import com.movie.app.repository.MovieReviewViewRepository;
import com.movie.app.repository.UserRepository;
import com.movie.app.service.MovieReviewService;
import com.movie.app.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class MovieReviewServiceImpl implements MovieReviewService {
    private final Logger log = LoggerFactory.getLogger(MovieReviewServiceImpl.class);

    @Autowired
    private MovieReviewRepository movieReviewRepository;

    @Autowired
    private MovieReviewViewRepository movieReviewViewRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public MovieReview create(MovieReview movieReview) {
        log.info("[SERVICE] create review {}", movieReview);
        User user = userRepository.findByUsername(Utils.getEmailFromToken()).orElseThrow();

        if(movieReview.getId() != null) {
            throw  new RuntimeException("Id must be null");
        }

        if(movieReview.getMovie() == null) {
            throw  new RuntimeException("movie detail must not be null");
        }

        movieReview.setCreatedAt(Instant.now());
        movieReview.setUser(user);

        return movieReviewRepository.save(movieReview);
    }

    @Override
    public MovieReview update(MovieReview movieReview) {
        log.info("[SERVICE] update review {}", movieReview);
        if(movieReview.getId() == null) {
            throw  new RuntimeException("Id must not be null");
        }

        if(!movieReviewRepository.existsById(movieReview.getId())) {
            throw  new RuntimeException("review with spesific id is not exist");
        }

        MovieReview movieReview1 = movieReviewRepository.findById(movieReview.getId()).orElseThrow();

        if(!movieReview1.getUser().getUsername().equals(Utils.getEmailFromToken())) {
            throw  new RuntimeException("user has not authorize to update this review");
        }

        if(movieReview.getContent() != null) {
            movieReview1.setContent(movieReview.getContent());
        }

        if(movieReview.getRating() != null) {
            movieReview1.setRating(movieReview.getRating());
        }

        movieReview1.setUpdatedAt(Instant.now());

        return movieReviewRepository.save(movieReview1);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("[SERVICE] delete review {}", id);

        if(!movieReviewRepository.existsById(id)) {
            throw  new RuntimeException("review with spesific id is not exist");
        }

        MovieReview movieReview1 = movieReviewRepository.findById(id).orElseThrow();

        if(!movieReview1.getUser().getUsername().equals(Utils.getEmailFromToken())) {
            throw  new RuntimeException("user has not authorize to delete this review");
        }

        movieReviewViewRepository.deleteById(id);
    }
}
