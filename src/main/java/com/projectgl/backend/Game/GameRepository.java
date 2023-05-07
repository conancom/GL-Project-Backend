package com.projectgl.backend.Game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findGameByName(String name);

    @Transactional
    Optional<Game> findGameBySearchName(String searchName);
}
