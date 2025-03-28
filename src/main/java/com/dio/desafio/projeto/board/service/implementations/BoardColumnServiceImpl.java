package com.dio.desafio.projeto.board.service.implementations;

import com.dio.desafio.projeto.board.model.Board;
import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.ColumnType;
import com.dio.desafio.projeto.board.model.DTOs.BoardColumnDTO;
import com.dio.desafio.projeto.board.repository.BoardColumnRepository;
import com.dio.desafio.projeto.board.service.BoardColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BoardColumnServiceImpl implements BoardColumnService {

    @Autowired
    private  BoardColumnRepository boardColumnRepository;

    @Override
    public List<BoardColumnDTO> findAll() {
        return boardColumnRepository.findAll().stream().map(BoardColumnDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public BoardColumnDTO findById(Long id) {
        //Recebe um id e busca no Repositório um board_column. Lança um erro caso não encontre.
        BoardColumn column = boardColumnRepository.findById(id).orElseThrow(NoSuchElementException::new);

        //Retorna um BoardColumnDTO, transformando através do metodo fromEntity.
        return BoardColumnDTO.fromEntity(column);
    }

    @Override
    public BoardColumnDTO create(BoardColumnDTO columnDTO) {
        //Recebe um BoardColumnDTO, pois não queremos preencher os relacionamentos da coluna, ela é criada vazia.

        // Verifica a existência de outra coluna com o mesmo nome.
        if(boardColumnRepository.existsByName(columnDTO.getName()))
        {
            throw new IllegalArgumentException("Already has an Column with this name");
        }

        //Transforma o DTO em uma entidade para ser salva no repositório
        BoardColumn columnToCreate = columnDTO.toEntity();
        //Salva a entidade no repositório
        boardColumnRepository.save(columnToCreate);

        //Retorna um DTO
        return columnDTO;
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
