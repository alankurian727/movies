package com.backbase.movie.controller;

import com.backbase.movie.entity.Movie;
import com.backbase.movie.exception.MovieNotFoundException;
import com.backbase.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for all movie realted methods.
 */
@RestController
@RequestMapping("/movies")
public class MovieController {

    /**
     * The Service.
     */
    MovieService service;

    /**
     * Instantiates a new Movie controller.
     *
     * @param service the service
     */
    @Autowired
    public MovieController(MovieService service) {
        this.service = service;
    }

    /**
     * End point to find movie based on name.
     *
     * @param movieName the movie name
     * @return the movie with best picture details
     * @throws MovieNotFoundException the movie not found exception
     */
    @GetMapping(value = "/{movieName}")
    public ResponseEntity<Movie> getMovie(@PathVariable String movieName) throws MovieNotFoundException {
        System.out.println("inside MovieController : getMovie");
        Movie movie = service.findByName(movieName);
        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }

    /**
     * End point to update movie rating entity.
     *
     * @param id     the id
     * @param rating the rating
     * @return the movie entity with updated rating
     * @throws MovieNotFoundException the movie not found exception
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Movie> updateMovieRating(@PathVariable Long id,@RequestParam("rating") Double rating)
            throws MovieNotFoundException {
        System.out.println("inside MovieController : updateMovieRating");
        Movie movie = service.updateRating(id,rating);
        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }

}
