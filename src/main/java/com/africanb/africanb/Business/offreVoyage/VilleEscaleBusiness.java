package com.africanb.africanb.Business.offreVoyage;


import com.africanb.africanb.dao.entity.compagnie.CompagnieTransport;
import com.africanb.africanb.dao.entity.compagnie.StatusUtil;
import com.africanb.africanb.dao.entity.compagnie.StatusUtilCompagnieTransport;
import com.africanb.africanb.dao.entity.compagnie.Ville;
import com.africanb.africanb.dao.entity.offreVoyage.OffreVoyage;
import com.africanb.africanb.dao.entity.offreVoyage.VilleEscale;
import com.africanb.africanb.dao.repository.compagnie.CompagnieTransportRepository;
import com.africanb.africanb.dao.repository.compagnie.StatusUtilCompagnieTransportRepository;
import com.africanb.africanb.dao.repository.compagnie.StatusUtilRepository;
import com.africanb.africanb.dao.repository.compagnie.VilleRepository;
import com.africanb.africanb.dao.repository.offreVoyage.OffreVoyageRepository;
import com.africanb.africanb.dao.repository.offreVoyage.VilleEscaleRepository;
import com.africanb.africanb.helper.ExceptionUtils;
import com.africanb.africanb.helper.FunctionalError;
import com.africanb.africanb.helper.TechnicalError;
import com.africanb.africanb.helper.contrat.IBasicBusiness;
import com.africanb.africanb.helper.contrat.Request;
import com.africanb.africanb.helper.contrat.Response;
import com.africanb.africanb.helper.dto.compagnie.StatusUtilCompagnieTransportDTO;
import com.africanb.africanb.helper.dto.offreVoyage.VilleEscaleDTO;
import com.africanb.africanb.helper.dto.transformer.compagnie.StatusUtilCompagnieTransportTransformer;
import com.africanb.africanb.helper.dto.transformer.offrreVoyage.VilleEscaleTransformer;
import com.africanb.africanb.helper.searchFunctions.Utilities;
import com.africanb.africanb.helper.validation.Validate;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Log
@Component
public class VilleEscaleBusiness implements IBasicBusiness<Request<VilleEscaleDTO>, Response<VilleEscaleDTO>> {

    private Response<VilleEscaleDTO> response;
    @Autowired
    private OffreVoyageRepository offreVoyageRepository;
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private VilleEscaleRepository villeEscaleRepository;
    @Autowired
    private FunctionalError functionalError;
    @Autowired
    private TechnicalError technicalError;
    @Autowired
    private ExceptionUtils exceptionUtils;

    @Autowired
    private EntityManager em;

    private final SimpleDateFormat dateFormat;
    private final SimpleDateFormat dateTimeFormat;

