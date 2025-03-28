package com.dio.desafio.projeto.board.service.implementations;

import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.model.DTOs.BoardColumnDTO;
import com.dio.desafio.projeto.board.model.DTOs.CardDTO;
import com.dio.desafio.projeto.board.model.DTOs.TimeSpentDTO;
import com.dio.desafio.projeto.board.model.TimeSpent;
import com.dio.desafio.projeto.board.repository.BoardColumnRepository;
import com.dio.desafio.projeto.board.repository.CardRepository;
import com.dio.desafio.projeto.board.repository.TimeSpentRepository;
import com.dio.desafio.projeto.board.service.TimeSpentService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TimeSpentServiceImpl implements TimeSpentService {

    private final TimeSpentRepository timeSpentRepository;
    private final BoardColumnRepository boardColumnRepository;
    private final CardRepository cardRepository;

    public TimeSpentServiceImpl(TimeSpentRepository timeSpentRepository, BoardColumnRepository boardColumnRepository, CardRepository cardRepository) {
        this.timeSpentRepository = timeSpentRepository;
        this.boardColumnRepository = boardColumnRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public List<TimeSpentDTO> findAll() {
        return timeSpentRepository.findAll().stream().map(TimeSpentDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public TimeSpentDTO findById(Long id) {
         TimeSpent timeSpent = timeSpentRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return TimeSpentDTO.fromEntity(timeSpent);
    }

    @Override
    public void startTracking(CardDTO card, BoardColumnDTO column) {
        BoardColumn columnEntity = boardColumnRepository.findById(column.getId())
                .orElseThrow(() -> new NoSuchElementException("Coluna não encontrada com ID: " + column.getId()));
        Card cardEntity = cardRepository.findById(card.getId())
                .orElseThrow(()-> new NoSuchElementException("Card não encontrado com ID: " + card.getId()));
        TimeSpent timeSpent = new TimeSpent();
        timeSpent.setCard(cardEntity);
        timeSpent.setColumn(columnEntity);
        timeSpent.setEntryTime(LocalDateTime.now());
        timeSpentRepository.save(timeSpent);
    }

    @Override
    public void stopTracking(CardDTO card, BoardColumnDTO column) {
        BoardColumn columnEntity = boardColumnRepository.findById(column.getId())
                .orElseThrow(() -> new NoSuchElementException("Coluna não encontrada com ID: " + column.getId()));
        Card cardEntity = cardRepository.findById(card.getId())
                .orElseThrow(()-> new NoSuchElementException("Card não encontrado com ID: " + card.getId()));
        TimeSpent timeSpent = timeSpentRepository.findByCardAndColumnIdAndExitTimeIsNull(cardEntity, columnEntity)
                .orElseThrow(() -> new RuntimeException("Registro de tempo não encontrado"));

        timeSpent.setExitTime(LocalDateTime.now());

        // Calcula a diferença em segundos
        long seconds = Duration.between(timeSpent.getEntryTime(), timeSpent.getExitTime()).getSeconds();
        timeSpent.setTimeSpentSeconds(seconds);

        timeSpentRepository.save(timeSpent);
    }

}
