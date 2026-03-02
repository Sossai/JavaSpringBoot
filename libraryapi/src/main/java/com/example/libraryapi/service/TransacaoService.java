package com.example.libraryapi.service;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.repository.AutorRepository;
import com.example.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Transactional
    void atualizacaoSemAtualizar(){

        var livro = livroRepository
                .findById(UUID.fromString("71e7a680-250f-4a36-a3c4-773ac4ab5a9b"))
                .orElse(null);

        /**
         *  como abriu a transacao vai p/ o estado da managed
         *  quando terminar vai comitar com a alteracao da data
         *  sem precisar rodar o save
         */
        livro.setDataPublicacao(LocalDate.of(2025,01,01));
    }


    @Transactional
    public void executar(){

        Autor autor = new Autor();
        autor.setNome("Francisca 2");
        autor.setNacionalidade("Americana");
        autor.setDataNascimento(LocalDate.of(1920,1,1));
        autorRepository.save(autor);

        if(autor.getNome().equals("Francisca 2")){
            throw new RuntimeException("Force rollback!");
        }

        Livro livro = new Livro();
        livro.setIsbn("9999");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("Uma aventura de odin 2");
        livro.setDataPublicacao(LocalDate.of(2025,10,21));
        livro.setAutor(autor);

        livroRepository.save(livro);


    }
}
