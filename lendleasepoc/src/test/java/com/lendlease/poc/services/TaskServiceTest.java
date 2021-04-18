package com.lendlease.poc.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendlease.poc.models.TaskDto;
import com.lendlease.poc.repository.TaskJpaRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Mock
    TaskJpaRepository taskJpaRepository;
    @Mock
    RedisTemplate redisTemplate;
    @Mock
    ChannelTopic channelTopic;
    @Mock
    ObjectMapper objectMapper;


    private TaskService taskService;

    @Before
    public void init() {
        taskService = new TaskService(taskJpaRepository, redisTemplate, channelTopic, objectMapper);
    }

    @Test
    public void getAllTask_shouldReturnAllTasks() {
        Mockito.when(taskService.getAllTasks())
                .thenReturn(new ArrayList<>());
        List<TaskDto> taskList = taskService.getAllTasks();
        assertThat(taskList.size(), Matchers.equalTo(0));
    }


}
