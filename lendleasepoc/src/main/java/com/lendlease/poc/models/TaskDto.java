package com.lendlease.poc.models;

import com.lendlease.poc.repository.models.TaskEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class TaskDto {
    @ApiModelProperty( hidden = true)
    private BigInteger id;
    private String name;
    private String description;
    @ApiModelProperty( hidden = true)
    private LocalDate dateCreated;

    public TaskDto(TaskEntity taskEntity) {
        BeanUtils.copyProperties(taskEntity, this);
    }
}
