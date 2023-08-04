package com.prueba.crud.models.dao;

import com.prueba.crud.models.entity.Task;
import org.springframework.data.repository.CrudRepository;

public interface ITaskDao extends CrudRepository<Task, Long> {
}
