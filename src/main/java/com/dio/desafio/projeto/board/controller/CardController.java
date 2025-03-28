package com.dio.desafio.projeto.board.controller;

import com.dio.desafio.projeto.board.model.DTOs.BoardColumnDTO;
import com.dio.desafio.projeto.board.model.DTOs.CardDTO;
import com.dio.desafio.projeto.board.service.BoardColumnService;
import com.dio.desafio.projeto.board.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private BoardColumnService boardColumnService;




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

    @PutMapping("/{cardId}")
    public ResponseEntity<CardDTO> updateCard(@PathVariable Long cardId, @RequestBody CardDTO updateCard){
        CardDTO card = cardService.findById(cardId);


        return ResponseEntity.ok().build();
    }

}
