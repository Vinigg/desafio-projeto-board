package com.dio.desafio.projeto.board.model.DTOs;

import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.Card;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Long columnId;
    private LocalDateTime movedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TimeSpentDTO> timeSpentList;
    private List<BlockCardDTO> blocks;

    public static CardDTO fromEntity(Card card){
        CardDTO dto = new CardDTO();
        dto.setId(card.getId());
        dto.setName(card.getName());
        dto.setColumnId(card.getCurrentColumn() != null ? card.getCurrentColumn().getId() : null);
        dto.setMovedAt(card.getMovedAt());
        dto.setCreatedAt(card.getCreatedAt());
        dto.setUpdatedAt(card.getUpdatedAt());
        // Convertendo as listas de entidades para DTOs
        if (card.getTimeSpentList() != null) {
            dto.setTimeSpentList(
                    card.getTimeSpentList().stream()
                            .map(TimeSpentDTO::fromEntity)
                            .collect(Collectors.toList())
            );
        }

        if (card.getBlocks() != null) {
            dto.setBlocks(
                    card.getBlocks().stream()
                            .map(BlockCardDTO::fromEntity)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public Card toEntity() {
        Card card = new Card();
        card.setId(this.id); // Apenas se for uma atualização
        card.setName(this.name);
        card.setMovedAt(this.movedAt);

        if (this.columnId != null) {
            BoardColumn column = new BoardColumn();
            column.setId(this.columnId); // Assume-se que só o ID é necessário
            card.setCurrentColumn(column);
        }

        return card;
    }
}
