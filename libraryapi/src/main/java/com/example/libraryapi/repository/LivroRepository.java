package com.example.libraryapi.repository;

import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    //busca paginada
    Page<Livro> findByAutor(Autor autr, Pageable pageable);

    //query method
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    Optional<Livro> findByIsbn(String isbn);

    // JPQL
    // select l.* from livro as l order by l.titulo
    @Query(" select l from Livro as l order by l.titulo ")
    List<Livro> listarTodos();

    /**
     *select a
     * from livro l
     * join autor a
     * on l.id_autor = a.id
     */
    //@Query("select a from Livro l join l.autor a")
    @Query("""
            select a
            from Livro l
            join l.autor a
            """)
    List<Autor> listarTodosAutores();

    // named parameters
    @Query(" select l from Livro l where l.genero = :nomeDoParametro order by :ordem")
    List<Livro> listarPorGenero(
            @Param("nomeDoParametro") GeneroLivro generoLivro,
            @Param("ordem") String nomePropriedade
    );

    // positional parameters
    @Query(" select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> listarPorGeneroPositionalParameters(
            GeneroLivro generoLivro,
            String nomePropriedade
    );

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);
}
