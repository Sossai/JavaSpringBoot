package com.example.libraryapi.service;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.repository.AutorRepository;
import com.example.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor // cria um construtor com todos os final
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;

    /*
    public AutorService(AutorRepository autorRepository,
                        AutorValidator autorValidator) {
        this.autorRepository = autorRepository;
        this.autorValidator = autorValidator;
    }

     */

    public Autor salvar(Autor autor){

        autorValidator.validar(autor);

        return autorRepository.save(autor
        );
    }

    public Optional<Autor> recuperar(UUID id){
        return autorRepository.findById(id);
    }

    public void apagar(UUID id){
        autorRepository.deleteById(id);
    }

    public List<Autor> buscaPorNomeENAcionalidade(String nome, String nacionalidade){
        return autorRepository.buscaPorNomeENacionalidade(nome, nacionalidade);
    }

    public void atualizar(Autor autor){

        if(autor.getId() == null)
            throw new IllegalArgumentException("Id obrigatório");

        autorValidator.validar(autor);

        autorRepository.save(autor);
    }

    public List<Autor> buscarByExample(String nome, String nacionalidade) {
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, matcher);

        return autorRepository.findAll(autorExample);
    }
}
