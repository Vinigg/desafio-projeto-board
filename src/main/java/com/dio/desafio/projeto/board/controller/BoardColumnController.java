package com.dio.desafio.projeto.board.controller;

import com.dio.desafio.projeto.board.model.BoardColumn;
import com.dio.desafio.projeto.board.model.DTOs.BoardColumnDTO;
import com.dio.desafio.projeto.board.service.BoardColumnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/columns")
public class BoardColumnController {
    private final BoardColumnService boardColumnService;

    public BoardColumnController(BoardColumnService boardColumnService) {
        this.boardColumnService = boardColumnService;
    }

    @GetMapping
    public ResponseEntity<List<BoardColumnDTO>> findAll(){
        var columns = boardColumnService.findAll();
        return ResponseEntity.ok(columns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardColumnDTO> findById(@PathVariable Long id){
        var column = boardColumnService.findById(id);
        return ResponseEntity.ok(column);
    }

    @PostMapping
    public ResponseEntity<BoardColumnDTO> create(@RequestBody BoardColumn boardColumnToCreate){
        var columnCreated = boardColumnService.create(boardColumnToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(boardColumnToCreate.getId())
                .toUri();
        return ResponseEntity.created(location).body(columnCreated);
    }
}
