package com.example.ladi.repository;

import com.example.ladi.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataRepository extends JpaRepository<Data, Integer> {
    List<Data> findAll();
    Data findAllById(int id);
}
