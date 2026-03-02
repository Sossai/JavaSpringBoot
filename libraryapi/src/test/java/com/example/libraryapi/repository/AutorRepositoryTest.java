package com.example.libraryapi.repository;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    //@Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1980,02,10));

        var autorSaved = autorRepository.save(autor);
    }

    //@Test
    public void gerAutor(){
        System.out.println("Start test");

        Optional<Autor> autor =autorRepository.findById(UUID.fromString("9e8a080a-4d03-4852-9f80-cae6a77d8c9a"));

        if(autor.isPresent())
            System.out.println(autor.toString());
        else
            System.out.println("Não encontrato");
    }

    //@Test
    void buscarLivroPorAutor()
    {
        System.out.println("Start test");
        var id = UUID.fromString("c53923af-b3d0-49ab-81d6-89a8796c9a2c");
        Autor autor = autorRepository.findById(id).orElse(null);

        List<Livro> livros = livroRepository.findByAutor(autor);


       //livros.forEach(System.out::println);


       for( Livro l : livros){
           System.out.println(l.getTitulo());
       }





    }
}
