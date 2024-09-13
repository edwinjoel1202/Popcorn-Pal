/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edwin.Popcorn_Pal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author edwin
 */

@Entity
@Table(name = "Review")
@Getter
@Setter
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID reviewId;

    @ManyToOne
    @JoinColumn(name = "movieId")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private int rating;

    @Column
    private String reviewText;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
