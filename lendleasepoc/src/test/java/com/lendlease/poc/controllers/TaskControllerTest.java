package com.lendlease.poc.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendlease.poc.models.TaskDto;
import com.lendlease.poc.spi.ITaskService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@RunWith(SpringRunner.class)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ITaskService taskService;

    private TaskDto task1;
    private TaskDto task2;

    @Before
    public void init() {
        task1 = TaskDto.builder()
                .setName("TASK ONE")
                .setDescription("Meeting with stake holders")
                .build();
        task2 = TaskDto.builder()
                .setName("TASK TWO")
                .setDescription("Technology Discussion")
                .build();
    }

    @Test
    @SneakyThrows
    public void getAllTasks_shouldReturnListOfTasks() {
        Mockito.when(taskService.getAllTasks())
                .thenReturn(Arrays.asList(task1, task2));
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("$.['data'].[0].['name']").value(task1.getName()))
                .andExpect(jsonPath("$.['data'].[0].['description']").value(task1.getDescription()))
                .andExpect(jsonPath("$.['data'].[1].['name']").value(task2.getName()))
                .andExpect(jsonPath("$.['data'].[1].['description']").value(task2.getDescription()));
    }

    @Test
    @SneakyThrows
    public void normalSubmitTask_shouldReturnSubmittedTask() {
        Mockito.when(taskService.submitTask(any(TaskDto.class)))
                .thenReturn(task1);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                .content(objectMapper.writeValueAsString(task1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("$.['data'].['name']").value(task1.getName()))
                .andExpect(jsonPath("$.['data'].['description']").value(task1.getDescription()));
    }

    @Test
    @SneakyThrows
    public void queuedSubmitTask_shouldReturnSuccess() {
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/queue")
                .content(objectMapper.writeValueAsString(task1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void completeTask_shouldReturnSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/1")
                .content(objectMapper.writeValueAsString(task1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
