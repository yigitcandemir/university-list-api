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
@Table(name="faculty_history")
@Getter
@Setter
public class FacultyHistory {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="faculty_id", nullable=false)
    private int facultyId;

    private String name;

    private String telephone;
    private String dean;

    @Column(name="campus_id")
    private int campusId;


    @Column(name="action_type")
    private String actionType; 

    @Column(name="action_by")
    private String actionBy;

    @Column(name="action_at")
    private LocalDateTime actionAt; 

    @PrePersist
    void onPersist() {
        if (actionAt == null) actionAt = LocalDateTime.now();
    }

}
