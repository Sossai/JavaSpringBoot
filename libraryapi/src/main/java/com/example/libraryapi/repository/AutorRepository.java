package com.example.libraryapi.repository;

import com.example.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {


    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);
    List<Autor> findByNome(String nome);
    List<Autor> findByNacionalidade(String nacionalidade);

    Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(
            String nome,
            LocalDate dataNascimento,
            String nacionalidade
    );

    boolean existsByNomeAndDataNascimentoAndNacionalidade(
            String nome,
            LocalDate dataNascimento,
            String nacionalidade
    );


    @Query("""
            select a
            from Autor a
            where a.nome like %:nome% and
            a.nacionalidade like %:nacionalidade%
            """)
    List<Autor> buscaPorNomeENacionalidade(String nome, String nacionalidade);


/*
    @Query("""
            update Autor
            set nome = :nome,
            set data_nacimento = :dataNascimento,
            set nacionalidade = :nacionalidade
            where id = :idAutor
            """)
    void updateAutor(UUID idAutor, Autor autor);
*/


}
