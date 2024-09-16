/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.Actor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edwin.Popcorn_Pal.repository.ActorRepository;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author edwin
 */
@Service
@Transactional
public class actorService {

    @Autowired
    private ActorRepository actorRepository;

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public Optional<Actor> getActorById(int actorId) {
        return actorRepository.findById(actorId);
    }

    public Actor saveActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public void deleteActor(int actorId) {
        actorRepository.deleteById(actorId);
    }
}
