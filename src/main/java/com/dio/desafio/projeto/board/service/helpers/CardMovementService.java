package com.dio.desafio.projeto.board.service.helpers;

import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.service.TimeSpentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CardMovementService {

    private final TimeSpentService timeSpentService;

    public CardMovementService(TimeSpentService timeSpentService) {
        this.timeSpentService = timeSpentService;
    }

    public void cardInitialTracking(Card card, BoardColumn column){
        timeSpentService.startTracking(card,column);
    }

    public void moveToColumn(Card card, BoardColumn column) {
        // 1. Validações de domínio
        if (card.getCurrentColumn().equals(column)) {
            throw new IllegalStateException("Card já está na coluna destino");
        }

        // 2. Lógica de infra/transação
        timeSpentService.stopTracking(card, card.getCurrentColumn());


        // 3. Atualiza estado
        card.setCurrentColumn(column);

        // 4. Nova tracking
        timeSpentService.startTracking(card, column);
    }
}
