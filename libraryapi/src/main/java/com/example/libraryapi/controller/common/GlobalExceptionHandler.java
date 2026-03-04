package com.example.libraryapi.controller.common;

import com.example.libraryapi.controller.dto.ErroCampo;
import com.example.libraryapi.controller.dto.ErroResposta;
import com.example.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.example.libraryapi.exceptions.ResgistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT) // sempre vai retornar deste tipo
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){ // pode ser qquer nome

        List<FieldError> fieldErrors = ex.getFieldErrors();

        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(
                    fe -> new ErroCampo(
                            fe.getField(),
                            fe.getDefaultMessage())
                ).collect(Collectors.toList());


        return new ErroResposta(HttpStatus.UNPROCESSABLE_CONTENT.value(), "Erro validação", listaErros);
        
    }

    // tiramos o try ctach das controlller para tratar por aqui
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResgistroDuplicadoException.class)
    public ErroResposta handleRegistroDuplicadoException(ResgistroDuplicadoException ex){
        return ErroResposta.conflito(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    public ErroResposta handleResgistroDuplicadoException( OperacaoNaoPermitidaException ex){
        return ErroResposta.respostaPadrao(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErroResposta handleErroNaoTratado( RuntimeException ex){
        return new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado.",
                List.of());
    }
}
