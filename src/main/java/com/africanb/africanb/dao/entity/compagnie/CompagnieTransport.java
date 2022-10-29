package com.africanb.africanb.dao.entity.compagnie;

import com.sun.istack.NotNull;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * @Author Alzouma Moussa Mahamadou
 */
@Entity
@Table(name = "compagnietransport")
public class CompagnieTransport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @Column(unique = true , length = 50)
    @NotNull
    private String designation;
    @Lob
    private String description;
    @NotNull
    private Boolean isActif;
    @NotNull
    private String raisonSociale;
    @NotNull
    private String telephone;
    @NotNull
    private String sigle;
    @NotNull
    private String email;
    @ManyToOne
    private Ville ville;
    @ManyToOne
    private StatusUtil statusUtilActual;
    @Column(name="is_deleted")
    private Boolean    isDeleted ;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt ;
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
    public Boolean getActif() {
        return isActif;
    }
    public void setActif(Boolean actif) {
        isActif = actif;
    }
    public StatusUtil getStatusUtilActual() {
        return statusUtilActual;
    }
    public void setStatusUtilActual(StatusUtil statusUtilActual) {
        this.statusUtilActual = statusUtilActual;
    }
    public String getRaisonSociale() {
        return raisonSociale;
    }
    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getSigle() {
        return sigle;
    }
    public void setSigle(String sigle) {
        this.sigle = sigle;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Ville getVille() {
        return ville;
    }
    public void setVille(Ville ville) {
        this.ville = ville;
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
        CompagnieTransport that = (CompagnieTransport) o;
        return Objects.equals(id, that.id) && designation.equals(that.designation) && Objects.equals(description, that.description) && Objects.equals(isActif, that.isActif) && Objects.equals(statusUtilActual, that.statusUtilActual) && Objects.equals(raisonSociale, that.raisonSociale) && Objects.equals(telephone, that.telephone) && Objects.equals(sigle, that.sigle) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, designation, description, isActif, statusUtilActual, raisonSociale, telephone, sigle, email);
    }

    @Override
    public String toString() {
        return "CompagnieTransport{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", description='" + description + '\'' +
                ", isActif=" + isActif +
                ", statusUtilActual=" + statusUtilActual +
                ", raisonSociale='" + raisonSociale + '\'' +
                ", telephone='" + telephone + '\'' +
                ", sigle='" + sigle + '\'' +
                ", email='" + email + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
