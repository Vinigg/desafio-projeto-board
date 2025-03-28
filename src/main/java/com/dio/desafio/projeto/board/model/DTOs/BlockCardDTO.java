package com.dio.desafio.projeto.board.model.DTOs;

import com.dio.desafio.projeto.board.model.BlockCard;
import com.dio.desafio.projeto.board.model.Card;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlockCardDTO {
    private Long id;
    private String blockReason;
    private LocalDateTime blockedAt;
    private String unblockReason;
    private LocalDateTime unblockedAt;
    private Long cardId; // Representação por ID

    // Conversão de Entity para DTO
    public static BlockCardDTO fromEntity(BlockCard block) {
        BlockCardDTO dto = new BlockCardDTO();
        dto.setId(block.getId());
        dto.setBlockReason(block.getBlockReason());
        dto.setBlockedAt(block.getBlockedAt());
        dto.setUnblockReason(block.getUnblockReason());
        dto.setUnblockedAt(block.getUnblockedAt());
        dto.setCardId(block.getCard() != null ? block.getCard().getId() : null);
        return dto;
    }

    // Conversão de DTO para Entity (opcional)
    public BlockCard toEntity() {
        BlockCard block = new BlockCard();
        block.setId(this.id);
        block.setBlockReason(this.blockReason);
        block.setBlockedAt(this.blockedAt);
        block.setUnblockReason(this.unblockReason);
        block.setUnblockedAt(this.unblockedAt);

        if (this.cardId != null) {
            Card card = new Card();
            card.setId(this.cardId);
            block.setCard(card);
        }

        return block;
    }
}