    public VilleEscaleBusiness() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }
    
    @Override
    public Response<VilleEscaleDTO> create(Request<VilleEscaleDTO> request, Locale locale) throws ParseException {
        Response<VilleEscaleDTO> response = new Response<VilleEscaleDTO>();
        List<VilleEscale> items = new ArrayList<VilleEscale>();
        if(request.getDatas().isEmpty() || request.getDatas() == null){
            response.setStatus(functionalError.DATA_NOT_EXIST("Liste vide ",locale));
            response.setHasError(true);
            return response;
        }
        List<VilleEscaleDTO> itemsDtos =  Collections.synchronizedList(new ArrayList<VilleEscaleDTO>());
        for(VilleEscaleDTO dto: request.getDatas() ) {
            Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
            fieldsToVerify.put("villeId", dto.getVilleId());
            fieldsToVerify.put("order", dto.getOrder());
            fieldsToVerify.put("offreVoyageId", dto.getOffreVoyageId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }
            if(itemsDtos.stream().anyMatch(a->a.getOffreVoyageId().equals(dto.getOffreVoyageId()))){
                response.setStatus(functionalError.DATA_DUPLICATE("Tentative de duplication de l'offre de voyage'" + dto.getOffreVoyageId() , locale));
                response.setHasError(true);
                return response;
            }
            itemsDtos.add(dto);
        }
        for(VilleEscaleDTO dto : itemsDtos){
            VilleEscale existingEntity = null;
            existingEntity = villeEscaleRepository.findByOffreVoyageAndVille(dto.getOffreVoyageId(),dto.getVilleId(),false);
            if (existingEntity != null) {
                response.setStatus(functionalError.DATA_EXIST("VilleEscale ", locale));
                response.setHasError(true);
                return response;
            }
            OffreVoyage existingOffreVoyage = null;
            if (Utilities.isValidID(dto.getOffreVoyageId())) {
                existingOffreVoyage = offreVoyageRepository.findOne(dto.getOffreVoyageId(), false);
                if (existingOffreVoyage == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("offreVoyage offreVoyageID -> " + dto.getOffreVoyageId(), locale));
                    response.setHasError(true);
                    return response;
                }
            }
            Ville existingVille = null;
            if (Utilities.isValidID(dto.getVilleId())) {
                existingVille = villeRepository.findOne(dto.getVilleId(), false);
                if (existingVille == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("ville villeID -> " + dto.getVilleId(), locale));
                    response.setHasError(true);
                    return response;
                }
            }
            VilleEscale entityToSave = VilleEscaleTransformer
                                        .INSTANCE.toEntity(dto, existingOffreVoyage, existingVille);
            entityToSave.setIsDeleted(false);
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            //entityToSave.setCreatedBy(request.user);
            items.add(entityToSave);
        }
        if(CollectionUtils.isEmpty(items)){
            response.setStatus(functionalError.DATA_NOT_EXIST("Erreur creation ",locale));
            response.setHasError(true);
            return response;
        }
        List<VilleEscale> itemsSaved = null;
        itemsSaved = villeEscaleRepository.saveAll((Iterable<VilleEscale>) items);
        if (CollectionUtils.isEmpty(itemsSaved)) {
            response.setStatus(functionalError.SAVE_FAIL("Erreur creation", locale));
            response.setHasError(true);
            return response;
        }
        List<VilleEscaleDTO> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                                ? VilleEscaleTransformer.INSTANCE.toLiteDtos(itemsSaved)
                                : VilleEscaleTransformer.INSTANCE.toDtos(itemsSaved);
        response.setItems(itemsDto);
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));
        return response;
    }

    @Override
    public Response<VilleEscaleDTO> update(Request<VilleEscaleDTO> request, Locale locale) throws ParseException {
        Response<VilleEscaleDTO> response = new Response<VilleEscaleDTO>();
        List<VilleEscale> items = new ArrayList<VilleEscale>();
        if(request.getDatas() == null  || CollectionUtils.isEmpty(request.getDatas())){
            response.setStatus(functionalError.DATA_NOT_EXIST("Liste vide ",locale));
            response.setHasError(true);
            return response;
        }
        List<VilleEscaleDTO> itemsDtos =  Collections.synchronizedList(new ArrayList<VilleEscaleDTO>());
        for(VilleEscaleDTO dto: request.getDatas() ) {
            Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }
            if(itemsDtos.stream().anyMatch(a->a.getId().equals(dto.getId()))){
                response.setStatus(functionalError.DATA_DUPLICATE("Tentative de duplication offreVoyage '" + dto.getOffreVoyageId() , locale));
                response.setHasError(true);
                return response;
            }
            itemsDtos.add(dto);
        }
        for(VilleEscaleDTO dto : itemsDtos){
            VilleEscale entityToSave = null;
            entityToSave = villeEscaleRepository.findOne(dto.getId(), false);
            if (entityToSave == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("villeEscale id -> " + dto.getId(), locale));
                response.setHasError(true);
                return response;
            }
            OffreVoyage existingOffreVoyage = null;
            if (Utilities.isValidID(dto.getOffreVoyageId()) && !entityToSave.getOffreVoyage().getId().equals(dto.getOffreVoyageId())) {
                existingOffreVoyage = offreVoyageRepository.findOne(dto.getOffreVoyageId(), false);
                if (existingOffreVoyage == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("OffreVoyageId -> " + dto.getOffreVoyageId(), locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setOffreVoyage(existingOffreVoyage);
            }
            Ville existingVille = null;
            if (Utilities.isValidID(dto.getVilleId()) && !entityToSave.getVille().getId().equals(dto.getVilleId())) {
                existingVille = villeRepository.findOne(dto.getVilleId(), false);
                if (existingVille == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("Ville villeId -> " + dto.getVilleId(), locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setVille(existingVille);
            }
            entityToSave.setUpdatedAt(Utilities.getCurrentDate());
            //entityToSave.setUpdatedBy(request.user);
            items.add(entityToSave);
        }
        if (CollectionUtils.isEmpty(items)) {
            response.setStatus(functionalError.DATA_NOT_EXIST("Erreur de modification ",locale));
            response.setHasError(true);
        }
        List<VilleEscale> itemsSaved = null;
        itemsSaved = villeEscaleRepository.saveAll((Iterable<VilleEscale>) items);
        if (CollectionUtils.isEmpty(itemsSaved)) {
                response.setStatus(functionalError.SAVE_FAIL("VilleEscale", locale));
                response.setHasError(true);
                return response;
        }
        //Transformation
        List<VilleEscaleDTO> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                                    ? VilleEscaleTransformer.INSTANCE.toLiteDtos(itemsSaved)
                                    : VilleEscaleTransformer.INSTANCE.toDtos(itemsSaved);
        response.setItems(itemsDto);
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));
        return response;
    }

    @Override
    public Response<VilleEscaleDTO> delete(Request<VilleEscaleDTO> request, Locale locale) {
        Response<VilleEscaleDTO> response = new Response<VilleEscaleDTO>();
        List<VilleEscale> items = new ArrayList<VilleEscale>();
        if(request.getDatas() == null  || CollectionUtils.isEmpty(request.getDatas())){
            response.setStatus(functionalError.DATA_NOT_EXIST("Liste vide ",locale));
            response.setHasError(true);
            return response;
        }
        for(VilleEscaleDTO dto: request.getDatas() ) {
            Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }
        }
        for(VilleEscaleDTO dto : request.getDatas()){
            VilleEscale existingEntity = null;
            existingEntity = villeEscaleRepository.findOne(dto.getId(), false);
            if (existingEntity == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("VillEsclae id -> " + dto.getId(), locale));
                response.setHasError(true);
                return response;
            }
            existingEntity.setIsDeleted(true);
            existingEntity.setDeletedAt(Utilities.getCurrentDate());
            //existingEntity.setDeletedBy(request.user);
            items.add(existingEntity);
        }
        if (CollectionUtils.isEmpty(items)) {
            response.setHasError(false);
            response.setStatus(functionalError.SUCCESS("", locale));
        }
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));
        return response;
    }

    @Override
    public Response<VilleEscaleDTO> forceDelete(Request<VilleEscaleDTO> request, Locale locale) {
        return null;
    }

    @Override
    public Response<VilleEscaleDTO> getAll(Locale locale) throws ParseException {
        return null;
    }

    @Override
    public Response<VilleEscaleDTO> getByCriteria(Request<VilleEscaleDTO> request, Locale locale) {

    /*    log.info("----begin get HistoriqueDemande-----");

        Response<StatusUtilCompagnieTransportDTO> response = new Response<StatusUtilCompagnieTransportDTO>();

        //verification si le parametre d'ordre à été fourni, sinon nous mettons le paramètre à vide
        if (Utilities.blank(request.getData().getOrderField())) {
            request.getData().setOrderField("");
        }

        //verification si le parametre direction à été fourni, sinon nous mettons le paramètre ascendant( du plus ancien au plus ressent)
        if (Utilities.blank(request.getData().getOrderDirection())) {
            request.getData().setOrderDirection("asc");
        }

        //recuperation des entités en base
        List<HistoriqueDemande> items = statusUtilCompagnieTransportRepository.getByCriteria(request, em, locale);

        if (Utilities.isEmpty(items)) {
            response.setStatus(functionalError.DATA_EMPTY("HistoriqueDemande", locale));
            response.setHasError(false);
            return response;
        }

        //Transformation
        List<HistoriqueDemandeDto> itemsDto = HistoriqueDemandeTransformer.INSTANCE.toDtos(items);

        //Envoie de la reponse
        response.setItems(itemsDto);
        response.setCount(statusUtilCompagnieTransportRepository.count(request, em, locale));
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));

        log.info("----end get HistoriqueDemande-----");

        return response;
        */
        return null;
    }
}
