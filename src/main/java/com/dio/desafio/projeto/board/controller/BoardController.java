package com.dio.desafio.projeto.board.controller;

import com.dio.desafio.projeto.board.model.Board;
import com.dio.desafio.projeto.board.model.DTOs.BoardDTO;
import com.dio.desafio.projeto.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<List<BoardDTO>> findAll(){
        return ResponseEntity.ok(boardService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> findById(@PathVariable Long id){
        var board = boardService.findById(id);
        return ResponseEntity.ok(board);
    }

    @PostMapping
    public ResponseEntity<BoardDTO> create(@RequestBody Board boardToCreate){
        var boardCreated = boardService.create(boardToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(boardCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(boardCreated);
    }


}
