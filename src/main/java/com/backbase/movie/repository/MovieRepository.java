package com.backbase.movie.repository;

import com.backbase.movie.entity.Movie;
import lombok.Generated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Generated
public interface MovieRepository extends JpaRepository<Movie,Long> {

    Movie findByNameIgnoreCase(String name);
}
