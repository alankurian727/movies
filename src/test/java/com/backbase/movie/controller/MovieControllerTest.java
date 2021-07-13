package com.backbase.movie.controller;

import com.backbase.movie.entity.Movie;
import com.backbase.movie.exception.MovieNotFoundException;
import com.backbase.movie.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc(addFilters = false)
class MovieControllerTest {

    @MockBean
    private MovieService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    public void before(){
        Mockito.reset(service);
    }

    @Test
    void testFindMovie() throws Exception {
        String url = "/movies/titanic";
        Movie movie = new Movie(Long.valueOf(1),"1997","Titanic",true,null,null);
        Mockito.when(service.findByName(Mockito.anyString())).thenReturn(movie);
        RequestBuilder request = MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Mockito.verify(service,times(1)).findByName(Mockito.anyString());
    }

    @Test
    void testFindMovieNotFound() throws Exception {
        String url = "/movies/titanicasd";
        Mockito.when(service.findByName(Mockito.anyString())).thenThrow(new MovieNotFoundException());
        RequestBuilder request = MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Mockito.verify(service,times(1)).findByName(Mockito.anyString());
    }

    @Test
    void testUpdateMovie() throws Exception {
        String url = "/movies/1";
        Movie movie = new Movie(Long.valueOf(1),"1997","Titanic",true,9.2,null);
        Mockito.when(service.updateRating(Mockito.anyLong(),Mockito.anyDouble())).thenReturn(movie);
        RequestBuilder request = MockMvcRequestBuilders.put(url)
                .param("rating", String.valueOf(9))
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(service,times(1)).updateRating(Mockito.anyLong(),Mockito.anyDouble());
    }

    @Test
    void testUpdateMovieNotFound() throws Exception {
        String url = "/movies/1";
        Mockito.when(service.updateRating(Mockito.anyLong(),Mockito.anyDouble())).thenThrow(new MovieNotFoundException());
        RequestBuilder request = MockMvcRequestBuilders.put(url)
                .param("rating", String.valueOf(9))
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
        Mockito.verify(service,times(1)).updateRating(Mockito.anyLong(),Mockito.anyDouble());
    }

}
