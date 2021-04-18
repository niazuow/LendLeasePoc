package com.lendlease.poc.repository;

import com.lendlease.poc.repository.models.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface TaskJpaRepository extends JpaRepository<TaskEntity, BigInteger> {
}
