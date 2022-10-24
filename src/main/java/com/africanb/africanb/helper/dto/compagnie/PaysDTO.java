package com.africanb.africanb.helper.dto.compagnie;

import com.africanb.africanb.helper.searchFunctions.SearchParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class PaysDTO {

    //Proprietes
    private Long id ;
    private String designation;
    private String description;


    //Historisation
    private String updatedAt;
    private Long  updatedBy;
    private String createdAt;
    private Long  createdBy;
    private String deletedAt;
    private Long  deletedBy;


    // Search param
    private SearchParam<String> designationParam        ;
    private SearchParam<Boolean>  isDeletedParam        ;
    private SearchParam<String>   updatedAtParam        ;
    private SearchParam<Long>     updatedByParam        ;
    private SearchParam<String>   createdAtParam        ;
    private SearchParam<Long>     createdByParam        ;

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
    public String toString() {
        return "CompagnieTransportDTO{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", description='" + description + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", updatedBy=" + updatedBy +
                ", createdAt='" + createdAt + '\'' +
                ", createdBy=" + createdBy +
                ", deletedAt='" + deletedAt + '\'' +
                ", deletedBy=" + deletedBy +
                ", isDeletedParam=" + isDeletedParam +
                ", updatedAtParam=" + updatedAtParam +
                ", updatedByParam=" + updatedByParam +
                ", createdAtParam=" + createdAtParam +
                ", createdByParam=" + createdByParam +
                '}';
    }
}
