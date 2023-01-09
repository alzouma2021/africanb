package com.africanb.africanb.Business.offreVoyage;


import com.africanb.africanb.dao.entity.compagnie.CompagnieTransport;
import com.africanb.africanb.dao.entity.compagnie.Ville;
import com.africanb.africanb.dao.entity.offreVoyage.OffreVoyage;
import com.africanb.africanb.dao.entity.offreVoyage.PrixOffreVoyage;
import com.africanb.africanb.dao.repository.Reference.ReferenceRepository;
import com.africanb.africanb.dao.repository.compagnie.CompagnieTransportRepository;
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
import com.africanb.africanb.helper.dto.offreVoyage.JourSemaineDTO;
import com.africanb.africanb.helper.dto.offreVoyage.OffreVoyageDTO;
import com.africanb.africanb.helper.dto.offreVoyage.PrixOffreVoyageDTO;
import com.africanb.africanb.helper.dto.offreVoyage.VilleEscaleDTO;
import com.africanb.africanb.helper.dto.transformer.offrreVoyage.OffreVoyageTransformer;
import com.africanb.africanb.helper.dto.transformer.offrreVoyage.PrixOffreVoyageTransformer;
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
public class OffreVoyageBusiness implements IBasicBusiness<Request<OffreVoyageDTO>, Response<OffreVoyageDTO>> {


    private Response<OffreVoyageDTO> response;
    @Autowired
    private ReferenceRepository referenceRepository;
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CompagnieTransportRepository compagnieTransportRepository;
    @Autowired
    private OffreVoyageRepository offreVoyageRepository;
    @Autowired
    private PrixOffreVoyageBusiness prixOffreVoyageBusiness;
    @Autowired
    private VilleEscaleBusiness villeEscaleBusiness;
    @Autowired
    private JourSemaineBusiness jourSemaineBusinesse;
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

