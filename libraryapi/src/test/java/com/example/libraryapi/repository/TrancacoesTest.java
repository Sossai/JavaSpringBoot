package com.example.libraryapi.repository;

import com.example.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TrancacoesTest {

    @Autowired
    TransacaoService transacaoService;

    //@Test
    void executarTest(){
        transacaoService.executar();
    }


}
