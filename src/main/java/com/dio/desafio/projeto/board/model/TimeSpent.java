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

    @Column(name = "time_spent_seconds") // Armazena a duração em segundos
    private Long timeSpentSeconds;

    @Column(name = "entry_time", nullable = false) // Momento em que o card entrou na coluna
    private LocalDateTime entryTime;

    @Column(name = "exit_time") // Momento em que o card saiu da coluna (inicialmente null)
    private LocalDateTime exitTime;
}
