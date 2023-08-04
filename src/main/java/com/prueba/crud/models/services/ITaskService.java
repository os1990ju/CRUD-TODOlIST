package com.prueba.crud.models.services;

import com.prueba.crud.models.entity.Task;

import java.util.List;

public interface ITaskService {
    public List<Task> findAll();
    public Task findById(Long id);
    public Task save(Task task);
    public void delete(Long id);
}
