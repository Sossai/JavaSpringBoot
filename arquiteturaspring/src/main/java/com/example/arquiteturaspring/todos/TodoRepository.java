package com.example.arquiteturaspring.todos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {
    //boolean existByDescricao(String descricao);

    boolean findByDescricao(String descricao);
    boolean existsByDescricao(String descricao);
}
