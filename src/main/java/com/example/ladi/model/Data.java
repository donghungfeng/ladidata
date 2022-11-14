package com.example.ladi.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Data {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "street")
    private String street;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "formcolor")
    private String formcolor;

    @Column(name = "status")
    private  int status;

    @Column(name = "date")
    private String date;

    @Column(name = "source")
    private String source;

    @Column(name = "ipaddress")
    private String ipAddress;

    @Column(name = "datechanged")
    private String dateChanged;
//
//    @Column(name = "nhanvienid")
//    private int nhanVienId;
}
