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
@Table(name = "campus_history")
@Getter
@Setter
public class CampusHistory {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="campus_id", nullable=false)
    private int campusId;

    @Column(name="university_id")
    private int universityId;

    private String name;

    private String city;
    private String district;

    @Column(columnDefinition = "text")
    private String address;

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
