package com.example.ladi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {
    List<T> findAll();
    T findAllById(int id);
}
