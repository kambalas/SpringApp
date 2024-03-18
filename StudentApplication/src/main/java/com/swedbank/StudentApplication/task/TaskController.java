package com.swedbank.StudentApplication.task;

import com.swedbank.StudentApplication.group.Group;
import com.swedbank.StudentApplication.group.GroupService;
import com.swedbank.StudentApplication.person.Person;
import com.swedbank.StudentApplication.person.PersonController;
import com.swedbank.StudentApplication.person.exception.PersonNotFoundException;
import com.swedbank.StudentApplication.task.exceptiion.TaskNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/tasks")
public class TaskController {
    private static Logger log = LoggerFactory.getLogger(PersonController.class);
    private TaskService service;
    private GroupService groupService;

    @Autowired
    public TaskController(TaskService service, GroupService groupService){
        this.service = service;
        this.groupService = groupService;
    }


    @GetMapping(produces = "application/json")
    ResponseEntity<List<Task>> getAllTasks() {
        List<Task> list = service.findAll();
        log.info("Collection size " + list.size());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("{id}")
    ResponseEntity<Task> getByid(@PathVariable("id") Long id)
            throws TaskNotFoundException {
        Task task = service.findById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    @PostMapping
    ResponseEntity<Task> saveTask(@RequestBody Task task){
        Task savedTask =  service.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) throws PersonNotFoundException{

        service.delete(id);
        return ResponseEntity.ok().build();
    }
    @PatchMapping
    public ResponseEntity<Task> updateTask(@RequestBody final Task task){
        Task updateTask = service.update(task);
        return new ResponseEntity<>(updateTask, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAllTasks(){
        service.deleteAll();
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/{taskId}/group/{groupId}")
    public ResponseEntity<?> assignTaskToGroup(@PathVariable Long taskId, @PathVariable Long groupId) {
        Task task = service.findById(taskId);
        Group group = groupService.findById(groupId); // Assume you have a method to find a group by ID

        task.setGroup(group);
        service.save(task); // Save the updated task

        return ResponseEntity.ok().build();
    }

}
