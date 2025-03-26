package com.dio.desafio.projeto.board.service.implementations;

import com.dio.desafio.projeto.board.model.Board;
import com.dio.desafio.projeto.board.repository.BoardRepository;
import com.dio.desafio.projeto.board.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Board findById(Integer id) {
        return boardRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Board create(Board boardToCreate) {
        if(boardRepository.existsByName(boardToCreate.getName())){
            throw new IllegalArgumentException("Already has an Board with this name");
        }
        return boardRepository.save(boardToCreate);
    }
}
