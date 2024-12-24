package com.movie.app.service.impl;


import com.movie.app.entities.Movie;
import com.movie.app.repository.MovieRepository;
import com.movie.app.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private MovieRepository movieRepository;


    @Override
    public Movie findById(Long id) {
        log.info("[SERVICE] find movie  by id {}", id);
        return movieRepository.findById(id).orElseThrow();
    }
}
