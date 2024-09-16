/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.Watchlist;
import jakarta.transaction.Transactional;
import com.edwin.Popcorn_Pal.repository.WatchlistRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author edwin
 */
@Service
@Transactional
public class watchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    public List<Watchlist> getAllWatchlistItems() {
        return watchlistRepository.findAll();
    }

    public Optional<Watchlist> getWatchlistById(UUID watchlistId) {
        return watchlistRepository.findById(watchlistId);
    }

    public Watchlist saveWatchlistItem(Watchlist watchlist) {
        return watchlistRepository.save(watchlist);
    }

    public void deleteWatchlistItem(UUID watchlistId) {
        watchlistRepository.deleteById(watchlistId);
    }
}
