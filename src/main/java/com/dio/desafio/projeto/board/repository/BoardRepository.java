package com.dio.desafio.projeto.board.repository;

import com.dio.desafio.projeto.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BoardRepository extends JpaRepository<Board, Integer> {
    boolean existsByName(String name);
}
