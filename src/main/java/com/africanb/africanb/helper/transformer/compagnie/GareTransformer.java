package com.africanb.africanb.helper.transformer.compagnie;


import com.africanb.africanb.dao.entity.compagnie.CompagnieTransport;
import com.africanb.africanb.dao.entity.compagnie.Gare;
import com.africanb.africanb.helper.contrat.FullTransformerQualifier;
import com.africanb.africanb.helper.dto.compagnie.GareDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface GareTransformer {

    GareTransformer INSTANCE = Mappers.getMapper(GareTransformer.class);

    @FullTransformerQualifier
    @Mappings({
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.designation", target = "designation"),
            @Mapping(source = "entity.description", target = "description"),

            @Mapping(source = "entity.telephone1", target = "telephone1"),
            @Mapping(source = "entity.telephone2", target = "telephone2"),
            @Mapping(source = "entity.email", target = "email"),
            @Mapping(source = "entity.adresseLocalisation", target = "adresseLocalisation"),

            @Mapping(source = "entity.compagnieTransport.raisonSociale", target = "compagnieTransportRaisonSociale"),

            @Mapping(source = "entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
            @Mapping(source = "entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
            @Mapping(source = "entity.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),

            @Mapping(source = "entity.updatedBy", target="updatedBy"),
            @Mapping(source = "entity.createdBy", target="createdBy"),
            @Mapping(source = "entity.deletedBy", target="deletedBy"),
            @Mapping(source = "entity.isDeleted", target="isDeleted"),
    })
    GareDTO toDto(Gare entity) throws ParseException;;

    @IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<GareDTO> toDtos(List<Gare> entities) throws ParseException;

    default GareDTO toLiteDto(Gare entity) {
        if (entity == null) {
            return null;
        }

        GareDTO dto = new GareDTO();
        dto.setId( entity.getId() );
        dto.setDesignation( entity.getDesignation() );
        dto.setDescription(entity.getDescription());
        dto.setTelephone1(entity.getTelephone1());
        dto.setTelephone2(entity.getTelephone2());
        dto.setEmail(entity.getEmail());
        dto.setAdresseLocalisation(entity.getAdresseLocalisation());

        return dto;
    }

    default List<GareDTO> toLiteDtos(List<Gare> entities) {
        if (entities == null || entities.stream().allMatch(o -> o == null)) {
            return null;
        }
        List<GareDTO> dtos = new ArrayList<GareDTO>();
        for (Gare entity : entities) {
            dtos.add(toLiteDto(entity));
        }
        return dtos;
    }

    @Mappings({
            @Mapping(source = "dto.id", target = "id"),
            @Mapping(source = "dto.designation", target = "designation"),
            @Mapping(source = "dto.description", target = "description"),

            @Mapping(source = "dto.telephone1", target = "telephone1"),
            @Mapping(source = "dto.telephone2", target = "telephone2"),
            @Mapping(source = "dto.email", target = "email"),
            @Mapping(source = "dto.adresseLocalisation", target = "adresseLocalisation"),

            @Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
            @Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
            @Mapping(source="dto.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),

            @Mapping(source="dto.updatedBy", target="updatedBy"),
            @Mapping(source="dto.createdBy", target="createdBy"),
            @Mapping(source="dto.deletedBy", target="deletedBy"),
            @Mapping(source="dto.isDeleted", target="isDeleted"),

            @Mapping(source="compagnieTransport", target="compagnieTransport")
    })
    Gare toEntity(GareDTO dto, CompagnieTransport compagnieTransport) throws ParseException;
}
