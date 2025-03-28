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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TimeSpentServiceImpl implements TimeSpentService {

    @Autowired
    private  TimeSpentRepository timeSpentRepository;
    @Autowired
    private  BoardColumnRepository boardColumnRepository;
    @Autowired
    private  CardRepository cardRepository;



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
    @Transactional
    public void startTracking(Long cardId, Long columnId) {
        //Busca pelo Card e Column com os IDs informados
        BoardColumn columnEntity = boardColumnRepository.findById(columnId)
                .orElseThrow(() -> new NoSuchElementException("Coluna não encontrada com ID: " + columnId));
        Card cardEntity = cardRepository.findById(cardId)
                .orElseThrow(()-> new NoSuchElementException("Card não encontrado com ID: " + cardId));

        //Cria uma instância do timeSpent e atribui CardId e ColumnId
        TimeSpent timeSpent = new TimeSpent();
        timeSpent.setCard(cardEntity);
        timeSpent.setColumn(columnEntity);
        timeSpent.setEntryTime(LocalDateTime.now());
        timeSpentRepository.save(timeSpent);
    }

    @Override
    @Transactional
    public void stopTracking(Long cardId, Long columnId) {
        //Verifica a existência de Card e Column
        BoardColumn columnEntity = boardColumnRepository.findById(columnId)
                .orElseThrow(() -> new NoSuchElementException("Coluna não encontrada com ID: " + columnId));
        Card cardEntity = cardRepository.findById(cardId)
                .orElseThrow(()-> new NoSuchElementException("Card não encontrado com ID: " + cardId));

        //Procura um TimeSpent Que pertença ao Card e Column informado e que tenha o exitTime como null
        TimeSpent timeSpent = timeSpentRepository.findByCardAndColumnIdAndExitTimeIsNull(cardEntity, columnEntity)
                .orElseThrow(() -> new RuntimeException("Registro de tempo não encontrado"));

        //Salva o horário atual no exitTime
        timeSpent.setExitTime(LocalDateTime.now());

        // Calcula a diferença em segundos e salva na coluna time_spent_seconds
        long seconds = Duration.between(timeSpent.getEntryTime(), timeSpent.getExitTime()).getSeconds();
        timeSpent.setTimeSpentSeconds(seconds);

        //Perpetua o TimeSpent no banco de dados
        timeSpentRepository.save(timeSpent);
    }

}
