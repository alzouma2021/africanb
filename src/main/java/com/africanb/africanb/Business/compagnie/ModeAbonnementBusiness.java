package com.africanb.africanb.Business.compagnie;


import com.africanb.africanb.dao.entity.compagnie.CompagnieTransport;
import com.africanb.africanb.dao.entity.compagnie.ModeAbonnement;
import com.africanb.africanb.dao.entity.offreVoyage.*;
import com.africanb.africanb.dao.repository.Reference.ReferenceRepository;
import com.africanb.africanb.dao.repository.compagnie.AbonnementPeriodiqueRepository;
import com.africanb.africanb.dao.repository.compagnie.AbonnementPrelevementRepository;
import com.africanb.africanb.dao.repository.compagnie.CompagnieTransportRepository;
import com.africanb.africanb.helper.ExceptionUtils;
import com.africanb.africanb.helper.FunctionalError;
import com.africanb.africanb.helper.TechnicalError;
import com.africanb.africanb.helper.contrat.IBasicBusiness;
import com.africanb.africanb.helper.contrat.Request;
import com.africanb.africanb.helper.contrat.Response;
import com.africanb.africanb.helper.dto.compagnie.AbonnementPeriodiqueDTO;
import com.africanb.africanb.helper.dto.compagnie.AbonnementPrelevementDTO;
import com.africanb.africanb.helper.dto.compagnie.ModeAbonnementDTO;
import com.africanb.africanb.helper.dto.offreVoyage.*;
import com.africanb.africanb.helper.searchFunctions.Utilities;
import com.africanb.africanb.helper.validation.Validate;
import com.africanb.africanb.utils.Reference.Reference;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author ALZOUMA MOUSSA MAHAAMADOU
 */
@Log
@Component
public class ModeAbonnementBusiness implements IBasicBusiness<Request<ModeAbonnementDTO>, Response<ModeAbonnementDTO>> {


    private Response<ModeAbonnementDTO> response;

    @Autowired
    private FunctionalError functionalError;
    @Autowired
    private CompagnieTransportRepository compagnieTransportRepository;
    @Autowired
    private ReferenceRepository referenceRepository;
    @Autowired
    private AbonnementPeriodiqueBusiness abonnementPeriodiqueBusiness;
    @Autowired
    private AbonnementPrelevementBusiness abonnementPrelevementBusiness;
    @Autowired
    private TechnicalError technicalError;
    @Autowired
    private ExceptionUtils exceptionUtils;
    @Autowired
    private EntityManager em;

    private final SimpleDateFormat dateFormat;
    private final SimpleDateFormat dateTimeFormat;

