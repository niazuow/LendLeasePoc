package com.lendlease.poc.repository.models;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.lendlease.poc.models.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity(name = "TASK")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private BigInteger id;
    @Column(name = "NAME")
    private String name;
    @Column(name="DESCRIPTION")
    private String description;
    @Column(name = "DATE_CREATED")
    private LocalDate dateCreated;


    public TaskEntity(TaskDto taskDto) {
        BeanUtils.copyProperties(taskDto, this);
    }

    @PostPersist
    public void init(){
        dateCreated = LocalDate.now();
    }
}
