package com.dio.desafio.projeto.board.repository;

import com.dio.desafio.projeto.board.model.BlockCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockCardRepository extends JpaRepository<BlockCard, Integer> {
}
