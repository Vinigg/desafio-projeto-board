package com.dio.desafio.projeto.board.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "board_column")
@NoArgsConstructor
public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(mappedBy = "currentColumn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;

    public BoardColumn(String name, ColumnType type, Integer boardOrder, Board board) {
        this.name = name;
        this.type = type;
        this.boardOrder = boardOrder;
        this.board = board;
    }
    public void addCard(Card card) {
        cards.add(card);
        card.setCurrentColumn(this);
    }
}
