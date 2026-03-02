package com.example.libraryapi.repository;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    //@Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("1234");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO II");
        livro.setDataPublicacao(LocalDate.of(2020,1,2));

        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1920,1,1));

        livro.setAutor(autor);

        repository.save(livro);
    }

    //@Test
    @Transactional  // se tiver cascade = lay e acessar a referencia ele cria o select garantindo a transacao
    void buscarLivro(){
        UUID id = UUID.fromString("71e7a680-250f-4a36-a3c4-773ac4ab5a9b");
        Livro livro = repository.findById(id).orElse(null);

        System.out.println("Livro:" + livro.getTitulo());

        // vai rodar mais um select pegando o autor
        System.out.println("Autor:" + livro.getAutor().getNome());
    }

    //@Test
    void listarTodosLivros(){
        List<Livro> livros = repository.listarTodos();

        livros.forEach(System.out::println);

        for (Livro l : livros){
            System.out.println(l.getTitulo());
        }
    }


//    @Test
    void listarTodosAutores(){
        List<Autor> autores = repository.listarTodosAutores();

        autores.forEach(System.out::println);
    }

    //@Test
    void listarPorGenero(){
        List<Livro> livros = repository.listarPorGenero(GeneroLivro.FICCAO, "preco");

        livros.forEach(System.out::println);
    }
}