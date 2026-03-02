package com.example.arquiteturaspring.todos;

import com.example.arquiteturaspring.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private TodoRepository repository;
    private TodoValidator todoValidator;

    @Autowired
    private AppProperties properties;

    public TodoService(TodoRepository repository, TodoValidator todoValidator) {
        this.repository = repository;
        this.todoValidator = todoValidator;
    }

    public TodoEntity salvar(TodoEntity novoTodo){

        //var properties = this.properties;
        System.out.println(properties.toString());


        if(todoValidator.operacaoValida(novoTodo))
            return repository.save(novoTodo);

        throw new IllegalArgumentException("Descricao duplicada");
    }

    public TodoEntity atualizarStatus(TodoEntity novoTodo){
        return repository.save(novoTodo);
    }

    public TodoEntity buscarPorId(Integer id){
        return repository.findById(id).orElse(null);
    }
}
