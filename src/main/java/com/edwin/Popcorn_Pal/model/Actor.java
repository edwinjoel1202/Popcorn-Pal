/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edwin.Popcorn_Pal.model;

import jakarta.persistence.*;
import java.util.Set;
/**
 *
 * @author edwin
 */
@Entity
@Table(name = "actors")
public class Actor 
{

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int actorId;

    @Column(nullable = false)
    private String name;

    @Column
    private String profilePicture;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies;
}
