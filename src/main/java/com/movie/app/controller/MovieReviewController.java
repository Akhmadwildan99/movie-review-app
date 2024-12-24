package com.movie.app.controller;


import com.movie.app.entities.MovieReview;
import com.movie.app.model.ApiResponse;
import com.movie.app.service.MovieReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class MovieReviewController {
    private final Logger log = LoggerFactory.getLogger(MovieReviewController.class);

    @Autowired
    private MovieReviewService movieReviewService;

    @PostMapping("/movie-reviews")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody MovieReview movieReview) {
        log.info("[REST] create movie review, {}", movieReview);

        try {
            MovieReview save = movieReviewService.create(movieReview);
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.CREATED.getReasonPhrase(), "Successes to create review", HttpStatus.CREATED.value());
            ApiResponse<MovieReview> response = new ApiResponse<>(meta, save);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage(), HttpStatus.BAD_REQUEST.value());
            ApiResponse<String> response = new ApiResponse<>(meta, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            ApiResponse<String> response = new ApiResponse<>(meta, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/movie-reviews/{id}")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable Long id, @RequestBody MovieReview movieReview) {
        log.info("[REST] update movie review, {}", movieReview);

        movieReview.setId(id);
        try {
            MovieReview save = movieReviewService.update(movieReview);
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.OK.getReasonPhrase(), "Successes to update review", HttpStatus.OK.value());
            ApiResponse<MovieReview> response = new ApiResponse<>(meta, save);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage(), HttpStatus.BAD_REQUEST.value());
            ApiResponse<String> response = new ApiResponse<>(meta, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            ApiResponse<String> response = new ApiResponse<>(meta, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/movie-reviews/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id) {
        log.info("[REST] delete movie review, {}", id);

        try {
            movieReviewService.delete(id);
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.NO_CONTENT.getReasonPhrase(), "Successes to delete review", HttpStatus.NO_CONTENT.value());
            ApiResponse<String> response = new ApiResponse<>(meta, null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage(), HttpStatus.BAD_REQUEST.value());
            ApiResponse<String> response = new ApiResponse<>(meta, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            ApiResponse<String> response = new ApiResponse<>(meta, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
