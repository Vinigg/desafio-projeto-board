package com.dio.desafio.projeto.board.service.implementations;

import com.dio.desafio.projeto.board.model.Board;
import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.ColumnType;
import com.dio.desafio.projeto.board.model.DTOs.BoardColumnDTO;
import com.dio.desafio.projeto.board.repository.BoardColumnRepository;
import com.dio.desafio.projeto.board.service.BoardColumnService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BoardColumnServiceImpl implements BoardColumnService {

    private final BoardColumnRepository boardColumnRepository;

    public BoardColumnServiceImpl(BoardColumnRepository boardColumnRepository) {
        this.boardColumnRepository = boardColumnRepository;
    }

    @Override
    public List<BoardColumnDTO> findAll() {
        return boardColumnRepository.findAll().stream().map(BoardColumnDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public BoardColumnDTO findById(Long id) {
        return boardColumnRepository.findById(id).map(BoardColumnDTO::fromEntity).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public BoardColumnDTO create(BoardColumn boardColumnToCreate) {
        if(boardColumnRepository.existsByName(boardColumnToCreate.getName()))
        {
            throw new IllegalArgumentException("Already has an Column with this name");
        }
        return BoardColumnDTO.fromEntity(boardColumnRepository.save(boardColumnToCreate));
    }

    @Override
    public void bulkDefaultColumns( Board board){
        List<BoardColumn> defaultColumns = Arrays.asList(
                new BoardColumn("INICIO", ColumnType.INICIAL, 1,board),
                new BoardColumn("FINALIZADO",ColumnType.FINAL,2,board),
                new BoardColumn("CANCELADO",ColumnType.CANCELAMENTO,3,board)
        );

        boardColumnRepository.saveAll(defaultColumns);
    }
}
