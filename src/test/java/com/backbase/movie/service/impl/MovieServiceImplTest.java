package com.backbase.movie.service.impl;

import com.backbase.movie.entity.Movie;
import com.backbase.movie.exception.MovieNotFoundException;
import com.backbase.movie.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MovieServiceImplTest {

    @InjectMocks
    MovieServiceImpl movieService;

    @Mock
    MovieRepository repo;


    @Test
    void testFindMovie() throws Exception {
        Movie movie = new Movie(Long.valueOf(1),"1997","Titanic",true,null,null);
        Mockito.when(repo.findByNameIgnoreCase(Mockito.anyString())).thenReturn(movie);
        Movie movieOut = movieService.findByName("Titanic");
        assertEquals(movieOut.getIsBestPicture(),true);
        Mockito.verify(repo,times(1)).findByNameIgnoreCase(Mockito.anyString());
    }

    @Test
    void testFindMovieNotFoundException(){
        Movie movie = null;
        Mockito.when(repo.findByNameIgnoreCase(Mockito.anyString())).thenReturn(movie);
        assertThrows(MovieNotFoundException.class, () -> movieService.findByName("asd") );
        Mockito.verify(repo,times(1)).findByNameIgnoreCase(Mockito.anyString());
    }

    @Test
    void testUpdateMovie() throws Exception {
        Movie movie = new Movie(Long.valueOf(1),"1997","Titanic",true,null,null);
        Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(movie));
        movie.setRating(9.5);
        Mockito.when(repo.save(Mockito.any(Movie.class))).thenReturn(movie);
        Movie movieOut = movieService.updateRating(80L,9.5);
        assertEquals(movieOut.getIsBestPicture(),true);
        assertEquals(movieOut.getRating(),9.5);
        Mockito.verify(repo,times(1)).findById(Mockito.anyLong());
        Mockito.verify(repo,times(1)).save(Mockito.any(Movie.class));
    }

    @Test
    void testUpdateMovieNotFoundException(){
        Movie movie = new Movie(Long.valueOf(1),"1997","Titanic",true,null,null);
        Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
         assertThrows(MovieNotFoundException.class,()->movieService.updateRating(80L,9.5));
        //assertEquals(movieOut.getIsBestPicture(),true);
        Mockito.verify(repo,times(1)).findById(Mockito.anyLong());
        //Mockito.verify(repo,times(1)).save(Mockito.any(Movie.class));
    }
}