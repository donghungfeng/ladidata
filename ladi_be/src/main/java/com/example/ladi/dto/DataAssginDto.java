package com.example.ladi.dto;

import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.ColumnResult;

@Entity

@SqlResultSetMapping(name = "DataAssginDto",
                     classes = @ConstructorResult(targetClass = DataAssginDto.class,
                                                  columns = {@ColumnResult(name = "id"),
                                                             @ColumnResult(name = "country"),
                                                             @ColumnResult(name = "date"),
                                                             @ColumnResult(name = "district"),
                                                             @ColumnResult(name = "formColor"),
                                                             @ColumnResult(name = "name"),
                                                             @ColumnResult(name = "phone"),
                                                             @ColumnResult(name = "source"),
                                                             @ColumnResult(name = "state"),
                                                             @ColumnResult(name = "status"),
                                                             @ColumnResult(name = "street"),
                                                             @ColumnResult(name = "ward"),
                                                             @ColumnResult(name = "staffId"),
                                                             @ColumnResult(name = "staffName")}))
@NamedNativeQuery(name = "find_DataAssgnDto",
                    query = "SELECT d.*, a.username as staffName"
                    +" FROM data as d"
                    +" LEFT JOIN account as a"
                    +" ON d.staff_id = a.id"
                    +" WHERE (:status is null or d.status = :status) AND (d.date BETWEEN :startDate AND :endDate)"
                    +" ORDER BY d.id DESC",
                    resultSetMapping = "DataAssginDto")
public class DataAssginDto {
    // private int id;
    // private String country;
    // private String date;
    // private String district;
    // private String formColor;
    // private String name;
    // private String phone;
    // private String source;
    // private String state;
    // private  int status;
    // private String street;
    // private String ward;
    // // private String ipAddress;
    // // private String dateChanged;
    // private int staffId;
    // private String staffName;
}
