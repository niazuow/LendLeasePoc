package com.lendlease.poc.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lendlease.poc.models.TaskDto;
import com.lendlease.poc.spi.ITaskService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TaskController {

    private final ITaskService taskService;

    private String TASKS_RETRIEVED = "Tasks successfully retrieved";
    private String TASK_CREATED = "Task successfully created";
    private String TASK_SUBMITTED = "Task successfully submitted to queue";
    private String TASK_COMPLETED = "Task completed";

    @GetMapping
    public ResponseEntity<ResponseData> getAllTasks() {
        final List<TaskDto> list = taskService.getAllTasks();
        return ResponseEntity.ok(new ResponseData(list, TASKS_RETRIEVED));
    }

    @PostMapping
    public ResponseEntity<ResponseData> submitTask(@RequestBody TaskDto taskDto) {
        final TaskDto saved = taskService.submitTask(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseData(saved, TASK_CREATED));
    }

    @PostMapping("/queue")
    public ResponseEntity<ResponseData> submitTaskToQueue(@RequestBody TaskDto taskDto) {
        taskService.submitTaskToQueue(taskDto);
        return ResponseEntity.ok(new ResponseData(null, TASK_SUBMITTED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData> completeTask(@PathVariable BigInteger id) {
        taskService.completeTask(id);
        return ResponseEntity.ok(new ResponseData(null, TASK_COMPLETED));
    }

}

@AllArgsConstructor
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class ResponseData {
    private Object data;
    private String message;
}