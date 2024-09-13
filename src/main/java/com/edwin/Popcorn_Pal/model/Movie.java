/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edwin.Popcorn_Pal.model;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;



/**
 *
 * @author edwin
 */

@Entity
@Table(name = "Movie")
@Getter
@Setter
public class Movie {
    
    @Id
    private int movieId;
    
    @Column(nullable = false)
    private String title;
    
    @Column
    private String releaseDate;
    
    @Column
    private String posterUrl;
    
    @Column
    private String description;
    
    @ManyToMany(mappedBy = "movies")
    private Set<Actor> actors;
}
