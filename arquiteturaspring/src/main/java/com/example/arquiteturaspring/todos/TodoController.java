package com.example.arquiteturaspring.todos;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private TodoService service;

    public TodoController(TodoService service){
        this.service = service;
    }

    @PostMapping
    public TodoEntity salvar(@RequestBody TodoEntity todo){
        try{
            return service.salvar(todo);
        }catch(Exception ex){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "teste", ex);

        }


    }



    @PutMapping("/{id}")
    public void atualizarStatus(@PathVariable Integer id, @RequestBody TodoEntity todo){
        todo.setId(id);
        service.atualizarStatus(todo);
    }
    @GetMapping("/{id}")
    public TodoEntity buscar(@PathVariable Integer id){
        return service.buscarPorId(id);
    }
}
