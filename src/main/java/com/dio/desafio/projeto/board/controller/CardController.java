package com.dio.desafio.projeto.board.controller;

import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.Card;
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
    public ResponseEntity<List<Card>> findAll(){
        var cards =  cardService.findAll();
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> findById(@PathVariable Integer id){
        var card = cardService.findById(id);
        return ResponseEntity.ok(card);
    }

    @PostMapping
    public ResponseEntity<Card> create(@RequestBody Card cardToCreate){
        var cardCreated = cardService.create(cardToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cardCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(cardCreated);
    }

    @PutMapping("/{cardId}/move")
    public ResponseEntity<Card> moveCard(@PathVariable Integer cardId, @RequestParam Integer newColumnId){
        Card card = cardService.findById(cardId);
        BoardColumn newColumn = boardColumnService.findById(newColumnId);

        cardMovementService.moveToColumn(card,newColumn);

        return ResponseEntity.ok().build();
    }

}
