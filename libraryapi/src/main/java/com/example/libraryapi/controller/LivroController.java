package com.example.libraryapi.controller;

import com.example.libraryapi.controller.dto.CadastroLivroDTO;
import com.example.libraryapi.controller.dto.ResultadoLivroDTO;
import com.example.libraryapi.mappers.AutorMapper;
import com.example.libraryapi.mappers.LivroMapper;
import com.example.libraryapi.model.GeneroLivro;
import com.example.libraryapi.model.Livro;
import com.example.libraryapi.security.SecurityService;
import com.example.libraryapi.service.LivroService;
import com.example.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("livros")
public class LivroController implements GenericController{

    private final LivroService livroService;
    private final LivroMapper livroMapper;
    private final AutorMapper autorMapper;
    private final UsuarioService usuarioService;
    private final SecurityService securityService;


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")  // ==> semelhante ao config //authorize.requestMatchers("/livros/**").hasAnyRole("ADMIN", "USER");
    public ResponseEntity<Void> salvar(
            @RequestBody @Valid CadastroLivroDTO livroDto
            //,Authentication authentication
            ){
        // removi os try/catch pq estao sendo tratadas nos handlers
        //try{

        //var usuarioLogado = (UserDetails) authentication.getPrincipal();
        //var usuario = usuarioService.obterPorLogin(usuarioLogado.getUsername());
        //ou
        var usuario = securityService.ObterUsuarioLogado();

        Livro livro = livroMapper.toEntity(livroDto);
        livro.setIdUsuario(usuario.getId());

        livroService.salvar(livro);

        var location = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(location).build();

        //}catch (ResgistroDuplicadoException ex){
        //    var erroDTO = ErroResposta.conflito(ex.getMessage());
        //    return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        //}
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ResultadoLivroDTO> buscar(
            @PathVariable String id,
            Authentication authentication
    ){
        //var livro = livroService.obterPorId(UUID.fromString(id));
        //return ResponseEntity.ok(livroMapper.toResultado(livro));




        return livroService
                .obterPorId(UUID.fromString(id))
                .map(livro -> {
                    ResultadoLivroDTO dto = livroMapper.toResultado(livro);
                    return ResponseEntity.ok(livroMapper.toResultado(livro));
                }).orElseGet(()-> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return livroService
                .obterPorId(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<ResultadoLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro generoLivro,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,

            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ){

        Page<Livro> paginaResultado = livroService.buscarPorFiltros(
                isbn, titulo, nomeAutor, generoLivro, anoPublicacao, pagina,tamanhoPagina);

        Page<ResultadoLivroDTO> resultado = paginaResultado.map(livroMapper::toResultado);

        // usado quado retornar a lista e não p page
        /*
        var lista = resultado
                .stream()
                .map(livroMapper::toResultado)
                .collect(Collectors.toList());

         */
        //return ResponseEntity.ok(lista);

        return ResponseEntity.ok(resultado);
    }

}
