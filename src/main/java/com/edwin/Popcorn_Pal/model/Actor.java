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
@Table(name = "actors")
@Getter
@Setter
public class Actor 
{
    
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
