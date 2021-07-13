package com.backbase.movie.service.impl;

import com.backbase.movie.entity.Movie;
import com.backbase.movie.exception.MovieNotFoundException;
import com.backbase.movie.repository.MovieRepository;
import com.backbase.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for handling all Movie methods.
 */
@Service
public class MovieServiceImpl implements MovieService {

    /**
     * The Repository.
     */
    MovieRepository repository;

    /**
     * Instantiates a new Movie service.
     *
     * @param repository the repository
     */
    @Autowired
    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }

    /**
     * Find movie by name.
     *
     * @param name the name
     * @return the movie
     * @throws MovieNotFoundException the movie not found exception
     */
    public Movie findByName(String name) throws MovieNotFoundException {
        Movie movie= repository.findByNameIgnoreCase(name);
        if(movie!=null){
            return movie;
        }
        throw new MovieNotFoundException("Movie with name "+name+" not found");
    }

    /**
     * Update rating movie.
     *
     * @param id     the id
     * @param rating the rating
     * @return the movie
     * @throws MovieNotFoundException the movie not found exception
     */
    public Movie updateRating(Long id,Double rating) throws MovieNotFoundException{
        Movie movie = repository.findById(id).orElseThrow(MovieNotFoundException::new);
        movie.setRating(rating);
        return repository.save(movie);
    }
}
