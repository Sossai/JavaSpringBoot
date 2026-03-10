package com.example.libraryapi.validator;

import com.example.libraryapi.exceptions.ResgistroDuplicadoException;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor()
public class LivroValidator {

    private final LivroRepository livroRepository;

    public void validar(Livro livro){
        if(existeLivroComIsbn(livro)){
            throw new ResgistroDuplicadoException("ISBN já cadastrado");
        }
    }

    private boolean existeLivroComIsbn(Livro livro){

        Optional<Livro> livroEncontrato = livroRepository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null){
            return  livroEncontrato.isPresent();
        }

        // se encontrou o livro mas o id é diferente
        return livroEncontrato
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }
}
