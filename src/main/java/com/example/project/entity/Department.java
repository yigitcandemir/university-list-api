package com.example.project.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "department")
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="faculty_id", nullable=false)
    @JsonBackReference
    private Faculty faculty;

    @Column(nullable=false)
    private String name;

    @Column(name = "created_at", updatable=false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_by", updatable=false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

}
