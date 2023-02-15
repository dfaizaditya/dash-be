package com.kbbukopin.projectdash.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue
    private Long id;
    private Long noTiket;

    @Enumerated(EnumType.STRING)
    private StatusQW statusQW;

    private String namaProyek;
    private String tipe;
    private String status;
    private String keterangan;
    private double rfc;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate dueDate;
}
