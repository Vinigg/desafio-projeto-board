package com.dio.desafio.projeto.board.controller;

import com.dio.desafio.projeto.board.model.Card;
import com.dio.desafio.projeto.board.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;


    public CardController(CardService cardService) {
        this.cardService = cardService;
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

}
