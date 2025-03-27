package com.dio.desafio.projeto.board.repository;

import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.model.TimeSpent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeSpentRepository extends JpaRepository<TimeSpent,Integer> {
    Optional<TimeSpent> findByCardAndColumnIdAndExitTimeIsNull(Card card, BoardColumn column);
}
