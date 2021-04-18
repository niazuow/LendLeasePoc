package com.lendlease.poc.models;

import org.hamcrest.Matchers;
import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


import java.math.BigInteger;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
public class TaskDtoTest {

    @Test
    public void testShouldConstruct() {
        TaskDto task = new TaskDto(BigInteger.valueOf(1), "string", "string", LocalDate.now());
        Assert.assertEquals(BigInteger.valueOf(1), task.getId());
        Assert.assertThat(task.getName(), Matchers.equalToIgnoringCase("string"));
        Assert.assertEquals(LocalDate.now(), task.getDateCreated());
    }
}
