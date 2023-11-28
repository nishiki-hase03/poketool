package com.nhworks.poketool.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="ptuser")
@Data
public class User implements Serializable{
    
    @Id
    private String id;
    private String password;
    private String mailAddress;
    private String userName;

}
