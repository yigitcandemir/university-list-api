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

@Entity
@Table(name = "universiteler")
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

        
    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public List<Campus> getCampuses() {
        return campuses;
    }

    public void setCampuses(List<Campus> campuses) {
        this.campuses = campuses;
    }

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

    public int getId()
    {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getWebsite(){
        return website;
    }
    public void setWebsite(String website){
        this.website = website;
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
    public String getDeletedBy(){
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

    public String getLogoBase64() {
        return logoBase64;
    }

    public void setLogoBase64(String logoBase64) {
        this.logoBase64 = logoBase64;
    }

}
