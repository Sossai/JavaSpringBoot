package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.controller.dto.CadastroLivroDTO;
import com.example.libraryapi.controller.dto.ErroResposta;
import com.example.libraryapi.controller.dto.ResultadoLivroDTO;
import com.example.libraryapi.exceptions.ResgistroDuplicadoException;
import com.example.libraryapi.mappers.AutorMapper;
import com.example.libraryapi.mappers.LivroMapper;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("livros")
public class LivroController implements GenericController{

    private final LivroService livroService;
    private final LivroMapper livroMapper;
    private final AutorMapper autorMapper;


    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO livroDto){
        // removi os try/catch pq estao sendo tratadas nos handlers
        //try{

        Livro livro = livroMapper.toEntity(livroDto);
        livroService.salvar(livro);

        var location = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(location).build();

        //}catch (ResgistroDuplicadoException ex){
        //    var erroDTO = ErroResposta.conflito(ex.getMessage());
        //    return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        //}
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoLivroDTO> buscar(
            @PathVariable String id
    ){
        //var livro = livroService.obterPorId(UUID.fromString(id));
        //return ResponseEntity.ok(livroMapper.toResultado(livro));

        return livroService
                .obterPorId(UUID.fromString(id))
                .map(livro -> {
                    ResultadoLivroDTO dto = livroMapper.toResultado(livro);
                    return ResponseEntity.ok(livroMapper.toResultado(livro));
                }).orElseGet(()-> ResponseEntity.notFound().build());

    }



}
