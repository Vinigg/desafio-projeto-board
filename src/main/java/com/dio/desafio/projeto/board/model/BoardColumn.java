package com.dio.desafio.projeto.board.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "board_column")
public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ColumnType type;

    @Column(name = "board_order")
    private Integer boardOrder;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;


}
