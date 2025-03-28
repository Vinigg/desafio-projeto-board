package com.dio.desafio.projeto.board.service.implementations;

import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.model.DTOs.CardDTO;
import com.dio.desafio.projeto.board.repository.BoardColumnRepository;
import com.dio.desafio.projeto.board.repository.CardRepository;
import com.dio.desafio.projeto.board.service.CardService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private BoardColumnRepository boardColumnRepository;
    @Autowired
    private TimeSpentServiceImpl timeSpentService;



    @Override
    public List<CardDTO> findAll() {
        return cardRepository.findAll().stream().map(CardDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public CardDTO findById(Long id) {
        //Recebe um id e busca no Repositório um card. Lança um erro caso não encontre.
        Card card = cardRepository.findById(id).orElseThrow(NoSuchElementException::new);

        //Retorna um CardDTO , transformando através do metodo fromEntity.
        return CardDTO.fromEntity(card);
    }

    @Override
    @Transactional
    public CardDTO create(CardDTO cardToCreate) {
        // Validações
        if (cardRepository.existsByName(cardToCreate.getName())) {
            throw new IllegalArgumentException("Já existe um Card com este nome");
        }

        // Busca a coluna
        BoardColumn column = boardColumnRepository.findById(cardToCreate.getColumnId())
                .orElseThrow(() -> new NoSuchElementException("Coluna não encontrada"));

        //Transforma o DTO em uma entidade Card
        Card card = cardToCreate.toEntity();

        // coleções são inicializadas
        card.setTimeSpentList(new ArrayList<>());
        card.setBlocks(new ArrayList<>());

        // Persiste (o cascade cuidará da persistência em cascata se configurado)
        cardRepository.save(card);

        // Inicia tracking
        timeSpentService.startTracking(card.getId(), column.getId());
        return CardDTO.fromEntity(card);
    }

    @Override
    @Transactional
    public CardDTO updateCard(Long id, CardDTO cardToUpdate) {
        Card card = cardRepository.findById(id).orElseThrow(NoSuchElementException::new);

        //Verifica se o update muda a coluna do card
        if (!Objects.equals(cardToUpdate.getColumnId(), card.getCurrentColumn().getId())){
            //Encerra o tracking na coluna antiga
            timeSpentService.stopTracking(card.getId(),card.getCurrentColumn().getId());

            //Inicia um tracking para a coluna nova
            timeSpentService.startTracking(card.getId(),cardToUpdate.getColumnId());
        }
        BeanUtils.copyProperties(cardToUpdate,card);
        cardRepository.save(card);

        return CardDTO.fromEntity(card);
    }


}
