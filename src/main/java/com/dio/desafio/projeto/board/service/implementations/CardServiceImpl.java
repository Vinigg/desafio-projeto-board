package com.dio.desafio.projeto.board.service.implementations;

import com.dio.desafio.projeto.board.model.Board;
import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.repository.CardRepository;
import com.dio.desafio.projeto.board.service.CardService;
import com.dio.desafio.projeto.board.service.helpers.CardMovementService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final CardMovementService cardMovementService;

    public CardServiceImpl(CardRepository cardRepository, CardMovementService cardMovementService) {
        this.cardRepository = cardRepository;
        this.cardMovementService = cardMovementService;
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card findById(Integer id) {
        return cardRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Card create(Card cardToCreate) {
        if(cardRepository.existsByName(cardToCreate.getName())){
            throw new IllegalArgumentException("Already has an Card with this name");
        }
        Card createdCard = cardRepository.save(cardToCreate);
        cardMovementService.cardInitialTracking(cardToCreate, cardToCreate.getCurrentColumn());
        return createdCard;
    }

}
