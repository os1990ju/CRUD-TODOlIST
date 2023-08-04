package com.prueba.crud.controllers;

import com.prueba.crud.models.entity.Task;
import com.prueba.crud.models.services.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TaskRestController {
    @Autowired
    private ITaskService taskService;

    /**
     * trae las tareas
     * @return
     */
    @GetMapping("/tasks")
    public List<Task> getTasks(){ return taskService.findAll(); }

    @PostMapping("/tasks")
    public ResponseEntity<?> createTask( @Valid @RequestBody Task task, BindingResult result ){
        Task newTask = null;
        Map<String,Object> response = new HashMap<>();

        //errors in body
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors ()
                    .stream()
                    .map(err -> "El campo'"+err.getField()+"'"+err.getDefaultMessage ())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            newTask = taskService.save(task);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al guardar la tarea en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","tarea insertada con exito!");
        response.put("Task",newTask);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }

    /**
     * metodo para actualizar tarea
     * @param task
     * @param result
     * @param id
     * @return
     */
    @PatchMapping("/tasks/{id}")
    public  ResponseEntity<?> updateTask(@Valid @RequestBody Task task, BindingResult result , @PathVariable Long id){

        Task currentTask = taskService.findById(id);
        Task taskUpdated = null;
        Map<String,Object> response = new HashMap<String,Object>();

        if(currentTask == null){
            response.put("mensaje", "Error: no se puede editar, la tarea ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        //manejo de error de reuqest body
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo'"+err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        try {

            currentTask.setName (task.getName());
            currentTask.setState (task.getState());
            currentTask.setExpireAt ( task.getExpireAt());
            taskUpdated = taskService.save(currentTask);

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al actualizar la tarea en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message","tarea actualizada con exito!");
        response.put("Task",taskUpdated);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }

    /**
     * metodo para eliminar una tarea
     * @param id
     * @return
     */
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        Map<String,Object> response = new HashMap<String,Object>();
        try {
            taskService.delete(id);
        } catch(DataAccessException e){
            response.put("Mensaje" , "Error al eliminar la tarea de la base de datos" );
            response.put("error" , e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Mensaje" , "Tarea eliminada con exito!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NO_CONTENT);
    }

}
