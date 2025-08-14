package com.example.project.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "faculty")
@Getter
@Setter
public class Faculty {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    public String getDeletedBy() {
        return deletedBy;
    }
    private String name;

    @ManyToOne
    @JoinColumn(name = "campus_id")
    @JsonBackReference
    private Campus campus;

    private String telephone;
    private String dean;

    @OneToMany(mappedBy="faculty", cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference
    private List<Department> department;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

    @Column(name = "created_at", updatable=false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "createdBy", updatable=false)
    private String createdBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private String deletedBy;

    public Faculty(){}

}
