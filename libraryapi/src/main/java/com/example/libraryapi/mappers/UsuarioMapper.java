package com.example.libraryapi.mappers;

import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.controller.dto.UsuarioDTO;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDTO dto);
    UsuarioDTO toDTO(Usuario usuario);
}
