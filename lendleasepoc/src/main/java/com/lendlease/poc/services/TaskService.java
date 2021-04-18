package com.lendlease.poc.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendlease.poc.models.TaskDto;
import com.lendlease.poc.repository.TaskJpaRepository;
import com.lendlease.poc.repository.models.TaskEntity;
import com.lendlease.poc.spi.ITaskService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskService implements ITaskService {

    private final TaskJpaRepository taskJpaRepository;
    private final RedisTemplate redisTemplate;
    private final ChannelTopic taskTopic;
    private final ObjectMapper objectMapper;


    /*
     * Returns all tasks in the DB and store it in the cache (if cache has no entries yet),
     * otherwise returns the value of the cache
     */
    @Override
    @Cacheable("tasks")
    public List<TaskDto> getAllTasks() {
        return taskJpaRepository.findAll().stream()
                .map(TaskDto::new).collect(Collectors.toList());
    }

    /*
     * Saves a task normally in the DB
     * and evicts all entries of the queue
     */
    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    public TaskDto submitTask(TaskDto taskDto) {
        final TaskEntity saved = taskJpaRepository.save(new TaskEntity(taskDto));
        return new TaskDto(saved);
    }


    /*
     * Submit the task to be saved in the queue
     */
    @Override
    @SneakyThrows
    public void submitTaskToQueue(TaskDto taskDto) {
        log.info("Submitting task to queue");
        redisTemplate.convertAndSend(taskTopic.getTopic(), objectMapper.writeValueAsString(taskDto));
    }


    /*
     * @param taskId
     * Deletes the the task by id and evicts all entries of the queue
     */
    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    public void completeTask(BigInteger taskId) {
        taskJpaRepository.deleteById(taskId);
    }


}
