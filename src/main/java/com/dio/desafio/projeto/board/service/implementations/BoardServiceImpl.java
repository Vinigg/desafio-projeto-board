package com.dio.desafio.projeto.board.service.implementations;

import com.dio.desafio.projeto.board.model.Board;
import com.dio.desafio.projeto.board.model.DTOs.BoardDTO;
import com.dio.desafio.projeto.board.repository.BoardColumnRepository;
import com.dio.desafio.projeto.board.repository.BoardRepository;
import com.dio.desafio.projeto.board.service.BoardColumnService;
import com.dio.desafio.projeto.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;


    @Autowired
    private BoardColumnService boardColumnService;

    public BoardServiceImpl(BoardRepository boardRepository, BoardColumnRepository boardColumnRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<BoardDTO> findAll() {
        return boardRepository.findAll().stream()
                .map(BoardDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public BoardDTO findById(Long id) {
        return boardRepository.findById(id).map(BoardDTO::fromEntity).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public BoardDTO create(Board boardToCreate) {
        if (boardRepository.existsByName(boardToCreate.getName())){
            throw new IllegalArgumentException("Already has an Board with this name");
        }
        Board board = new Board();
        board.setName(boardToCreate.getName());
        boardRepository.save(board);
        boardColumnService.bulkDefaultColumns(board);
        return BoardDTO.fromEntity(board);
    }
}
