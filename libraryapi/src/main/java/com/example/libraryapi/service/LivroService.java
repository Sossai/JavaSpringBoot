package com.example.libraryapi.service;

import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.repository.LivroRepository;
import com.example.libraryapi.repository.specs.LivroSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor()
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro ){
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro){
        livroRepository.delete(livro);
    }

    //Specifications
    public List<Livro> buscarPorFiltros(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro generoLivro,
            Integer anoPublicacao
    ){
        // foi para class de specs
        //Specification<Livro> isbnEqual = (root, query, cb) -> cb.equal(root.get("isbn"), isbn);

        /*
        Specification<Livro> specs = Specification
                .where(LivroSpecs.isbnEqual(isbn))
                .and(LivroSpecs.tituloLike(titulo))
                .and(LivroSpecs.generoEqual(generoLivro));
         */

        //conjuntion = specs verdadeiro ( 0 = 0 )

        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if(isbn != null){
            //specs = specs.and(LivroSpecs.isbnEqual(isbn));
            specs = specs.and(isbnEqual(isbn));
        }

        if(titulo != null){
            //specs = specs.and(LivroSpecs.tituloLike(titulo));
            specs = specs.and(tituloLike(titulo));
        }

        if(generoLivro != null){
            //specs = specs.and(LivroSpecs.generoEqual(generoLivro));
            specs = specs.and(generoEqual(generoLivro));
        }

        if(anoPublicacao != null){
            //specs = specs.and(LivroSpecs.generoEqual(generoLivro));
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if(nomeAutor != null){
            //specs = specs.and(LivroSpecs.generoEqual(generoLivro));
            specs = specs.and(nomeAutorLike(nomeAutor));
        }


        return livroRepository.findAll(specs);
    }
}
