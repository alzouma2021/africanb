package com.africanb.africanb.helper.dto.transformer.compagnie;


import com.africanb.africanb.dao.entity.compagnie.FamilleStatusUtil;
import com.africanb.africanb.dao.entity.compagnie.Pays;
import com.africanb.africanb.helper.contrat.FullTransformerQualifier;
import com.africanb.africanb.helper.dto.compagnie.FamilleStatusUtilDTO;
import com.africanb.africanb.helper.dto.compagnie.PaysDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface FamilleStatusUtilTransformer {

    FamilleStatusUtilTransformer INSTANCE = Mappers.getMapper(FamilleStatusUtilTransformer.class);

    @FullTransformerQualifier
    @Mappings({
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.designation", target = "designation"),
            @Mapping(source = "entity.description", target = "description"),
            @Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
            @Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
            @Mapping(source="entity.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),
            @Mapping(source="entity.updatedBy", target="updatedBy"),
            @Mapping(source="entity.createdBy", target="createdBy"),
            @Mapping(source="entity.deletedBy", target="deletedBy"),
            @Mapping(source="entity.isDeleted", target="isDeleted"),
    })
    FamilleStatusUtilDTO toDto(FamilleStatusUtil entity);

    @IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<FamilleStatusUtilDTO> toDtos(List<FamilleStatusUtil> entities) throws ParseException;

    public default FamilleStatusUtilDTO toLiteDto(FamilleStatusUtil entity) {
        if (entity == null) {
            return null;
        }
        FamilleStatusUtilDTO dto = new FamilleStatusUtilDTO();
        dto.setId( entity.getId() );
        dto.setDesignation( entity.getDesignation() );
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public default List<FamilleStatusUtilDTO> toLiteDtos(List<FamilleStatusUtil> entities) {
        if (entities == null || entities.stream().allMatch(o -> o == null)) {
            return null;
        }
        List<FamilleStatusUtilDTO> dtos = new ArrayList<FamilleStatusUtilDTO>();
        for (FamilleStatusUtil entity : entities) {
            dtos.add(toLiteDto(entity));
        }
        return dtos;
    }

    @InheritInverseConfiguration
    FamilleStatusUtil toEntity(FamilleStatusUtilDTO dto);
}
