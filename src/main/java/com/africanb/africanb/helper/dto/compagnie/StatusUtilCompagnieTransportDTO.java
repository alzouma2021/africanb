package com.africanb.africanb.helper.dto.compagnie;

import com.africanb.africanb.dao.entity.compagnie.CompagnieTransport;
import com.africanb.africanb.dao.entity.compagnie.StatusUtil;
import com.africanb.africanb.helper.searchFunctions.SearchParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @Author Alzouma Moussa Mahamadou
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class StatusUtilCompagnieTransportDTO  {

    private Long id ;

    private Boolean  isDeleted;
    private String updatedAt;
    private Long  updatedBy;
    private String createdAt;
    private Long  createdBy;
    private String deletedAt;
    private Long  deletedBy;

    private Long compagnieTransportId;
    private Long statusUtilId;

    private SearchParam<Boolean>  isDeletedParam;
    private SearchParam<String>   compagnieTransportDesignationParam;
    private SearchParam<String>   statusUtilDesignationParam;
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
    public Long getCompagnieTransportId() {
        return compagnieTransportId;
    }
    public void setCompagnieTransportId(Long compagnieTransportId) {
        this.compagnieTransportId = compagnieTransportId;
    }
    public Long getStatusUtilId() {
        return statusUtilId;
    }
    public void setStatusUtilId(Long statusUtilId) {
        this.statusUtilId = statusUtilId;
    }
    public SearchParam<Boolean> getIsDeletedParam() {
        return isDeletedParam;
    }
    public void setIsDeletedParam(SearchParam<Boolean> isDeletedParam) {
        this.isDeletedParam = isDeletedParam;
    }
    public SearchParam<String> getCompagnieTransportDesignationParam() {
        return compagnieTransportDesignationParam;
    }
    public void setCompagnieTransportDesignationParam(SearchParam<String> compagnieTransportDesignationParam) {
        this.compagnieTransportDesignationParam = compagnieTransportDesignationParam;
    }
    public SearchParam<String> getStatusUtilDesignationParam() {
        return statusUtilDesignationParam;
    }
    public void setStatusUtilDesignationParam(SearchParam<String> statusUtilDesignationParam) {
        this.statusUtilDesignationParam = statusUtilDesignationParam;
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
        StatusUtilCompagnieTransportDTO that = (StatusUtilCompagnieTransportDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(compagnieTransportId, that.compagnieTransportId) && Objects.equals(statusUtilId, that.statusUtilId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, compagnieTransportId, statusUtilId);
    }

    @Override
    public String toString() {
        return "StatusUtilCompagnieTransportDTO{" +
                "id=" + id +
                ", isDeleted=" + isDeleted +
                ", updatedAt='" + updatedAt + '\'' +
                ", updatedBy=" + updatedBy +
                ", createdAt='" + createdAt + '\'' +
                ", createdBy=" + createdBy +
                ", deletedAt='" + deletedAt + '\'' +
                ", deletedBy=" + deletedBy +
                ", compagnieTransportId=" + compagnieTransportId +
                ", statusUtilId=" + statusUtilId +
                ", isDeletedParam=" + isDeletedParam +
                ", compagnieTransportDesignationParam=" + compagnieTransportDesignationParam +
                ", statusUtilDesignationParam=" + statusUtilDesignationParam +
                ", updatedAtParam=" + updatedAtParam +
                ", updatedByParam=" + updatedByParam +
                ", createdAtParam=" + createdAtParam +
                ", createdByParam=" + createdByParam +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                '}';
    }

}
