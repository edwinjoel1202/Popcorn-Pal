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
@Table(name = "Watchlist")
@Getter
@Setter
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID watchlistId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(nullable = false)
    private LocalDateTime addedAt = LocalDateTime.now();
}
