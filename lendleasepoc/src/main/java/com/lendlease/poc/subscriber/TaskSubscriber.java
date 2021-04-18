package com.lendlease.poc.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendlease.poc.models.TaskDto;
import com.lendlease.poc.repository.TaskJpaRepository;
import com.lendlease.poc.repository.models.TaskEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskSubscriber implements MessageListener {
    private final TaskJpaRepository taskJpaRepository;
    private final ObjectMapper objectMapper;

    /*
     * @param message
     * @param bytes
     * Called everytime a message is consumed on the queue (tasks topic).
     * Saves the serialized message value and deletes the cache entries.
     */
    @Override
    @SneakyThrows
    @CacheEvict(value = "tasks", allEntries = true)
    public void onMessage(Message message, byte[] bytes) {
        log.info("Received new task from queue: {} ", message.toString());
        final TaskDto taskDto = objectMapper.readValue(message.toString(), TaskDto.class);
        taskJpaRepository.save(new TaskEntity(taskDto));
    }
}
