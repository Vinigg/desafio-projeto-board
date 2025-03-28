package com.dio.desafio.projeto.board.controller;

import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.model.DTOs.BoardColumnDTO;
import com.dio.desafio.projeto.board.model.DTOs.BoardDTO;
import com.dio.desafio.projeto.board.model.DTOs.CardDTO;
import com.dio.desafio.projeto.board.service.BoardColumnService;
import com.dio.desafio.projeto.board.service.CardService;
import com.dio.desafio.projeto.board.service.helpers.CardMovementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;
    private final BoardColumnService boardColumnService;
    private final CardMovementService cardMovementService;


    public CardController(CardService cardService, BoardColumnService boardColumnService, CardMovementService cardMovementService) {
        this.cardService = cardService;
        this.boardColumnService = boardColumnService;
        this.cardMovementService = cardMovementService;
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> findAll(){
        var cards =  cardService.findAll();
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> findById(@PathVariable Long id){
        var card = cardService.findById(id);
        return ResponseEntity.ok(card);
    }

    @PostMapping
    public ResponseEntity<CardDTO> create(@RequestBody CardDTO cardToCreate){
        var cardCreated = cardService.create(cardToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cardCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(cardCreated);
    }

    @PutMapping("/{cardId}/move")
    public ResponseEntity<CardDTO> moveCard(@PathVariable Long cardId, @RequestParam Long newColumnId){
        CardDTO card = cardService.findById(cardId);
        BoardColumnDTO newColumn = boardColumnService.findById(newColumnId);

        cardMovementService.moveToColumn(card,newColumn);

        return ResponseEntity.ok().build();
    }

}
