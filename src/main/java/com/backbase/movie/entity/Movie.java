package com.backbase.movie.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String year;

    @Column
    private String name;

    @Column
    private Boolean isBestPicture;

    @Column
    private Double rating;

    @Column
    private Double boxOfficeCollection;



}
