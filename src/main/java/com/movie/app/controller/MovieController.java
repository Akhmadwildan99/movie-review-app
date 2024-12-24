package com.movie.app.controller;

import com.movie.app.entities.Movie;
import com.movie.app.entities.MovieView;
import com.movie.app.model.ApiResponse;
import com.movie.app.service.MovieViewService;
import com.movie.app.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MovieController {
    private final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieViewService movieViewService;

    @GetMapping("/movies")
    public ResponseEntity<ApiResponse<?>> findAll() {
        log.info("[REST] request find all movie");

        try {
            List<MovieView> movies = movieViewService.findAll();
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.OK.getReasonPhrase(), "Successes to fetch all movies", HttpStatus.OK.value());
            ApiResponse<List<MovieView>> response = new ApiResponse<>(meta, movies);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            ApiResponse<String> response = new ApiResponse<>(meta, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/movie-details/{id}")
    public ResponseEntity<ApiResponse<?>> findById(@PathVariable Long id) {
        log.info("[REST] request find detail movie {}", id);

        try {
            Movie movieDetail = movieService.findById(id);

            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.OK.getReasonPhrase(), "Successes to fetch  movie detail", HttpStatus.OK.value());
            ApiResponse<Movie> response = new ApiResponse<>(meta, movieDetail);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            ApiResponse<String> response = new ApiResponse<>(meta, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