    public OffreVoyageBusiness() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    @Override
    public Response<OffreVoyageDTO> create(Request<OffreVoyageDTO> request, Locale locale) throws ParseException {
        Response<OffreVoyageDTO> response = new Response<OffreVoyageDTO>();
        List<OffreVoyage> items = new ArrayList<OffreVoyage>();
        if(request.getDatas() == null || request.getDatas().isEmpty()){
            response.setStatus(functionalError.DATA_NOT_EXIST("Liste vide",locale));
            response.setHasError(true);
            return response;
        }
        List<OffreVoyageDTO> itemsDtos =  Collections.synchronizedList(new ArrayList<OffreVoyageDTO>());
        for(OffreVoyageDTO dto: request.getDatas() ) {
            Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
            fieldsToVerify.put("designation", dto.getDesignation());
            fieldsToVerify.put("villeDepartDesignation", dto.getVilleDepartDesignation());
            fieldsToVerify.put("villeDestinationDesignation", dto.getVilleDestinationDesignation());
            fieldsToVerify.put("typeOffreVoyageDesignation", dto.getTypeOffreVoyageDesignation());
            fieldsToVerify.put("compagnieRaisonSociale", dto.getCompagnieTransportRaisonSociale());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }
            if(itemsDtos.stream().anyMatch(a->a.getDesignation().equalsIgnoreCase(dto.getDesignation()))){
                response.setStatus(functionalError.DATA_DUPLICATE("Tentative de duplication de la designation'" + dto.getDesignation(), locale));
                response.setHasError(true);
                return response;
            }
            itemsDtos.add(dto);
        }
        for(OffreVoyageDTO itemDto : itemsDtos){
            OffreVoyage existingOffreVoyage = null;
            existingOffreVoyage= offreVoyageRepository.findByDesignation(itemDto.getDesignation(),false);
            if (existingOffreVoyage!=null) {
                response.setStatus(functionalError.DATA_EXIST("L'offre de voyage ayant  pour identifiant -> " + itemDto.getDesignation() +",existe", locale));
                response.setHasError(true);
                return response;
            }
            Ville existingVilleDepart = null;
            existingVilleDepart= villeRepository.findByDesignation(itemDto.getVilleDepartDesignation(),false);
            if (existingVilleDepart == null) {
                response.setStatus(functionalError.DATA_EXIST("La ville de départ indiquée n'existe pas", locale));
                response.setHasError(true);
                return response;
            }
            Ville existingVilleDestination = null;
            existingVilleDestination= villeRepository.findByDesignation(itemDto.getVilleDestinationDesignation(),false);
            if (existingVilleDestination == null) {
                response.setStatus(functionalError.DATA_EXIST("La ville de destination indiquée n'existe pas", locale));
                response.setHasError(true);
                return response;
            }
            CompagnieTransport existingCompagnieTransport = null;
            existingCompagnieTransport= compagnieTransportRepository.findByRaisonSociale(itemDto.getCompagnieTransportRaisonSociale(),false);
            if (existingCompagnieTransport == null) {
                response.setStatus(functionalError.DATA_EXIST("La compagnie de transport n'existe pas", locale));
                response.setHasError(true);
                return response;
            }
            if (existingCompagnieTransport.getIsValidate()==null || !existingCompagnieTransport.getIsValidate()) {
                response.setStatus(functionalError.DATA_EXIST("Vous ne pouvez pas créer des offres de voyage.Car,votre compagnie n'est encore validée", locale));
                response.setHasError(true);
                return response;
            }
            Reference existingTypeOffreVoyage = null;
            existingTypeOffreVoyage= referenceRepository.findByDesignation(itemDto.getTypeOffreVoyageDesignation(),false);
            if (existingTypeOffreVoyage == null) {
                response.setStatus(functionalError.DATA_EXIST("Le type offre de voyage ayant  pour identifiant -> " + itemDto.getDesignation() +", n'existe pas", locale));
                response.setHasError(true);
                return response;
            }
            OffreVoyage entityToSave = OffreVoyageTransformer.INSTANCE.toEntity(itemDto,existingVilleDepart,existingVilleDestination,existingTypeOffreVoyage,existingCompagnieTransport);
            entityToSave.setIsDeleted(false);
            entityToSave.setCreatedAt(Utilities.getCurrentDate());

            OffreVoyage entitySaved=null;
            entitySaved=offreVoyageRepository.save(entityToSave);
            if(entitySaved==null){
                response.setStatus(functionalError.SAVE_FAIL("Erreur de creation", locale));
                response.setHasError(true);
                return response;
            }
            //Check if prixOffreVoyageExist
            if(!CollectionUtils.isEmpty(itemDto.getPrixOffreVoyageDTOList())){
                    Request<PrixOffreVoyageDTO> subRequest = new Request<PrixOffreVoyageDTO>();
                    subRequest.setDatas((List<PrixOffreVoyageDTO>) itemDto.getPrixOffreVoyageDTOList());
                    for(PrixOffreVoyageDTO prixOffreVoyageDTO: itemDto.getPrixOffreVoyageDTOList()){
                        prixOffreVoyageDTO.setOffreVoyageDesignation(entitySaved.getDesignation());
                    }
                    //subRequest.setUser(request.getUser());
                    Response<PrixOffreVoyageDTO> subResponse = prixOffreVoyageBusiness.create(subRequest, locale);
                    if (subResponse.isHasError()) {
                        response.setStatus(subResponse.getStatus());
                        response.setHasError(Boolean.TRUE);
                        return response;
                    }
            }
            //Check if jourSemaine
            if(!CollectionUtils.isEmpty(itemDto.getJourSemaineDTOList())){
                Request<JourSemaineDTO> subRequestJourSemaine = new Request<JourSemaineDTO>();
                subRequestJourSemaine.setDatas((List<JourSemaineDTO>) itemDto.getJourSemaineDTOList());
                for(JourSemaineDTO jourSemaineDTO: itemDto.getJourSemaineDTOList()){
                    jourSemaineDTO.setOffreVoyageDesignation(entitySaved.getDesignation());
                }
                //subRequest.setUser(request.getUser());
                Response<JourSemaineDTO> subResponse = jourSemaineBusinesse.create(subRequestJourSemaine, locale);
                if (subResponse.isHasError()) {
                    response.setStatus(subResponse.getStatus());
                    response.setHasError(Boolean.TRUE);
                    return response;
                }
            }
            //Check if villeEscaleList
            if(!CollectionUtils.isEmpty(itemDto.getVilleEscaleDTOList())){
                Request<VilleEscaleDTO> subRequestVilleEscale = new Request<VilleEscaleDTO>();
                subRequestVilleEscale.setDatas((List<VilleEscaleDTO>) itemDto.getVilleEscaleDTOList());
                //subRequest.setUser(request.getUser());
                //Initialisation de l'offre de voyage
                for(VilleEscaleDTO villeEscaleDTO: itemDto.getVilleEscaleDTOList()){
                    villeEscaleDTO.setOffreVoyageDesignation(entitySaved.getDesignation());
                }
                Response<VilleEscaleDTO> subResponse = villeEscaleBusiness.create(subRequestVilleEscale, locale);
                if (subResponse.isHasError()) {
                    response.setStatus(subResponse.getStatus());
                    response.setHasError(Boolean.TRUE);
                    return response;
                }
            }
            //entityToSave.setCreatedBy(request.user); // à modifier
            items.add(entitySaved);
        }
        if (CollectionUtils.isEmpty(items)) {
            response.setStatus(functionalError.SAVE_FAIL("Erreur de creation", locale));
            response.setHasError(true);
            return response;
        }
        List<OffreVoyageDTO> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                                    ? OffreVoyageTransformer.INSTANCE.toLiteDtos(items)
                                    : OffreVoyageTransformer.INSTANCE.toDtos(items);
        response.setItems(itemsDto);
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));
        return response;
    }

    @Override
    public Response<OffreVoyageDTO> update(Request<OffreVoyageDTO> request, Locale locale) throws ParseException {
        Response<OffreVoyageDTO> response = new Response<OffreVoyageDTO>();
        List<OffreVoyage> items = new ArrayList<OffreVoyage>();
        if(request.getDatas() == null  || request.getDatas().isEmpty()){
            response.setStatus(functionalError.DATA_NOT_EXIST("Liste vide",locale));
            response.setHasError(true);
            return response;
        }
        List<OffreVoyageDTO>itemsDtos =  Collections.synchronizedList(new ArrayList<OffreVoyageDTO>());
        for(OffreVoyageDTO dto: request.getDatas() ) {
            Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
            fieldsToVerify.put("id", dto.getId());
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
        for(OffreVoyageDTO dto: itemsDtos) {
            OffreVoyage entityToSave = offreVoyageRepository.findOne(dto.getId(), false);
            if (entityToSave == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("L'offre de voyage ayant l'identifiant suivant -> " + dto.getId() +", n'existe pas", locale));
                response.setHasError(true);
                return response;
            }
            if (entityToSave.getIsActif() == true) {
                response.setStatus(functionalError.DATA_NOT_EXIST("Vous ne pouvez pas modifier l'offre de voyage car elle est active.", locale));
                response.setHasError(true);
                return response;
            }
            //TODO Verifier si l'offre de voyage a été réservée
            if (Utilities.isNotBlank(dto.getDesignation()) && !dto.getDesignation().equals(entityToSave.getDesignation())) {
                OffreVoyage existingOffreVoyage = offreVoyageRepository.findByDesignation(dto.getDesignation(), false);
                if (existingOffreVoyage != null && !existingOffreVoyage.getId().equals(entityToSave.getId())) {
                    response.setStatus(functionalError.DATA_EXIST("OffreVoyage -> " + dto.getDesignation(), locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setDesignation(dto.getDesignation());
            }
            //Ville depart
            String villeDepartDesignation=entityToSave.getVilleDepart()!=null&&entityToSave.getVilleDepart().getDesignation()!=null
                                       ?entityToSave.getVilleDepart().getDesignation()
                                       :null;
            if(villeDepartDesignation==null){
                response.setStatus(functionalError.DATA_NOT_EXIST("L'offre de voyage ne possede aucune ville de départ", locale));
                response.setHasError(true);
                return response;
            }
            Ville existingVilleDepart = villeRepository.findByDesignation(villeDepartDesignation,false);
            if (existingVilleDepart == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("La ville de l'offre de voyage -> " + dto.getId() +", n'existe pas", locale));
                response.setHasError(true);
                return response;
            }
            if (Utilities.isNotBlank(dto.getVilleDepartDesignation()) && !dto.getVilleDepartDesignation().equals(existingVilleDepart.getDesignation())) {
                Ville villeDepartToSave = villeRepository.findByDesignation(dto.getVilleDepartDesignation(), false);
                if (villeDepartToSave == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("La nouvelle ville de départ  l'offre de voyage n'existe pas-> " + dto.getId() +", n'existe pas", locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setVilleDepart(villeDepartToSave);
            }
            //Ville destnation
            String villeDestinationDesignation=entityToSave.getVilleDestination()!=null&&entityToSave.getVilleDestination().getDesignation()!=null
                    ?entityToSave.getVilleDestination().getDesignation()
                    :null;
            if(villeDestinationDesignation==null){
                response.setStatus(functionalError.DATA_NOT_EXIST("L'offre de voyage ne possede aucune ville de destination", locale));
                response.setHasError(true);
                return response;
            }
            Ville existingVilleDestination = villeRepository.findByDesignation(villeDestinationDesignation,false);
            if (existingVilleDestination == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("La ville de l'offre de voyage -> " + dto.getId() +", n'existe pas", locale));
                response.setHasError(true);
                return response;
            }
            if (Utilities.isNotBlank(dto.getVilleDestinationDesignation()) && !dto.getVilleDestinationDesignation().equals(existingVilleDestination.getDesignation())) {
                Ville villeDestinationToSave = villeRepository.findByDesignation(dto.getVilleDestinationDesignation(), false);
                if (villeDestinationToSave == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("La nouvelle ville de destination  l'offre de voyage n'existe pas-> " + dto.getId() +", n'existe pas", locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setVilleDestination(villeDestinationToSave);
            }
            //TypeOffreVoyage
            String typeOffreVoyageDesignation=entityToSave.getTypeOffreVoyage()!=null&&entityToSave.getTypeOffreVoyage().getDesignation()!=null
                    ?entityToSave.getTypeOffreVoyage().getDesignation()
                    :null;
            if(typeOffreVoyageDesignation==null){
                response.setStatus(functionalError.DATA_NOT_EXIST("Le prix offre de voyage ne relie à aucun type offre de voyage", locale));
                response.setHasError(true);
                return response;
            }
            Reference existingTypeOffrreVoyage = referenceRepository.findByDesignation(typeOffreVoyageDesignation,false);
            if (existingTypeOffrreVoyage == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("Le type offre de voyage de l'offre de voyage -> " + dto.getId() +", n'existe pas", locale));
                response.setHasError(true);
                return response;
            }
            if (Utilities.isNotBlank(dto.getTypeOffreVoyageDesignation()) && !dto.getTypeOffreVoyageDesignation().equals(existingTypeOffrreVoyage.getDesignation())) {
                Reference typeOffreVoyageToSave = referenceRepository.findByDesignation(dto.getTypeOffreVoyageDesignation(), false);
                if (typeOffreVoyageToSave == null) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("Le nouveau type offre de voyage de l'offre de voyage n'existe pas-> " + dto.getId() +", n'existe pas", locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setTypeOffreVoyage(typeOffreVoyageToSave);
            }
            //Autres
            if(Utilities.isNotBlank(dto.getDescription()) && !dto.getDesignation().equals(entityToSave.getDescription())){
                entityToSave.setDescription(dto.getDescription());
            }
            if(dto.getIsActif()!=null && !dto.getIsActif().equals(entityToSave.getIsActif())){
                entityToSave.setIsActif(dto.getIsActif());
            }
            entityToSave.setUpdatedAt(Utilities.getCurrentDate());
            //entityToSave.setUpdatedBy(request.user);
            OffreVoyage entityupdated=null;
            entityupdated=offreVoyageRepository.save(entityToSave);
            if(entityupdated==null){
                response.setStatus(functionalError.SAVE_FAIL("Erreur de Modification", locale));
                response.setHasError(true);
                return response;
            }
            //Check if prixOffreVoyageExist
            if(!CollectionUtils.isEmpty(dto.getPrixOffreVoyageDTOList())){
                Request<PrixOffreVoyageDTO> subRequest = new Request<PrixOffreVoyageDTO>();
                subRequest.setDatas( dto.getPrixOffreVoyageDTOList());
                for(PrixOffreVoyageDTO prixOffreVoyageDTO: dto.getPrixOffreVoyageDTOList()){
                    prixOffreVoyageDTO.setOffreVoyageDesignation(entityupdated.getDesignation());
                }
                //subRequest.setUser(request.getUser());
                Response<PrixOffreVoyageDTO> subResponse = prixOffreVoyageBusiness.update(subRequest, locale);
                if (subResponse.isHasError()) {
                    response.setStatus(subResponse.getStatus());
                    response.setHasError(Boolean.TRUE);
                    return response;
                }
            }
            //Check if jourSemaine
            if(!CollectionUtils.isEmpty(dto.getJourSemaineDTOList())){
                Request<JourSemaineDTO> subRequestJourSemaine = new Request<JourSemaineDTO>();
                subRequestJourSemaine.setDatas( dto.getJourSemaineDTOList());
                for(JourSemaineDTO jourSemaineDTO: dto.getJourSemaineDTOList()){
                    jourSemaineDTO.setOffreVoyageDesignation(entityupdated.getDesignation());
                }
                //subRequest.setUser(request.getUser());
                Response<JourSemaineDTO> subResponse = jourSemaineBusinesse.update(subRequestJourSemaine, locale);
                if (subResponse.isHasError()) {
                    response.setStatus(subResponse.getStatus());
                    response.setHasError(Boolean.TRUE);
                    return response;
                }
            }
            //Check if villeEscaleList
            if(!CollectionUtils.isEmpty(dto.getVilleEscaleDTOList())){
                Request<VilleEscaleDTO> subRequestVilleEscale = new Request<VilleEscaleDTO>();
                subRequestVilleEscale.setDatas( dto.getVilleEscaleDTOList());
                //subRequest.setUser(request.getUser());
                //Initialisation de l'offre de voyage
                for(VilleEscaleDTO villeEscaleDTO: dto.getVilleEscaleDTOList()){
                    villeEscaleDTO.setOffreVoyageDesignation(entityupdated.getDesignation());
                }
                Response<VilleEscaleDTO> subResponse = villeEscaleBusiness.update(subRequestVilleEscale, locale);
                if (subResponse.isHasError()) {
                    response.setStatus(subResponse.getStatus());
                    response.setHasError(Boolean.TRUE);
                    return response;
                }
            }
            items.add(entityupdated);
        }
        List<OffreVoyageDTO> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                                ? OffreVoyageTransformer.INSTANCE.toLiteDtos(items)
                                : OffreVoyageTransformer.INSTANCE.toDtos(items);

        response.setItems(itemsDto);
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));
        log.info("----end update l'offre de voyage-----");
        return response;
    }

    @Override
    public Response<OffreVoyageDTO> delete(Request<OffreVoyageDTO> request, Locale locale) {

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
    public Response<OffreVoyageDTO> forceDelete(Request<OffreVoyageDTO> request, Locale locale) {
        return null ;
    }

    @Override
    public Response<OffreVoyageDTO> getAll(Locale locale) throws ParseException {
       return null;
    }

    @Override
    public Response<OffreVoyageDTO> getByCriteria(Request<OffreVoyageDTO> request, Locale locale) {
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

}
