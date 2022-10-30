package com.africanb.africanb.dao.entity.compagnie;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ville implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, length = 50)
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

}
