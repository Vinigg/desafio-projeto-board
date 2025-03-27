package com.dio.desafio.projeto.board.controller;

import com.dio.desafio.projeto.board.model.Board;
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
    public ResponseEntity<List<Board>> findAll(){
        var boards =  boardService.findAll();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> findById(@PathVariable Integer id){
        var board = boardService.findById(id);
        return ResponseEntity.ok(board);
    }

    @PostMapping
    public ResponseEntity<Board> create(@RequestBody Board boardToCreate){
        var boardCreated = boardService.create(boardToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(boardCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(boardCreated);
    }


}
