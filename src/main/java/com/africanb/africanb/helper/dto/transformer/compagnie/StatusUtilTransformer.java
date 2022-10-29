package com.africanb.africanb.helper.dto.transformer.compagnie;


import com.africanb.africanb.dao.entity.compagnie.FamilleStatusUtil;
import com.africanb.africanb.dao.entity.compagnie.Pays;
import com.africanb.africanb.dao.entity.compagnie.StatusUtil;
import com.africanb.africanb.dao.entity.compagnie.Ville;
import com.africanb.africanb.helper.contrat.FullTransformerQualifier;
import com.africanb.africanb.helper.dto.compagnie.StatusUtilDTO;
import com.africanb.africanb.helper.dto.compagnie.VilleDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface StatusUtilTransformer {

    StatusUtilTransformer INSTANCE = Mappers.getMapper(StatusUtilTransformer.class);

    @FullTransformerQualifier
    @Mappings({
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.designation", target = "designation"),
            @Mapping(source = "entity.description", target = "description"),

            @Mapping(source = "entity.familleStatusUtil.id", target = "familleStatusUtilId"),
            @Mapping(source = "entity.familleStatusUtil.designation", target = "familleStatusUtilDesignation"),

            @Mapping(source="entity.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
            @Mapping(source="entity.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
            @Mapping(source="entity.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),
            @Mapping(source="entity.updatedBy", target="updatedBy"),
            @Mapping(source="entity.createdBy", target="createdBy"),
            @Mapping(source="entity.deletedBy", target="deletedBy")
    })
    StatusUtilDTO toDto(StatusUtil entity);

    @IterableMapping(qualifiedBy = {FullTransformerQualifier.class})
    List<StatusUtilDTO> toDtos(List<StatusUtil> entities) throws ParseException;

    public default StatusUtilDTO toLiteDto(StatusUtil entity) {
        if (entity == null) {
            return null;
        }
        StatusUtilDTO dto = new StatusUtilDTO();
        dto.setId( entity.getId() );
        dto.setDesignation( entity.getDesignation() );
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public default List<StatusUtilDTO> toLiteDtos(List<StatusUtil> entities) {
        if (entities == null || entities.stream().allMatch(o -> o == null)) {
            return null;
        }
        List<StatusUtilDTO> dtos = new ArrayList<StatusUtilDTO>();
        for (StatusUtil entity : entities) {
            dtos.add(toLiteDto(entity));
        }
        return dtos;
    }

    @Mappings({
            @Mapping(source = "dto.id", target = "id"),
            @Mapping(source = "dto.designation", target = "designation"),

            @Mapping(source="dto.updatedAt", dateFormat="dd/MM/yyyy",target="updatedAt"),
            @Mapping(source="dto.createdAt", dateFormat="dd/MM/yyyy",target="createdAt"),
            @Mapping(source="dto.deletedAt", dateFormat="dd/MM/yyyy",target="deletedAt"),
            @Mapping(source="dto.updatedBy", target="updatedBy"),
            @Mapping(source="dto.createdBy", target="createdBy"),
            @Mapping(source="dto.deletedBy", target="deletedBy"),

            @Mapping(source = "familleStatusUtil", target = "familleStatusUtil"),
    })
    StatusUtil toEntity(StatusUtilDTO dto, FamilleStatusUtil familleStatusUtil);
}
