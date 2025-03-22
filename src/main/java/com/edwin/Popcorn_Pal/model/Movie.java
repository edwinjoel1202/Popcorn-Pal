package com.edwin.Popcorn_Pal.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

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
