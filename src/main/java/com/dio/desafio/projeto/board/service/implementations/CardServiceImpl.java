package com.dio.desafio.projeto.board.service.implementations;

import com.dio.desafio.projeto.board.model.Board;
import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.repository.CardRepository;
import com.dio.desafio.projeto.board.service.CardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
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
        return cardRepository.save(cardToCreate);
    }
}
