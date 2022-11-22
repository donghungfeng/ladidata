package com.example.ladi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "config")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Config extends BaseEntity{
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "status")
    private int status;

    @Column(name = "note")
    private String note;
}
