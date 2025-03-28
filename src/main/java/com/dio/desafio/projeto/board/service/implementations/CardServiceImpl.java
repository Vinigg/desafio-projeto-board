package com.dio.desafio.projeto.board.service.implementations;

import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.model.DTOs.BoardColumnDTO;
import com.dio.desafio.projeto.board.model.DTOs.CardDTO;
import com.dio.desafio.projeto.board.repository.BoardColumnRepository;
import com.dio.desafio.projeto.board.repository.CardRepository;
import com.dio.desafio.projeto.board.service.CardService;
import com.dio.desafio.projeto.board.service.helpers.CardMovementService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final BoardColumnRepository boardColumnRepository;
    private final CardMovementService cardMovementService;

    public CardServiceImpl(CardRepository cardRepository, BoardColumnServiceImpl boardColumnService, BoardColumnRepository boardColumnRepository, CardMovementService cardMovementService) {
        this.cardRepository = cardRepository;
        this.boardColumnRepository = boardColumnRepository;
        this.cardMovementService = cardMovementService;
    }

    @Override
    public List<CardDTO> findAll() {
        return cardRepository.findAll().stream().map(CardDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public CardDTO findById(Long id) {
        Card card = cardRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return CardDTO.fromEntity(card);
    }

    @Override
    @Transactional
    public CardDTO create(CardDTO cardToCreate) {
        // Validações
        if (cardRepository.existsByName(cardToCreate.getName())) {
            throw new IllegalArgumentException("Já existe um Card com este nome");
        }
        if (cardToCreate.getColumnId() == null) {
            throw new IllegalArgumentException("columnId é obrigatório");
        }

        // Busca a coluna
        BoardColumn column = boardColumnRepository.findById(cardToCreate.getColumnId())
                .orElseThrow(() -> new NoSuchElementException("Coluna não encontrada"));

        // Cria e associa o card
        Card card = new Card();
        card.setName(cardToCreate.getName().trim());
        card.setCurrentColumn(column);

        // coleções são inicializadas
        card.setTimeSpentList(new ArrayList<>());
        card.setBlocks(new ArrayList<>());

        // Persiste (o cascade cuidará da persistência em cascata se configurado)
        Card savedCard = cardRepository.save(card);

        // Inicia tracking
        cardMovementService.cardInitialTracking(cardToCreate, BoardColumnDTO.fromEntity(column));

        return CardDTO.fromEntity(savedCard);
    }

}
