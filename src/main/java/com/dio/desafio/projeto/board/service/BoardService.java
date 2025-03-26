package com.dio.desafio.projeto.board.service;

import com.dio.desafio.projeto.board.model.Board;


public interface BoardService {

    Board findById(Integer id);

    Board create(Board boardToCreate);
}
