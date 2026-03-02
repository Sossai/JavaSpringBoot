package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.CadastroLivroDTO;
import com.example.libraryapi.controller.dto.ErroResposta;
import com.example.libraryapi.exceptions.ResgistroDuplicadoException;
import com.example.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("livros")
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO livro){
        try{

               //livroService.salvar();
            return ResponseEntity.ok(livro);

        }catch (ResgistroDuplicadoException ex){
            var erroDTO = ErroResposta.conflito(ex.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }

    }

}
