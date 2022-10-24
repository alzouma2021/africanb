package com.africanb.africanb.dao.entity.compagnie;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * @author Alzouma Moussa Mahamadou
 */
@Entity
@Table(name = "ville" )
public class Ville implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @Column(unique = true, length = 50)
    @NotNull
    private String designation;
    @Lob
    private String description;
    @Column(name="is_deleted")
    private Boolean isDeleted;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name="updated_by")
    private Long updatedBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private Long createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    private Long deletedBy;

    @ManyToOne
    private Pays pays;

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
    public Pays getPays() {
        return pays;
    }
    public void setPays(Pays pays) {
        this.pays = pays;
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
        Ville ville = (Ville) o;
        return Objects.equals(id, ville.id) && Objects.equals(designation, ville.designation) && Objects.equals(pays, ville.pays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, designation, pays);
    }

    @Override
    public String toString() {
        return "Ville{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
