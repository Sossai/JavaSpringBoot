package com.example.libraryapi.mappers;

import com.example.libraryapi.controller.dto.CadastroLivroDTO;
import com.example.libraryapi.controller.dto.ResultadoLivroDTO;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        uses = AutorMapper.class    // faz automatico sem precisar do expression comentado (autorMapper.toDTO)
)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    AutorMapper autorMapper;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(cadastroLivroDTO.idAutor()).orElse(null) )")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "idUsuario", ignore = true)
    public abstract Livro toEntity(CadastroLivroDTO cadastroLivroDTO);


    //@Mapping(target = "autor", expression = "java( autorMapper.toDTO(livro.getAutor()) )")
    public abstract ResultadoLivroDTO toResultado(Livro livro);

}