    public ModeAbonnementBusiness() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    @Override
    public Response<ModeAbonnementDTO> create(Request<ModeAbonnementDTO> request, Locale locale) throws ParseException {
        Response<ModeAbonnementDTO> response = new Response<ModeAbonnementDTO>();
        List<ModeAbonnement> items = new ArrayList<ModeAbonnement>();
        List<ModeAbonnementDTO> itemsDto= new ArrayList<ModeAbonnementDTO>();
        if(request.getDatas() == null || request.getDatas().isEmpty()){
            response.setStatus(functionalError.DATA_NOT_EXIST("Liste vide",locale));
            response.setHasError(true);
            return response;
        }
        List<ModeAbonnementDTO>itemsDtos =  Collections.synchronizedList(new ArrayList<ModeAbonnementDTO>());
        for(ModeAbonnementDTO dto: request.getDatas() ) {
            if(dto!=null){
                Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
                fieldsToVerify.put("designation", dto.getDesignation());
                fieldsToVerify.put("compagnieTransportRaisonSociale", dto.getCompagnieTransportRaisonSociale());
                fieldsToVerify.put("periodiciteAbonnementDesignation", dto.getPeriodiciteAbonnementDesignation());
                if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                    response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                    response.setHasError(true);
                    return response;
                }
                if(itemsDtos.stream().anyMatch(a->a.getDesignation().equalsIgnoreCase(dto.getDesignation()))){
                    response.setStatus(functionalError.DATA_DUPLICATE("Tentative de duplication de la designation'" + dto.getDesignation() + "' pour les pays", locale));
                    response.setHasError(true);
                    return response;
                }
                itemsDtos.add(dto);
            }
        }
        for(ModeAbonnementDTO itemDto : itemsDtos){
            //Verify Compagnie transport
            CompagnieTransport existingCompagnieTransport = null;
            existingCompagnieTransport = compagnieTransportRepository.findByRaisonSociale(itemDto.getCompagnieTransportRaisonSociale(),false);
            if (existingCompagnieTransport == null) {
                response.setStatus(functionalError.DATA_EXIST("La compagnie de transport n'existe pas", locale));
                response.setHasError(true);
                return response;
            }
            //Verify periodiciteAbonnement
            Reference existingPeriodiciteAbonnement = null;
            existingPeriodiciteAbonnement = referenceRepository.findByDesignation(itemDto.getPeriodiciteAbonnementDesignation(),false);
            if (existingPeriodiciteAbonnement == null) {
                response.setStatus(functionalError.DATA_EXIST("La periodicite de l'abonnement n'existe pas", locale));
                response.setHasError(true);
                return response;
            }
            itemDto=Utilities.transformerLaClasseModeAbonnementEnClasseFilleCorrespondante(itemDto);
            log.info("_117 Affichage de l'objet itemDto :: "+ itemDto.toString());
            ModeAbonnementDTO entitySaved=null;
            entitySaved=saveModeAbonnementEnFonctionDeLaClasseFilleCorrespondante(itemDto,locale);
            log.info("_120 Affichage de l'objet entitySaved :: "+ entitySaved.toString());
            itemsDto.add(entitySaved);
        }
        if (CollectionUtils.isEmpty(itemsDto)) {
            response.setStatus(functionalError.SAVE_FAIL("Erreur de creation", locale));
            response.setHasError(true);
            return response;
        }
        response.setItems(itemsDto);
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));
        return response;
    }


    public Response<ModeAbonnementDTO> update(Request<ModeAbonnementDTO> request, Locale locale) throws ParseException {
        Response<ModeAbonnementDTO> response = new Response<ModeAbonnementDTO>();
        List<ModeAbonnement> items = new ArrayList<ModeAbonnement>();
        List<ModeAbonnementDTO> itemsDto= new ArrayList<ModeAbonnementDTO>();
        if(request.getDatas() == null || request.getDatas().isEmpty()){
            response.setStatus(functionalError.DATA_NOT_EXIST("Liste vide",locale));
            response.setHasError(true);
            return response;
        }
        List<ModeAbonnementDTO>itemsDtos =  Collections.synchronizedList(new ArrayList<ModeAbonnementDTO>());
        for(ModeAbonnementDTO dto: request.getDatas() ) {
            if(dto!=null){
                Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
                fieldsToVerify.put("id", dto.getId());
                fieldsToVerify.put("compagnieTransportRaisonSociale", dto.getCompagnieTransportRaisonSociale());
                fieldsToVerify.put("periodiciteAbonnementDesignation", dto.getPeriodiciteAbonnementDesignation());
                if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                    response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                    response.setHasError(true);
                    return response;
                }
                if(itemsDtos.stream().anyMatch(a->a.getId().equals(dto.getId()))){
                    response.setStatus(functionalError.DATA_DUPLICATE("Tentative de duplication de l'identifiant'" + dto.getId() + "' pour les abonnements", locale));
                    response.setHasError(true);
                    return response;
                }
                itemsDtos.add(dto);
            }
        }
        for(ModeAbonnementDTO itemDto : itemsDtos){
            //Verify Compagnie transport
            CompagnieTransport existingCompagnieTransport = null;
            existingCompagnieTransport = compagnieTransportRepository.findByRaisonSociale(itemDto.getCompagnieTransportRaisonSociale(),false);
            if (existingCompagnieTransport == null) {
                response.setStatus(functionalError.DATA_EXIST("La compagnie de transport n'existe pas", locale));
                response.setHasError(true);
                return response;
            }
            //Verify periodiciteAbonnement
            Reference existingPeriodiciteAbonnement = null;
            existingPeriodiciteAbonnement = referenceRepository.findByDesignation(itemDto.getPeriodiciteAbonnementDesignation(),false);
            if (existingPeriodiciteAbonnement == null) {
                response.setStatus(functionalError.DATA_EXIST("La periodicite de l'abonnement n'existe pas", locale));
                response.setHasError(true);
                return response;
            }
            itemDto=Utilities.transformerLaClasseModeAbonnementEnClasseFilleCorrespondante(itemDto);
            log.info("_117 Affichage de l'objet itemDto :: "+ itemDto.toString());
            ModeAbonnementDTO entitySaved=null;
            entitySaved=updateModeAbonnementEnFonctionDeLaClasseFilleCorrespondante(itemDto,locale);
            log.info("_120 Affichage de l'objet entitySaved :: "+ entitySaved.toString());
            itemsDto.add(entitySaved);
        }
        if (CollectionUtils.isEmpty(itemsDto)) {
            response.setStatus(functionalError.SAVE_FAIL("Erreur de creation", locale));
            response.setHasError(true);
            return response;
        }
        response.setItems(itemsDto);
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));
        return response;
    }

    @Override
    public Response<ModeAbonnementDTO> delete(Request<ModeAbonnementDTO> request, Locale locale) {

/*        log.info("----begin delete agence-----");

        Response<AgenceDto> response = new Response<AgenceDto>();
        List<Agence> items = new ArrayList<Agence>();

        //Verification
        if(request.getDatas().isEmpty() || request.getDatas() == null){
            response.setStatus(functionalError.DATA_NOT_EXIST("Liste de données est vide ",locale));
            response.setHasError(true);
            return response;
        }

        //Verification des champs obligatoires
        for(AgenceDto dto : request.getDatas()) {

            Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
            fieldsToVerify.put("id", dto.getId());

            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }

        }

        //Parcourir la liste
        for(AgenceDto dto : request.getDatas()){

            // Verification du parametre identifiant
            Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
            fieldsToVerify.put("id", dto.getId());

            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }

            // Verify if Functionality  exist
            Agence existingEntity = null;

            existingEntity = agenceRepository.findOne(dto.getId(), false);

            if (existingEntity == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("L'agence ayant  id -> " + dto.getId() + ",n'existe pas", locale));
                response.setHasError(true);
                return response;
            }

            log.info("_413 Verification d'existence de l'objet"+existingEntity.toString()); //TODO A effacer

            //Suppression logique
            existingEntity.setIsDeleted(true);
            existingEntity.setDeletedAt(Utilities.getCurrentDate());
            existingEntity.setDeletedBy(request.user);// a modifier

            items.add(existingEntity);

        }

        //Verificatioon de la liste de données recues
        if(items == null  || items.isEmpty()){
            response.setStatus(functionalError.DATA_NOT_EXIST("Liste de données est vide ",locale));
            response.setHasError(true);
            return response;
        }

        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));

        return response;*/
        return null;
    }

    @Override
    public Response<ModeAbonnementDTO> forceDelete(Request<ModeAbonnementDTO> request, Locale locale) {
        return null ;
    }

    @Override
    public Response<ModeAbonnementDTO> getAll(Locale locale) throws ParseException {
       return null;
    }

    @Override
    public Response<ModeAbonnementDTO> getByCriteria(Request<ModeAbonnementDTO> request, Locale locale) {
       /*
        log.info("----begin get agence-----");

        Response<AgenceDto> response = new Response<AgenceDto>();

        if (Utilities.blank(request.getData().getOrderField())) {
            request.getData().setOrderField("");
        }
        if (Utilities.blank(request.getData().getOrderDirection())) {
            request.getData().setOrderDirection("asc");
        }

        List<Agence> items = agenceRepository.getByCriteria(request, em, locale);

        if (Utilities.isEmpty(items)) {
            response.setStatus(functionalError.DATA_EMPTY("Aucune agence ne correspond aux critères de recherche definis", locale));
            response.setHasError(false);
            return response;
        }

        List<AgenceDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                                 ? AgenceTransformer.INSTANCE.toLiteDtos(items)
                                 : AgenceTransformer.INSTANCE.toDtos(items);


        response.setItems(itemsDto);
        response.setCount(agenceRepository.count(request, em, locale));
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));

        log.info("----end get agence-----");

        return response;
    */
        return null;
    }

  /*  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Response<ValeurCaracteristiqueOffreVoyageDTO> getAllValeurCaracteristiqueOffreVoyageByOffreVoyageDesignation(Request<OffreVoyageDTO> request, Locale locale) throws ParseException {
        Response<ValeurCaracteristiqueOffreVoyageDTO> response = new Response<ValeurCaracteristiqueOffreVoyageDTO>();
        List<ValeurCaracteristiqueOffreVoyageDTO> itemsDto= new ArrayList<ValeurCaracteristiqueOffreVoyageDTO>();
        List<ValeurCaracteristiqueOffreVoyage> items = new ArrayList<ValeurCaracteristiqueOffreVoyage>();
        if (request.getData() == null ) {
            response.setStatus(functionalError.DATA_NOT_EXIST("Aucune donnée definie", locale));
            response.setHasError(true);
            return response;
        }
        Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
        fieldsToVerify.put("offreVoyageDesignation", request.getData().getDesignation());
        if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
            response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            response.setHasError(true);
            return response;
        }
        String offreVoyageDesignation=request.getData().getDesignation();
        OffreVoyage existingOffreVoyage = null;
        existingOffreVoyage = offreVoyageRepository.findByDesignation(offreVoyageDesignation,false);
        if (existingOffreVoyage == null) {
            response.setStatus(functionalError.DATA_EXIST("Offre de voyage n'existe pas", locale));
            response.setHasError(true);
            return response;
        }
        items= (List<ValeurCaracteristiqueOffreVoyage>) valeurCaracteristiqueOffreVoyageRepository.findAllByOffreVoyageDesignation(offreVoyageDesignation,false);
        if (CollectionUtils.isEmpty(items)) {
            response.setStatus(functionalError.DATA_NOT_EXIST("La compagnie ne dipose d'aucune offre de voyage", locale));
            response.setHasError(true);
            return response;
        }
        itemsDto=convertValeurCaracteristiqueOffreVoyagesFilleToValeurCaracteristiqueOffreVoyageDTO(items);
        response.setItems(itemsDto);
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));
        log.info("----end update l'offre de voyage-----");
        return response;
    }*/


    public ModeAbonnementDTO saveModeAbonnementEnFonctionDeLaClasseFilleCorrespondante(ModeAbonnementDTO modeAbonnementDTO,Locale locale) throws ParseException {

        if(modeAbonnementDTO instanceof AbonnementPeriodiqueDTO){

            Request<AbonnementPeriodiqueDTO> subRequest = new Request<AbonnementPeriodiqueDTO>();
            List<AbonnementPeriodiqueDTO> itemsDTO = Collections.synchronizedList(new ArrayList<AbonnementPeriodiqueDTO>());
            AbonnementPeriodiqueDTO abonnementPeriodiqueDTO = (AbonnementPeriodiqueDTO) modeAbonnementDTO;
            //Conversion
            itemsDTO.add(abonnementPeriodiqueDTO);
            subRequest.setDatas( itemsDTO);
            Response<AbonnementPeriodiqueDTO> subResponse = abonnementPeriodiqueBusiness.create(subRequest,locale);
            if (subResponse.isHasError()) {
                response.setStatus(subResponse.getStatus());
                response.setHasError(Boolean.TRUE);
                return new ModeAbonnementDTO();
            }
            ModeAbonnementDTO rtn = new ModeAbonnementDTO();
            rtn.setId( subResponse.getItems().get(0).getId());
            rtn.setDesignation( subResponse.getItems().get(0).getDesignation());
            rtn.setDescription( subResponse.getItems().get(0).getDescription());

            rtn.setRedevance( subResponse.getItems().get(0).getRedevance());
            rtn.setRedevancePublicite( subResponse.getItems().get(0).getRedevancePublicite());
            rtn.setDateDebutAbonnement(subResponse.getItems().get(0).getDateDebutAbonnement());
            rtn.setDateFinAbonnement(subResponse.getItems().get(0).getDateFinAbonnement());
            rtn.setPeriodiciteAbonnementDesignation(subResponse.getItems().get(0).getPeriodiciteAbonnementDesignation());
            rtn.setCompagnieTransportRaisonSociale(subResponse.getItems().get(0).getCompagnieTransportRaisonSociale());

            rtn.setDeletedAt( subResponse.getItems().get(0).getDeletedAt());
            rtn.setUpdatedAt( subResponse.getItems().get(0).getUpdatedAt());
            rtn.setCreatedAt( subResponse.getItems().get(0).getCreatedAt());
            rtn.setCreatedBy( subResponse.getItems().get(0).getCreatedBy());
            rtn.setIsDeleted( subResponse.getItems().get(0).getIsDeleted());
            rtn.setDeletedBy( subResponse.getItems().get(0).getDeletedBy());
            rtn.setUpdatedBy( subResponse.getItems().get(0).getUpdatedBy());
            rtn.setIsDeletedParam( subResponse.getItems().get(0).getIsDeletedParam());
            rtn.setUpdatedAtParam( subResponse.getItems().get(0).getUpdatedAtParam());
            rtn.setCreatedAtParam( subResponse.getItems().get(0).getCreatedAtParam());
            rtn.setCreatedByParam( subResponse.getItems().get(0).getCreatedByParam());
            rtn.setUpdatedByParam(subResponse.getItems().get(0).getUpdatedByParam());
            rtn.setOrderDirection(subResponse.getItems().get(0).getOrderDirection());

            return rtn;
        }
        else if(modeAbonnementDTO instanceof AbonnementPrelevementDTO){
            Request<AbonnementPrelevementDTO> subRequest = new Request<AbonnementPrelevementDTO>();
            List<AbonnementPrelevementDTO> itemsDTO = Collections.synchronizedList(new ArrayList<AbonnementPrelevementDTO>());
            AbonnementPrelevementDTO abonnementPrelevementDTO = (AbonnementPrelevementDTO) modeAbonnementDTO;
            //Conversion
            itemsDTO.add(abonnementPrelevementDTO);
            subRequest.setDatas( itemsDTO);
            Response<AbonnementPrelevementDTO> subResponse = abonnementPrelevementBusiness.create(subRequest,locale);
            if (subResponse.isHasError()) {
                response.setStatus(subResponse.getStatus());
                response.setHasError(Boolean.TRUE);
                return new AbonnementPrelevementDTO();
            }
            ModeAbonnementDTO rtn = new ModeAbonnementDTO();
            rtn.setId( subResponse.getItems().get(0).getId());
            rtn.setDesignation( subResponse.getItems().get(0).getDesignation());
            rtn.setDescription( subResponse.getItems().get(0).getDescription());

           // rtn.setRedevance( subResponse.getItems().get(0).getRedevance());
            rtn.setTaux( subResponse.getItems().get(0).getTaux());
            rtn.setDateDebutAbonnement(subResponse.getItems().get(0).getDateDebutAbonnement());
            rtn.setDateFinAbonnement(subResponse.getItems().get(0).getDateFinAbonnement());
            rtn.setPeriodiciteAbonnementDesignation(subResponse.getItems().get(0).getPeriodiciteAbonnementDesignation());
            rtn.setCompagnieTransportRaisonSociale(subResponse.getItems().get(0).getCompagnieTransportRaisonSociale());

            rtn.setDeletedAt( subResponse.getItems().get(0).getDeletedAt());
            rtn.setUpdatedAt( subResponse.getItems().get(0).getUpdatedAt());
            rtn.setCreatedAt( subResponse.getItems().get(0).getCreatedAt());
            rtn.setCreatedBy( subResponse.getItems().get(0).getCreatedBy());
            rtn.setIsDeleted( subResponse.getItems().get(0).getIsDeleted());
            rtn.setDeletedBy( subResponse.getItems().get(0).getDeletedBy());
            rtn.setUpdatedBy( subResponse.getItems().get(0).getUpdatedBy());
            rtn.setIsDeletedParam( subResponse.getItems().get(0).getIsDeletedParam());
            rtn.setUpdatedAtParam( subResponse.getItems().get(0).getUpdatedAtParam());
            rtn.setCreatedAtParam( subResponse.getItems().get(0).getCreatedAtParam());
            rtn.setCreatedByParam( subResponse.getItems().get(0).getCreatedByParam());
            rtn.setUpdatedByParam(subResponse.getItems().get(0).getUpdatedByParam());
            rtn.setOrderDirection(subResponse.getItems().get(0).getOrderDirection());

            return rtn;
        }
        else{

        }
        return new ModeAbonnementDTO();
    }


    public ModeAbonnementDTO updateModeAbonnementEnFonctionDeLaClasseFilleCorrespondante(ModeAbonnementDTO modeAbonnementDTO,Locale locale) throws ParseException {

        if(modeAbonnementDTO instanceof AbonnementPeriodiqueDTO){

            Request<AbonnementPeriodiqueDTO> subRequest = new Request<AbonnementPeriodiqueDTO>();
            List<AbonnementPeriodiqueDTO> itemsDTO = Collections.synchronizedList(new ArrayList<AbonnementPeriodiqueDTO>());
            AbonnementPeriodiqueDTO abonnementPeriodiqueDTO = (AbonnementPeriodiqueDTO) modeAbonnementDTO;
            //Conversion
            itemsDTO.add(abonnementPeriodiqueDTO);
            subRequest.setDatas( itemsDTO);
            Response<AbonnementPeriodiqueDTO> subResponse = abonnementPeriodiqueBusiness.update(subRequest,locale);
            if (subResponse.isHasError()) {
                response.setStatus(subResponse.getStatus());
                response.setHasError(Boolean.TRUE);
                return new ModeAbonnementDTO();
            }
            ModeAbonnementDTO rtn = new ModeAbonnementDTO();
            rtn.setId( subResponse.getItems().get(0).getId());
            rtn.setDesignation( subResponse.getItems().get(0).getDesignation());
            rtn.setDescription( subResponse.getItems().get(0).getDescription());

            rtn.setRedevance( subResponse.getItems().get(0).getRedevance());
            rtn.setRedevancePublicite( subResponse.getItems().get(0).getRedevancePublicite());
            rtn.setDateDebutAbonnement(subResponse.getItems().get(0).getDateDebutAbonnement());
            rtn.setDateFinAbonnement(subResponse.getItems().get(0).getDateFinAbonnement());
            rtn.setPeriodiciteAbonnementDesignation(subResponse.getItems().get(0).getPeriodiciteAbonnementDesignation());
            rtn.setCompagnieTransportRaisonSociale(subResponse.getItems().get(0).getCompagnieTransportRaisonSociale());

            rtn.setDeletedAt( subResponse.getItems().get(0).getDeletedAt());
            rtn.setUpdatedAt( subResponse.getItems().get(0).getUpdatedAt());
            rtn.setCreatedAt( subResponse.getItems().get(0).getCreatedAt());
            rtn.setCreatedBy( subResponse.getItems().get(0).getCreatedBy());
            rtn.setIsDeleted( subResponse.getItems().get(0).getIsDeleted());
            rtn.setDeletedBy( subResponse.getItems().get(0).getDeletedBy());
            rtn.setUpdatedBy( subResponse.getItems().get(0).getUpdatedBy());
            rtn.setIsDeletedParam( subResponse.getItems().get(0).getIsDeletedParam());
            rtn.setUpdatedAtParam( subResponse.getItems().get(0).getUpdatedAtParam());
            rtn.setCreatedAtParam( subResponse.getItems().get(0).getCreatedAtParam());
            rtn.setCreatedByParam( subResponse.getItems().get(0).getCreatedByParam());
            rtn.setUpdatedByParam(subResponse.getItems().get(0).getUpdatedByParam());
            rtn.setOrderDirection(subResponse.getItems().get(0).getOrderDirection());

            return rtn;
        }
        else if(modeAbonnementDTO instanceof AbonnementPrelevementDTO){
            Request<AbonnementPrelevementDTO> subRequest = new Request<AbonnementPrelevementDTO>();
            List<AbonnementPrelevementDTO> itemsDTO = Collections.synchronizedList(new ArrayList<AbonnementPrelevementDTO>());
            AbonnementPrelevementDTO abonnementPrelevementDTO = (AbonnementPrelevementDTO) modeAbonnementDTO;
            //Conversion
            itemsDTO.add(abonnementPrelevementDTO);
            subRequest.setDatas( itemsDTO);
            Response<AbonnementPrelevementDTO> subResponse = abonnementPrelevementBusiness.update(subRequest,locale);
            if (subResponse.isHasError()) {
                response.setStatus(subResponse.getStatus());
                response.setHasError(Boolean.TRUE);
                return new AbonnementPrelevementDTO();
            }
            ModeAbonnementDTO rtn = new ModeAbonnementDTO();
            rtn.setId( subResponse.getItems().get(0).getId());
            rtn.setDesignation( subResponse.getItems().get(0).getDesignation());
            rtn.setDescription( subResponse.getItems().get(0).getDescription());

            // rtn.setRedevance( subResponse.getItems().get(0).getRedevance());
            rtn.setTaux( subResponse.getItems().get(0).getTaux());
            rtn.setDateDebutAbonnement(subResponse.getItems().get(0).getDateDebutAbonnement());
            rtn.setDateFinAbonnement(subResponse.getItems().get(0).getDateFinAbonnement());
            rtn.setPeriodiciteAbonnementDesignation(subResponse.getItems().get(0).getPeriodiciteAbonnementDesignation());
            rtn.setCompagnieTransportRaisonSociale(subResponse.getItems().get(0).getCompagnieTransportRaisonSociale());

            rtn.setDeletedAt( subResponse.getItems().get(0).getDeletedAt());
            rtn.setUpdatedAt( subResponse.getItems().get(0).getUpdatedAt());
            rtn.setCreatedAt( subResponse.getItems().get(0).getCreatedAt());
            rtn.setCreatedBy( subResponse.getItems().get(0).getCreatedBy());
            rtn.setIsDeleted( subResponse.getItems().get(0).getIsDeleted());
            rtn.setDeletedBy( subResponse.getItems().get(0).getDeletedBy());
            rtn.setUpdatedBy( subResponse.getItems().get(0).getUpdatedBy());
            rtn.setIsDeletedParam( subResponse.getItems().get(0).getIsDeletedParam());
            rtn.setUpdatedAtParam( subResponse.getItems().get(0).getUpdatedAtParam());
            rtn.setCreatedAtParam( subResponse.getItems().get(0).getCreatedAtParam());
            rtn.setCreatedByParam( subResponse.getItems().get(0).getCreatedByParam());
            rtn.setUpdatedByParam(subResponse.getItems().get(0).getUpdatedByParam());
            rtn.setOrderDirection(subResponse.getItems().get(0).getOrderDirection());

            return rtn;
        }
        else{

        }
        return new ModeAbonnementDTO();
    }
}
