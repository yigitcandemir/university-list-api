package com.example.project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="department_history")
@Getter
@Setter
public class DepartmentHistory {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="department_id")
    private int departmentId;

    @Column(name="faculty_id")
    private int facultyId;

    private String name;

    @Column(name="operation_type")
    private String operationType;

    @Column(name="opearated_by")
    private String operatedBy;;

    @Column(name="operated_at")
    private LocalDateTime operatedAt; 

    @PrePersist
    void onPersist() {
        if (operatedAt == null) operatedAt = LocalDateTime.now();
    }

}
