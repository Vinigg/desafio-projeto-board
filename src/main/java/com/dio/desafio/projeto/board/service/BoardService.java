package com.dio.desafio.projeto.board.service;

import com.dio.desafio.projeto.board.model.Board;

import java.util.List;


public interface BoardService {

    List<Board> findAll();

    Board findById(Integer id);

    Board create(Board boardToCreate);


}
