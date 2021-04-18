package com.lendlease.poc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendlease.poc.models.TaskDto;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaslIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private TaskDto task1;
    private TaskDto task2;

    @Before
    public void init() {
        task1 = new TaskDto(BigInteger.valueOf(1), "normal", "normal", LocalDate.now());
        task2 = new TaskDto(BigInteger.valueOf(1), "queue", "queue", LocalDate.now());
    }

    //First task was created
    @Test
    @SneakyThrows
    public void submitTask_shouldReturnTaskSubmitted() {
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                .content(objectMapper.writeValueAsString(task1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("$.['data'].['name']").value(task1.getName()))
                .andExpect(jsonPath("$.['data'].['dateCreated']").value(String.valueOf(task1.getDateCreated())))
                .andExpect(jsonPath("$.['data'].['description']").value(task1.getDescription()))
                .andExpect(jsonPath("$.['message']").value("Task successfully created"));

    }

    //Second task was submitted to Queue, then created
    @Test
    @SneakyThrows
    public void submitTaskToQueue_shouldReturnNothing() {
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/queue")
                .content(objectMapper.writeValueAsString(task2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['message']").value("Task successfully submitted to queue"));

    }

    //Only first task was retrieved, (due to delay going into queue, proof that it goes into queue)
    @Test
    @SneakyThrows
    public void getAllTask_shouldReturnNormalTaskOnlyDueToQueDelay() {
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("data", hasSize(1)))
                .andExpect(jsonPath("$.['message']").value("Tasks successfully retrieved"));
    }

    //First task was deleted
    @Test
    @SneakyThrows
    public void completeTask_shouldReturnNothing(){
        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['message']").value("Task completed"));
    }

    //Now queue task was retrieved
    @Test
    @SneakyThrows
    public void getAllTask_shouldReturnOneResultOnly(){
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("data", hasSize(1)))
                .andExpect(jsonPath("$.['message']").value("Tasks successfully retrieved"));
    }
}
