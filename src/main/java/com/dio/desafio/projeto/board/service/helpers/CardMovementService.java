package com.dio.desafio.projeto.board.service.helpers;

import com.dio.desafio.projeto.board.controller.BoardController;
import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.model.DTOs.BoardColumnDTO;
import com.dio.desafio.projeto.board.model.DTOs.CardDTO;
import com.dio.desafio.projeto.board.repository.BoardColumnRepository;
import com.dio.desafio.projeto.board.service.TimeSpentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
public class CardMovementService {

    private final BoardColumnRepository boardColumnRepository;
    private final TimeSpentService timeSpentService;

    public CardMovementService(BoardColumnRepository boardColumnRepository, TimeSpentService timeSpentService) {
        this.boardColumnRepository = boardColumnRepository;
        this.timeSpentService = timeSpentService;
    }

    public void cardInitialTracking(CardDTO card, BoardColumnDTO column){
        timeSpentService.startTracking(card,column);
    }

    public void moveToColumn(CardDTO card, BoardColumnDTO column) {
        // 1. Validações de domínio
        if (Objects.equals(card.getColumnId(), column.getId())) {
            throw new IllegalStateException("Card já está na coluna destino");
        }
        BoardColumn columnEntity = boardColumnRepository.findById(column.getId())
                .orElseThrow(() -> new NoSuchElementException("Coluna não encontrada com ID: " + column.getId()));

        // 2. Lógica de infra/transação
        BoardColumn newColumn = boardColumnRepository.findById(card.getColumnId())
                .orElseThrow(() -> new NoSuchElementException("Coluna não encontrada com ID: " + column.getId()));
        timeSpentService.stopTracking(card, BoardColumnDTO.fromEntity(newColumn));


        // 3. Atualiza estado
        card.setColumnId(columnEntity.getId());

        // 4. Nova tracking
        timeSpentService.startTracking(card, column);
    }
}
