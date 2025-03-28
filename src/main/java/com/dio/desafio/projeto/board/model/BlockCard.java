package com.dio.desafio.projeto.board.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "block_card")
public class BlockCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "block_reason", nullable = false)
    private String blockReason;

    @Column(name = "blocked_at", nullable = false)
    private LocalDateTime blockedAt = LocalDateTime.now();

    @Column(name = "unblock_reason")
    private String unblockReason;

    @Column(name = "unblocked_at")
    private LocalDateTime unblockedAt;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

}
