package com.backbase.movie.service;

import com.backbase.movie.entity.Movie;
import com.backbase.movie.exception.MovieNotFoundException;

/**
 * The interface Movie service.
 */
public interface MovieService {

    /**
     * Find movie by name.
     *
     * @param name the name
     * @return the movie
     * @throws MovieNotFoundException the movie not found exception
     */
    Movie findByName(String name) throws MovieNotFoundException;

    /**
     * Update rating movie.
     *
     * @param id     the id
     * @param rating the rating
     * @return the movie
     * @throws MovieNotFoundException the movie not found exception
     */
    Movie updateRating(Long id,Double rating) throws MovieNotFoundException;
}
