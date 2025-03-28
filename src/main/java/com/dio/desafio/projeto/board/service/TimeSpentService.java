package com.dio.desafio.projeto.board.service;



import com.dio.desafio.projeto.board.model.DTOs.BoardColumnDTO;
import com.dio.desafio.projeto.board.model.DTOs.CardDTO;
import com.dio.desafio.projeto.board.model.DTOs.TimeSpentDTO;
import com.dio.desafio.projeto.board.model.TimeSpent;

import java.util.List;

public interface TimeSpentService {

    List<TimeSpentDTO> findAll();

    TimeSpentDTO findById(Long id);

    void startTracking(Long cardId, Long columnId);

    void stopTracking(Long cardId, Long columnId);
}
