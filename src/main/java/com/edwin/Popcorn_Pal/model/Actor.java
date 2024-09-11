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
@Table(name = "Actor")
@Getter
@Setter
public class Actor 
{
    
    @Id
    private int actorId;
    
    private String name;
    
    private String profilePicture;
    
    @ManyToMany
    @JoinTable(
            name = "Movie_actors",
            joinColumns = @JoinColumn(name = "actorId"),
            inverseJoinColumns = @JoinColumn(name = "movieId")
    )
    private Set<Movie> movies;
}
