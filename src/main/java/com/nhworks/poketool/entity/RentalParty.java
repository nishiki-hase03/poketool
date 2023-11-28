package com.nhworks.poketool.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="rentalparty")
@Data
public class RentalParty {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "mailAddress")
    private String mailAddress;

    @Column(name = "rentalId")
    private String rentalId;
    
    @Column(name = "introduce")
    private String introduce;
    
}
