package com.dio.desafio.projeto.board.model.DTOs;

import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.model.TimeSpent;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeSpentDTO {
    private Long id;
    private Long cardId;
    private Long columnId;
    private Long timeSpentSeconds;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    // Conversão de Entity para DTO
    public static TimeSpentDTO fromEntity(TimeSpent timeSpent) {
        TimeSpentDTO dto = new TimeSpentDTO();
        dto.setId(timeSpent.getId());
        dto.setCardId(timeSpent.getCard() != null ? timeSpent.getCard().getId() : null);
        dto.setColumnId(timeSpent.getColumn() != null ? timeSpent.getColumn().getId() : null);
        dto.setTimeSpentSeconds(timeSpent.getTimeSpentSeconds());
        dto.setEntryTime(timeSpent.getEntryTime());
        dto.setExitTime(timeSpent.getExitTime());
        return dto;
    }

    // Conversão de DTO para Entity
    public TimeSpent toEntity() {
        TimeSpent timeSpent = new TimeSpent();
        timeSpent.setId(this.id);

        // Converte IDs para referências das entidades
        if (this.cardId != null) {
            Card card = new Card();
            card.setId(this.cardId);
            timeSpent.setCard(card);
        }
        if (this.columnId != null) {
            BoardColumn column = new BoardColumn();
            column.setId(this.columnId);
            timeSpent.setColumn(column);
        }

        timeSpent.setTimeSpentSeconds(this.timeSpentSeconds);
        timeSpent.setEntryTime(this.entryTime);
        timeSpent.setExitTime(this.exitTime);
        return timeSpent;
    }
}
