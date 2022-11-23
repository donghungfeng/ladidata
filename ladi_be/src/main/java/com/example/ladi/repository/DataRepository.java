package com.example.ladi.repository;

import com.example.ladi.dto.WorkAssignDto;
import com.example.ladi.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DataRepository extends BaseRepository<Data> {
    List<Data> findAll();
    Data findAllById(int id);

    @Query(
        value = "SELECT d.id, d.country, d.`date`, d.district, d.formcolor, d.name, d.phone, d.source, d.state, d.status, d.street, d.ward, a.username"
        +" FROM data as d"
        +" INNER JOIN account as a"
        +" ON d.staff_id = a.id"
        +" WHERE (:status is null or d.status = :status) AND (d.date BETWEEN :startDate AND :endDate)"
        +" ORDER BY d.id DESC",
        nativeQuery = true, 
        resultSetMapping = "Mapping.PlayerNameDto")
    List<WorkAssignDto> findAllByStatus(@Param("status") Integer status, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(
        value = "SELECT * FROM data as d WHERE d.date > :startDate AND d.date < :endDate ORDER BY d.id DESC", 
        nativeQuery = true)
    List<Data> findAllByDate( @Param("startDate") String startDate, @Param("endDate") String endDate);
}
