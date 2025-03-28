package com.dio.desafio.projeto.board.service;

import com.dio.desafio.projeto.board.model.Board;
import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.DTOs.BoardColumnDTO;

import java.util.List;


public interface BoardColumnService {

    List<BoardColumnDTO> findAll();

    BoardColumnDTO findById(Long id);

    BoardColumnDTO create(BoardColumn boardColumnToCreate);

    void bulkDefaultColumns(Board board);
}
