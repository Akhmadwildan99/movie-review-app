package com.movie.app.repository;

import com.movie.app.entities.MovieReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieReviewRepository extends JpaRepository<MovieReview, Long>, JpaSpecificationExecutor<MovieReview> {
    @Modifying
    @Query(value = "delete from movie_reviews where id = :movieId", nativeQuery = true)
    void removeById(@Param("movieId") Long movieId);
}
