package com.example.ladi.repository;

import com.example.ladi.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DataRepository extends BaseRepository<Data> {
}
