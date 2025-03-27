package com.dio.desafio.projeto.board.service;

import com.dio.desafio.projeto.board.model.Board;
import com.dio.desafio.projeto.board.model.Card;

import java.util.List;

public interface CardService {
    List<Card> findAll();

    Card findById(Integer id);

    Card create(Card cardToCreate);
}
