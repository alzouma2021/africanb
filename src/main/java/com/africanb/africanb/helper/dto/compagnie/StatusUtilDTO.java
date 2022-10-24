package com.africanb.africanb.helper.dto.compagnie;

import com.africanb.africanb.dao.entity.compagnie.FamilleStatusUtil;
import com.africanb.africanb.helper.searchFunctions.SearchParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Alzouma Moussa Mahamadou
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class StatusUtilDTO {

    private Long id ;
    private String designation;
    private String description;

    private Boolean  isDeleted;
    private String updatedAt;
    private Long  updatedBy;
    private String createdAt;
    private Long  createdBy;
    private String deletedAt;
    private Long  deletedBy;

    private Long familleStatusUtilId;
    private String familleStatusUtilDesignation;

    private SearchParam<String> designationParam;
    private SearchParam<Boolean>  isDeletedParam;
    private SearchParam<String>   updatedAtParam;
    private SearchParam<Long>     updatedByParam;
    private SearchParam<String>   createdAtParam;
    private SearchParam<Long>     createdByParam;

    private String orderField;
    private String orderDirection;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Boolean getDeleted() {
        return isDeleted;
    }
    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Long getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public Long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    public String getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
    public Long getDeletedBy() {
        return deletedBy;
    }
    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }
    public Long getFamilleStatusUtilId() {
        return familleStatusUtilId;
    }
    public void setFamilleStatusUtilId(Long familleStatusUtilId) {
        this.familleStatusUtilId = familleStatusUtilId;
    }
    public String getFamilleStatusUtilDesignation() {
        return familleStatusUtilDesignation;
    }
    public void setFamilleStatusUtilDesignation(String familleStatusUtilDesignation) {
        this.familleStatusUtilDesignation = familleStatusUtilDesignation;
    }
    public SearchParam<String> getDesignationParam() {
        return designationParam;
    }
    public void setDesignationParam(SearchParam<String> designationParam) {
        this.designationParam = designationParam;
    }
    public SearchParam<Boolean> getIsDeletedParam() {
        return isDeletedParam;
    }
    public void setIsDeletedParam(SearchParam<Boolean> isDeletedParam) {
        this.isDeletedParam = isDeletedParam;
    }
    public SearchParam<String> getUpdatedAtParam() {
        return updatedAtParam;
    }
    public void setUpdatedAtParam(SearchParam<String> updatedAtParam) {
        this.updatedAtParam = updatedAtParam;
    }
    public SearchParam<Long> getUpdatedByParam() {
        return updatedByParam;
    }
    public void setUpdatedByParam(SearchParam<Long> updatedByParam) {
        this.updatedByParam = updatedByParam;
    }
    public SearchParam<String> getCreatedAtParam() {
        return createdAtParam;
    }
    public void setCreatedAtParam(SearchParam<String> createdAtParam) {
        this.createdAtParam = createdAtParam;
    }
    public SearchParam<Long> getCreatedByParam() {
        return createdByParam;
    }
    public void setCreatedByParam(SearchParam<Long> createdByParam) {
        this.createdByParam = createdByParam;
    }
    public String getOrderField() {
        return orderField;
    }
    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }
    public String getOrderDirection() {
        return orderDirection;
    }
    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusUtilDTO that = (StatusUtilDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(designation, that.designation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, designation);
    }

    @Override
    public String toString() {
        return "StatusUtilDTO{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", description='" + description + '\'' +
                ", isDeleted=" + isDeleted +
                ", updatedAt='" + updatedAt + '\'' +
                ", updatedBy=" + updatedBy +
                ", createdAt='" + createdAt + '\'' +
                ", createdBy=" + createdBy +
                ", deletedAt='" + deletedAt + '\'' +
                ", deletedBy=" + deletedBy +
                ", familleStatusUtilId=" + familleStatusUtilId +
                ", familleStatusUtilDesignation='" + familleStatusUtilDesignation + '\'' +
                ", designationParam=" + designationParam +
                ", isDeletedParam=" + isDeletedParam +
                ", updatedAtParam=" + updatedAtParam +
                ", updatedByParam=" + updatedByParam +
                ", createdAtParam=" + createdAtParam +
                ", createdByParam=" + createdByParam +
                '}';
    }

}
