package com.movie.app.repository;

import com.movie.app.entities.MovieReviewView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieReviewViewRepository extends JpaRepository<MovieReviewView, Long>, JpaSpecificationExecutor<MovieReviewView> {
}
