package com.dio.desafio.projeto.board.repository;

import com.dio.desafio.projeto.board.model.TimeSpent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TimeSpentRepository extends JpaRepository<TimeSpent,Integer> {
}
