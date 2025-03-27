package com.dio.desafio.projeto.board.service;


import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.model.TimeSpent;

import java.util.List;

public interface TimeSpentService {

    List<TimeSpent> findAll();

    TimeSpent findById(Integer id);

    void startTracking(Card card, BoardColumn column);

    void stopTracking(Card card, BoardColumn column);
}
