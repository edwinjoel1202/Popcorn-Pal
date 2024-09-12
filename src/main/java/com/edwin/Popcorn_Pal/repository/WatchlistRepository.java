/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.edwin.Popcorn_Pal.repository;

import com.edwin.Popcorn_Pal.model.Watchlist;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author edwin
 */

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, UUID>{
    
}
