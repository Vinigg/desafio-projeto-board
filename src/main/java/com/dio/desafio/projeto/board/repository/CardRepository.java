package com.dio.desafio.projeto.board.repository;

import com.dio.desafio.projeto.board.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    boolean existsByName(String name);
}
