package com.dio.desafio.projeto.board.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "time_spent")
public class TimeSpent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne
    @JoinColumn(name = "column_id", nullable = false)
    private BoardColumn boardColumn;

    @Column(name = "time_spent", nullable = false)
    private LocalDateTime timeSpent;
}
