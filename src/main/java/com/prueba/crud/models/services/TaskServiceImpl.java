package com.prueba.crud.models.services;

import com.prueba.crud.models.dao.ITaskDao;
import com.prueba.crud.models.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class TaskServiceImpl implements ITaskService{
    @Autowired
    private ITaskDao taskDao;

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll( ) {
        return (List<Task>) taskDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Task findById( Long id ) {
        return taskDao.findById(id).orElse(null);
    }

    @Override
    public Task save( Task task ) {
        return taskDao.save(task);
    }

    @Override
    public void delete( Long id ) {
        taskDao.deleteById(id);
    }


}
