package com.dio.desafio.projeto.board.model.DTOs;

import com.dio.desafio.projeto.board.model.Board;
import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.ColumnType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardColumnDTO {
    private Long id;
    private String name;
    private ColumnType type;
    private Long boardId;
    private Integer order;
    private List<CardDTO> cardsList;

    public static BoardColumnDTO fromEntity(BoardColumn column) {
        BoardColumnDTO dto = new BoardColumnDTO();
        dto.setId(column.getId());
        dto.setName(column.getName());
        dto.setType(column.getType());
        dto.setOrder(column.getBoardOrder());
        if (column.getCards() != null){
            dto.setCardsList(
                    column.getCards().stream()
                    .map(CardDTO::fromEntity)
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }
    public BoardColumn toEntity(){
        BoardColumn column = new BoardColumn();
        column.setId(this.getId());
        column.setName(this.getName());
        column.setType(this.getType());
        column.setBoardOrder(this.getOrder());
        if (this.boardId != null) {
            Board board = new Board();
            board.getColumns().add(column);
            column.setBoard(board);
        }
        return column;
    }
}
