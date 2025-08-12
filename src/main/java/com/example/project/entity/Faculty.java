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

@Entity
@Table(name = "faculty")
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

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public String getName(){
        return name;
    } 
    public void setName(String name){
        this.name = name;
    }
    public Campus getCampus(){
        return campus;
    }
    public void setCampus(Campus campus){
        this.campus = campus;
    }
    public String getTelephone(){
        return telephone;
    }
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
    public String getDean(){
        return dean;
    }
    public void setDean(String dean){
        this.dean = dean;
    }
    public List<Department> getDepartment(){
        return department;
    }
    public void setDepartment(List<Department> department){
        this.department = department;
    }
    public Boolean isDeleted(){
        return deleted;
    }
    public void setDeleted(Boolean deleted){
        this.deleted = deleted;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }
    public LocalDateTime getDeletedAt(){
        return deletedAt;
    }
    public void setDeletedAt(LocalDateTime deletedAt){
        this.deletedAt = deletedAt;
    }
    public String getDeleteBy(){
        return deletedBy;
    }
    public void setDeletedBy(String deletedBy){
        this.deletedBy = deletedBy;
    }
    public String getCreatedBy(){
        return createdBy;
    }
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }
    public String getUpdatedBy(){
        return updatedBy;
    } 
    public void setUpdatedBy(String updatedBy){
        this.updatedBy = updatedBy;
    }
}
