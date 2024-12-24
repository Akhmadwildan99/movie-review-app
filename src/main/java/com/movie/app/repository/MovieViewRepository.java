package com.movie.app.repository;

import com.movie.app.entities.MovieView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieViewRepository extends JpaRepository<MovieView, Long>, JpaSpecificationExecutor<MovieView> {
}
