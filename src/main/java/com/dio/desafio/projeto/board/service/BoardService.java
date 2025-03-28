package com.dio.desafio.projeto.board.service;

import com.dio.desafio.projeto.board.model.Board;
import com.dio.desafio.projeto.board.model.DTOs.BoardDTO;

import java.util.List;


public interface BoardService {

    List<BoardDTO> findAll();

    BoardDTO findById(Long id);

    BoardDTO create(Board boardToCreate);


}
