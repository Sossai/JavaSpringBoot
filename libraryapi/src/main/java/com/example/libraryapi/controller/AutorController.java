package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.AutorDTO;
import com.example.libraryapi.controller.dto.ErroResposta;
import com.example.libraryapi.exceptions.ResgistroDuplicadoException;
import com.example.libraryapi.mappers.AutorMapper;
import com.example.libraryapi.model.Autor;
import com.example.libraryapi.repository.AutorRepository;
import com.example.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("autores")
public class AutorController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;
/*
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

 */

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autorDTO){

        try{
            //var autor = autorService.salvar(autorDTO.toAutor());
            var autor = autorService.salvar(autorMapper.toEntity(autorDTO));

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autor.getId())
                    .toUri();


            //return new ResponseEntity("Salvo com sucesso", HttpStatus.CREATED);
            return ResponseEntity.created(location).build();

        }catch (ResgistroDuplicadoException e){

            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> recuperarAutor(@PathVariable("id") String id){
        /*
        Optional<Autor> autorOptional = autorService.recuperar(UUID.fromString(id));
        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();

            AutorDTO autorDTO = new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade()
            );

            //AutorDTO autorDTO = autorMapper.toDTO(autor);
            return ResponseEntity.ok(autorDTO);
        }

        return ResponseEntity.notFound().build();

        */
        // .map aplica se tem valor no optional

        return autorService
                .recuperar(UUID.fromString(id))
                .map(autor -> {
                    AutorDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(()-> ResponseEntity.notFound().build());



    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(@PathVariable("id") String id){

        var autorId = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.recuperar(autorId);

        if(!autorOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }

        autorService.apagar(UUID.fromString(id));

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> buscaAutor(
            @RequestParam(value = "nome" ,required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade)
    {

        //List<Autor> autores = autorService.buscaPorNomeENAcionalidade(nome, nacionalidade);
        List<Autor> autores = autorService.buscarByExample(nome, nacionalidade);

/*
        List<AutorDTO> autorDTOList = new ArrayList<>();
        for (Autor a : autores)
        {
            autorDTOList.add(new AutorDTO(
                    a.getId(),
                    a.getNome(),
                    a.getDataNascimento(),
                    a.getNacionalidade()));
        }

 */

        List<AutorDTO> autorDTOList = autores
                .stream()
                .map(
                    autor -> new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade())
                ).collect(Collectors.toList());


        return ResponseEntity.ok(autorDTOList);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@Valid @PathVariable("id") String id, @RequestBody AutorDTO autorDTO){

        try {
            var autorId = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.recuperar(autorId);

            if (!autorOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Autor autor = autorOptional.get();
            autor.setNome(autorDTO.nome());
            autor.setDataNascimento(autorDTO.dataNascimento());
            autor.setNacionalidade(autorDTO.nacionalidade());

            autorService.atualizar(autor);

            return ResponseEntity.noContent().build();
        }catch (ResgistroDuplicadoException e){
            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }
}
