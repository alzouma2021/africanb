package com.africanb.africanb.Business.offreVoyage;

import com.africanb.africanb.dao.entity.offreVoyage.ValeurCaracteristiqueOffreVoyage;
import com.africanb.africanb.helper.dto.offreVoyage.ValeurCaracteristiqueOffreVoyageDTO;

import java.text.ParseException;
import java.util.Locale;

public interface ValeurCaracteristiqueOffreVoyageInterface {
    public ValeurCaracteristiqueOffreVoyageDTO saveValeurCaracteristiqueOffreVoyageDTOEnFonctionDuTypeDeLaPropriete(ValeurCaracteristiqueOffreVoyageDTO valeurCaracteristiqueOffreVoyageDTO, Locale locale) throws ParseException;
}
