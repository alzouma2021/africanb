package com.africanb.africanb.helper.dto.compagnie;

import com.africanb.africanb.helper.dto.offreVoyage.ValeurCaracteristiqueOffreVoyageDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.ToString;

/**
 * @Author Alzouma Moussa Mahamadou
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class AbonnementPeriodiqueDTO extends ModeAbonnementDTO {
    private long redevance;
    private long redevancePublicite;
}
