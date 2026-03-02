package com.example.arquiteturaspring.todos;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

//@Lazy
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON) // default
//@Scope(BeanDefinition.SCOPE_PROTOTYPE) // scoped
//@Scope(WebApplicationContext.SCOPE_APPLICATION) // default
//@Scope(WebApplicationContext.SCOPE_REQUEST) // default
//@Scope(WebApplicationContext.SCOPE_SESSION) // default


//@Scope("singleton") // default
//@Scope("request")
//@Scope("session")
//@Scope("application")
public class TodoValidator {

    private TodoRepository todoRepository;

    public TodoValidator(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public boolean operacaoValida(TodoEntity todo){
        if(todoRepository.existsByDescricao(todo.getDescricao()))
            return false;

        return true;
    }
}
