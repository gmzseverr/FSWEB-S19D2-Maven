package com.workintech.s18d4.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="address",schema="bank")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="street")
    private String street;

    @Column(name="no")
    private Integer no;

    @Column(name="city")
    private String city;

    @Column(name="country")
    private String country;

    @Column(name="description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "address")
    private Customer customer;



}
