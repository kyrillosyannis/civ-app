package com.civiliansconnection.capp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Sector {

    @Id
    private Long id;

    @Column
    private String title;
}
