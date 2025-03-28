package com.dio.desafio.projeto.board.service;

import com.dio.desafio.projeto.board.model.Board;
import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.model.DTOs.CardDTO;

import java.util.List;

public interface CardService {
    List<CardDTO> findAll();

    CardDTO findById(Long id);

    CardDTO create(CardDTO cardToCreate);
}
