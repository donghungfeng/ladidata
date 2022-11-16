package com.example.ladi.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Account extends BaseEntity{
    @Column(name = "username", unique = true, nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String passWord;

    @Column(name = "active")
    private int active;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "note")
    private String note;

    @Column(name = "role")
    private String role;
}
