package com.dio.desafio.projeto.board.service;

import com.dio.desafio.projeto.board.model.BoardColumn;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BoardColumnService {

    List<BoardColumn> findAll();

    BoardColumn findById(Integer id);

    BoardColumn create(BoardColumn boardColumnToCreate);

}
