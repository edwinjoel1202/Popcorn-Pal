/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edwin.Popcorn_Pal.model;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author edwin
 */

@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID movieId;

    @Column(nullable = false)
    private String title;

    @Column
    private String releaseDate;

    @Column
    private String posterUrl;

    @Column
    private String description;

    @ManyToMany(mappedBy = "watchlist")
    private Set<User> users;

    @ManyToMany
    @JoinTable(
        name = "movie_actors",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<Review> reviews;
}
