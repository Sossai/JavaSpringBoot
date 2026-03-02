package com.example.libraryapi.mappers;

import ch.qos.logback.core.model.ComponentModel;
import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    //@Mapping(source = "nome", target = "nomeAutor") // se campos diferentes
    Autor toEntity(AutorDTO dto);
    AutorDTO toDTO(Autor autor);
}
