package com.example.ladi.repository;

import com.example.ladi.dto.DataAssginDto;
import com.example.ladi.model.Data;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends BaseRepository<Data> {
    List<Data> findAll();
    Data findAllById(int id);

    @Query(name = "find_DataAssgnDto", nativeQuery = true)
    List<DataAssginDto> findAllByStatus(@Param("status") Integer status, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(
        value = "SELECT d.*, a.username as staffName"
        +" FROM data as d"
        +" LEFT JOIN account as a"
        +" ON d.staff_id = a.id"
        +" WHERE d.date BETWEEN :startDate AND :endDate"
        +" ORDER BY d.id DESC", 
        nativeQuery = true)
    List<Data> findAllByDate( @Param("startDate") String startDate, @Param("endDate") String endDate);
}
