package com.africanb.africanb.dao.entity.compagnie;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * @Author Alzouma Moussa Mahamadou
 */
@Entity
@Table(name = "statusutilcompagnietransport")
public class StatusUtilCompagnieTransport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @ManyToOne
    private CompagnieTransport compagnieTransport;
    @ManyToOne
    private StatusUtil statusUtil;
    @Column(name="is_deleted")
    private Boolean    isDeleted;


    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name="updated_by")
    private Long  updatedBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private Long  createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    private Long  deletedBy;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public CompagnieTransport getCompagnieTransport() {
        return compagnieTransport;
    }
    public void setCompagnieTransport(CompagnieTransport compagnieTransport) {
        this.compagnieTransport = compagnieTransport;
    }
    public StatusUtil getStatusUtil() {
        return statusUtil;
    }
    public void setStatusUtil(StatusUtil statusUtil) {
        this.statusUtil = statusUtil;
    }
    public Boolean getDeleted() {
        return isDeleted;
    }
    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Long getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    public Date getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
    public Long getDeletedBy() {
        return deletedBy;
    }
    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusUtilCompagnieTransport that = (StatusUtilCompagnieTransport) o;
        return Objects.equals(id, that.id) && Objects.equals(compagnieTransport, that.compagnieTransport) && Objects.equals(statusUtil, that.statusUtil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, compagnieTransport, statusUtil);
    }

    @Override
    public String toString() {
        return "StatusCompagnieTransport{" +
                "id=" + id +
                ", compagnieTransport=" + compagnieTransport +
                ", statusUtil=" + statusUtil +
                '}';
    }

}
