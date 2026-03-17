package com.example.libraryapi.service;

import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public void salvar(Usuario usuario){
        var pass = usuario.getSenha();
        usuario.setSenha(encoder.encode(pass));

        usuarioRepository.save(usuario);
    }

    public Usuario obterPorLogin(String login){
        return usuarioRepository.findByLogin(login);
    }
}
