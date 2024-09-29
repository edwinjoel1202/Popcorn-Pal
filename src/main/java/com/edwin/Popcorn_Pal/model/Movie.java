package com.edwin.Popcorn_Pal.model;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use AUTO for UUID or IDENTITY for incrementing numbers
    private Long movieId; // Changed from UUID to Long

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String releaseDate;

    @Column(nullable = false)
    private String posterUrl;

    @Column(nullable = false)
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
