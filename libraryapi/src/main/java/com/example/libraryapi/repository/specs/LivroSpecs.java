package com.example.libraryapi.repository.specs;

import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn){
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo){
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%"  );
    }




    public static Specification<Livro> generoEqual(GeneroLivro generoLivro){
        return (root, query, cb) -> cb.equal(root.get("genero"), generoLivro);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer ano){

/*
        return (root, query, cb) -> cb.equal(
                root.get("dataPublicacao")
              , ano.toString());
    }

 */
        // nao funfou
        /*
        return (root, query, builder) -> {
            var data = root.get("dataPublicacao");
            var anoExtraido = builder.function("YEAR", Integer.class, data);

            return builder.equal(anoExtraido, ano);
        };
        */

        //ok funciona
        //return (root, query, builder) -> {
        //    LocalDate startOfYear = LocalDate.of(ano, 1, 1);
        //    LocalDate endOfYear = LocalDate.of(ano, 12, 31);
        //    return builder.between(root.get("dataPublicacao"), startOfYear, endOfYear);
        //};


        //query =====> and to_char(data_publicacao, 'YYY') = :ano
        return (root, query, cb) ->
           cb.equal(cb.function("to_char", String.class,
                   root.get("dataPublicacao"), cb.literal("YYYY")), ano.toString());

    }

    public static Specification<Livro> nomeAutorLike(String nomeAutor){
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("autor").get("nome")), "%" + nomeAutor.toUpperCase() + "%"  );
    }

    // caso queira fazer um join distinto
    public static Specification<Livro> nomeAutorLike2(String nomeAutor){
        return (root, query, cb) ->{
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);

            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nomeAutor.toUpperCase() + "%");
        };

    }
}
