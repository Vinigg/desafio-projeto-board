package com.dio.desafio.projeto.board.repository;

import com.dio.desafio.projeto.board.model.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BoardColumnRepository extends JpaRepository<BoardColumn,Long> {
    boolean existsByName( String name);
}
