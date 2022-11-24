package com.example.ladi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "work")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Work extends BaseEntity{

    @Column(name = "timeint")
    private Long timeIn;

    @Column(name = "timeout")
    private Long timeOut;

    @Column(name = "don_giao")
    private int donGiao;

    @Column(name = "don_hoan_thanh")
    private int donHoanThanh;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "active")
    private int isActive;

    @ManyToOne
    @JoinColumn(name = "nhanvienid", nullable = false)
    Account account;
}
