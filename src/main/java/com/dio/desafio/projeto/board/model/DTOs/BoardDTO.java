package com.dio.desafio.projeto.board.model.DTOs;

import com.dio.desafio.projeto.board.model.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String name;
    private List<BoardColumnDTO> columns;

    public static BoardDTO fromEntity(Board board) {
        BoardDTO dto = new BoardDTO();
        dto.setId(board.getId());
        dto.setName(board.getName());
        if (board.getColumns() != null) {
            dto.setColumns(
                    board.getColumns().stream()
                            .map(BoardColumnDTO::fromEntity)
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }

    // Conversão de DTO para Entity
    public Board toEntity() {
        Board board = new Board();
        board.setId(this.id); // Opcional: só inclua se permitir atualização por DTO
        board.setName(this.name);
        // Não setar columns aqui para evitar inconsistências (gerenciar separadamente)
        return board;
    }
}
