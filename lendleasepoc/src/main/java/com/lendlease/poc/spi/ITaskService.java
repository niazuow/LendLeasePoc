package com.lendlease.poc.spi;

import com.lendlease.poc.models.TaskDto;

import java.math.BigInteger;
import java.util.List;

public interface ITaskService {

    List<TaskDto> getAllTasks();

    TaskDto submitTask(TaskDto taskDto);

    void submitTaskToQueue(TaskDto taskDto);

    void completeTask(BigInteger taskId);
}
