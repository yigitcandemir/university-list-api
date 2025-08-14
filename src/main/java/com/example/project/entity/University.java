package com.example.project.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "universiteler")
@Getter
@Setter
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String website;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Campus> campuses;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

    @Column(name = "created_at", updatable=false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_by", updatable=false)
    private String createdBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "logo_base64", columnDefinition="LONGTEXT")
    private String logoBase64;

    @Column(name = "logo_content_type")
    private String logoContentType;

    public University() {}

    public University(String name, String website) {
        this.name = name;
        this.website = website;
    }

    public University(int id, String name, String website) {
        this.id = id;
        this.name = name;
        this.website = website;
    }

}
