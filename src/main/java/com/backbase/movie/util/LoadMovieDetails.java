package com.backbase.movie.util;

import com.backbase.movie.entity.Movie;
import com.backbase.movie.repository.MovieRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This class Load movie details to h2 in memory database while application is build.
 */
@Component
@Generated
public class LoadMovieDetails {

    /**
     * The Repo.
     */
    @Autowired
    MovieRepository repo;

    /**
     * The Resource loader.
     */
    @Autowired
    ResourceLoader resourceLoader;

    /**
     * Load movies when application is building
     * This will reduce an extra trigger or end point to read and persist data from csv to database
     *
     * @throws IOException the io exception
     */
    @PostConstruct
    public void loadMovies() throws IOException {
        System.out.println("Entering LoadMovieDetails:loadMovies");
        Movie movie;
        List<Movie> movies = new ArrayList<>();
        String[] line;
        FileReader filereader = null;
        CSVReader csvReader = null;
        try {
            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "academy_awards.csv");
            filereader = new FileReader(file);
            csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            while ((line = csvReader.readNext()) != null) {
                if("Best Picture".equals(line[1])){
                    movie= new Movie();
                    movie.setYear(line[0]);
                    movie.setName(line[2]);
                    if("YES".equals(line[4]))
                        movie.setIsBestPicture(Boolean.TRUE);
                    else
                        movie.setIsBestPicture(Boolean.FALSE);
                    movies.add(movie);

                }
            }
            repo.saveAll(movies);
            System.out.println( "CSV Data read and saved successfully");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            filereader.close();
            csvReader.close();
        }
    }

}
