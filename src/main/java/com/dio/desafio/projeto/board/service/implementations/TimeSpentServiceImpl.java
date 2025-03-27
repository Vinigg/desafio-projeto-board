package com.dio.desafio.projeto.board.service.implementations;

import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.model.TimeSpent;
import com.dio.desafio.projeto.board.repository.TimeSpentRepository;
import com.dio.desafio.projeto.board.service.TimeSpentService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TimeSpentServiceImpl implements TimeSpentService {

    private final TimeSpentRepository timeSpentRepository;

    public TimeSpentServiceImpl(TimeSpentRepository timeSpentRepository) {
        this.timeSpentRepository = timeSpentRepository;
    }

    @Override
    public List<TimeSpent> findAll() {
        return timeSpentRepository.findAll();
    }

    @Override
    public TimeSpent findById(Integer id) {
        return timeSpentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void startTracking(Card card, BoardColumn column) {
        TimeSpent timeSpent = new TimeSpent();
        timeSpent.setCard(card);
        timeSpent.setColumn(column);
        timeSpent.setEntryTime(LocalDateTime.now());
        timeSpentRepository.save(timeSpent);
    }

    @Override
    public void stopTracking(Card card, BoardColumn column) {
        TimeSpent timeSpent = timeSpentRepository.findByCardAndColumnIdAndExitTimeIsNull(card, column)
                .orElseThrow(() -> new RuntimeException("Registro de tempo não encontrado"));

        timeSpent.setExitTime(LocalDateTime.now());

        // Calcula a diferença em segundos
        long seconds = Duration.between(timeSpent.getEntryTime(), timeSpent.getExitTime()).getSeconds();
        timeSpent.setTimeSpentSeconds(seconds);

        timeSpentRepository.save(timeSpent);
    }

}
