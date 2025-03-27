package com.dio.desafio.projeto.board.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "column_id",nullable = false) // Guarda apenas o ID da coluna atual
    private BoardColumn currentColumn;

    @Column(name = "moved_at")
    private LocalDateTime movedAt;

    @Column(name = "created_at", updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private final LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "card")
    private List<TimeSpent> timeSpentList;

    @OneToMany(mappedBy = "card")
    private List<BlockCard> blocks;

}
