package com.movie.app.service.impl;

import com.movie.app.entities.MovieView;
import com.movie.app.repository.MovieViewRepository;
import com.movie.app.service.MovieViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

@Service
public class MovieViewServiceImpl implements MovieViewService {
    private final Logger log = LoggerFactory.getLogger(MovieViewServiceImpl.class);

    @Autowired
    private MovieViewRepository movieViewRepository;


    @Override
    public List<MovieView> findAll() {
        log.info("[SERViCE] find all movie");
        return movieViewRepository.findAll();
    }
}
