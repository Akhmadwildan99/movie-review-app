package com.movie.app.service;

import com.movie.app.entities.Movie;

import java.util.List;

public interface MovieService {
    Movie findById(Long id);
}
