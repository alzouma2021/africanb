
/**
 * @Author ALZOUMA MOUSSA MAHAMADOU
 */

package com.africanb.africanb.Business.compagnie;

import com.africanb.africanb.dao.entity.compagnie.CompagnieTransport;
import com.africanb.africanb.dao.entity.compagnie.StatusUtil;
import com.africanb.africanb.dao.entity.compagnie.Ville;
import com.africanb.africanb.dao.repository.compagnieRepository.CompagnieTransportRepository;
import com.africanb.africanb.dao.repository.compagnieRepository.StatusUtilCompagnieTransportRepository;
import com.africanb.africanb.dao.repository.compagnieRepository.StatusUtilRepository;
import com.africanb.africanb.dao.repository.compagnieRepository.VilleRepository;
import com.africanb.africanb.helper.ExceptionUtils;
import com.africanb.africanb.helper.FunctionalError;
import com.africanb.africanb.helper.TechnicalError;
import com.africanb.africanb.helper.contrat.IBasicBusiness;
import com.africanb.africanb.helper.contrat.Request;
import com.africanb.africanb.helper.contrat.Response;
import com.africanb.africanb.helper.dto.compagnie.CompagnieTransportDTO;
import com.africanb.africanb.helper.dto.compagnie.StatusUtilCompagnieTransportDTO;
import com.africanb.africanb.helper.dto.transformer.compagnie.CompagnieTransportTransformer;
import com.africanb.africanb.helper.searchFunctions.Utilities;
import com.africanb.africanb.helper.validation.Validate;
import com.africanb.africanb.utils.Constants.StatusUtilConstants;
import com.africanb.africanb.utils.emailService.BodiesOfEmail;
import com.africanb.africanb.utils.emailService.EmailDTO;
import com.africanb.africanb.utils.emailService.EmailServiceBusiness;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Log
@Component
public class CompagnieTransportBusiness implements IBasicBusiness<Request<CompagnieTransportDTO>, Response<CompagnieTransportDTO>> {

    private Response<CompagnieTransportDTO> response;
    @Autowired
    private StatusUtilRepository statusUtilRepository;
    @Autowired
    private CompagnieTransportRepository compagnieTransportRepository;
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private StatusUtilCompagnieTransportRepository statusUtilCompagnieTransportRepository;
    @Autowired
    private FunctionalError functionalError;
    @Autowired
    private TechnicalError technicalError;
    @Autowired
    private ExceptionUtils exceptionUtils;
    @Autowired
    private EntityManager em;
    @Autowired
    private EmailServiceBusiness emailServiceBusiness;

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat dateTimeFormat;

    @Autowired
    private StatusUtilCompagnieTransportBusiness statusUtilCompagnieTransportBusiness;

    public CompagnieTransportBusiness() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    @Override
    public Response<CompagnieTransportDTO> create(Request<CompagnieTransportDTO> request, Locale locale) throws ParseException {
        Response<CompagnieTransportDTO> response = new Response<CompagnieTransportDTO>();
        List<CompagnieTransport> items = new ArrayList<CompagnieTransport>();
        if(request.getDatas() == null  || CollectionUtils.isEmpty(request.getDatas())){
            response.setStatus(functionalError.DATA_NOT_EXIST("Liste vide ",locale));
            response.setHasError(true);
            return response;
        }
        List<CompagnieTransportDTO> itemsDtos =  Collections.synchronizedList(new ArrayList<CompagnieTransportDTO>());
        for (CompagnieTransportDTO dto : request.getDatas()) {
            Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
            fieldsToVerify.put("designation", dto.getDesignation());
            fieldsToVerify.put("raisonSociale", dto.getRaisonSociale());
            fieldsToVerify.put("telephone", dto.getTelephone());
            fieldsToVerify.put("sigle", dto.getSigle());
            fieldsToVerify.put("email", dto.getEmail());
            //fieldsToVerify.put("ville", dto.getVilleId());
            fieldsToVerify.put("villeDesignation", dto.getVilleDesignation());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }
            if(itemsDtos.stream().anyMatch(a->a.getId().equals(dto.getId()))){
                response.setStatus(functionalError.DATA_DUPLICATE("Tentative de duplication de compagnie '" + dto.getDesignation() , locale));
                response.setHasError(true);
                return response;
            }
            itemsDtos.add(dto);
        }
        for(CompagnieTransportDTO dto: itemsDtos) {
            CompagnieTransport existingEntity=null;
            existingEntity=compagnieTransportRepository.findByDesignation(dto.getVilleDesignation(),false);
            if (existingEntity != null) {
                response.setStatus(functionalError.DATA_EXIST("CompagnieTransport  -> " + dto.getVilleDesignation(), locale));
                response.setHasError(true);
                return response;
            }
            Ville existingVille=null;
            existingVille=villeRepository.findByDesignation(dto.getVilleDesignation(),false);
            if (existingVille == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("Ville  -> " + dto.getVilleDesignation(), locale));
                response.setHasError(true);
                return response;
            }
            StatusUtil existingStatusUtilActual=null;
            existingStatusUtilActual=statusUtilRepository.findByDesignation(StatusUtilConstants.COMPAGNIE_TRANSPORT_ENCOURS_TRAITEMENT,false);
            if (existingStatusUtilActual == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("StatusUtilActual  -> " + StatusUtilConstants.COMPAGNIE_TRANSPORT_ENCOURS_TRAITEMENT, locale));
                response.setHasError(true);
                return response;
            }
            dto.setStatusUtilActualId(existingStatusUtilActual.getId());
            dto.setStatusUtilActualDesignation(existingStatusUtilActual.getDesignation());
            CompagnieTransport entityToSave = CompagnieTransportTransformer.INSTANCE.toEntity(dto,existingVille,existingStatusUtilActual);
            entityToSave.setIsDeleted(false);
            entityToSave.setIsActif(false);
            entityToSave.setCreatedAt(Utilities.getCurrentDate());
            //entityToSave.setCreatedBy((long) request.getUser());
            CompagnieTransport entitySaved = compagnieTransportRepository.save(entityToSave);
            if(entitySaved==null){
                response.setStatus(functionalError.SAVE_FAIL("Erreur creation",locale));
                response.setHasError(true);
                return response;
            }
            //
            List<StatusUtilCompagnieTransportDTO> itemsDatas =  Collections.synchronizedList(new ArrayList<StatusUtilCompagnieTransportDTO>());
            StatusUtilCompagnieTransportDTO statusUtilCompagnieTransportDTO= new StatusUtilCompagnieTransportDTO();
            statusUtilCompagnieTransportDTO.setStatusUtilId(existingStatusUtilActual.getId());
            statusUtilCompagnieTransportDTO.setCompagnieTransportId(entitySaved.getId());
            itemsDatas.add(statusUtilCompagnieTransportDTO);
            Request<StatusUtilCompagnieTransportDTO> subRequest = new Request<StatusUtilCompagnieTransportDTO>();
            subRequest.setDatas(itemsDatas);
            //subRequest.setUser(request.getUser());
            Response<StatusUtilCompagnieTransportDTO> subResponse = statusUtilCompagnieTransportBusiness.create(subRequest, locale);
            if (subResponse.isHasError()) {
                response.setStatus(subResponse.getStatus());
                response.setHasError(Boolean.TRUE);
                return response;
            }
            //Send mail de création
            Runnable runnable = () -> {
                BodiesOfEmail bodiesOfEmail= new BodiesOfEmail();
                EmailDTO emailDTO = new EmailDTO();
                Request<EmailDTO> subRequestEmail = new Request<EmailDTO>();

                emailDTO.setSubject("Creation de compagnie de transport");
                emailDTO.setMessage(bodiesOfEmail.bodyHtmlMailCreateCompagny());
                emailDTO.setToAddress(entitySaved.getEmail());
                subRequestEmail.setData(emailDTO);

                emailServiceBusiness.sendSimpleEmail(subRequestEmail,locale);
            };
            runnable.run();
            items.add(entitySaved);
        }
        if(CollectionUtils.isEmpty(items)){
            response.setStatus(functionalError.DATA_NOT_EXIST("Erreur de creation ",locale));
            response.setHasError(true);
            return response;
        }
        List<CompagnieTransportDTO> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                ? CompagnieTransportTransformer.INSTANCE.toLiteDtos(items)
                : CompagnieTransportTransformer.INSTANCE.toDtos(items);
        response.setItems(itemsDto);
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));
        return response;
    }

    @Override
    public Response<CompagnieTransportDTO> update(Request<CompagnieTransportDTO> request, Locale locale) throws ParseException {
        Response<CompagnieTransportDTO> response = new Response<CompagnieTransportDTO>();
        List<CompagnieTransport> items = new ArrayList<CompagnieTransport>();
        if(request.getDatas() == null  || CollectionUtils.isEmpty(request.getDatas())){
            response.setStatus(functionalError.DATA_NOT_EXIST("Liste vide ",locale));
            response.setHasError(true);
            return response;
        }
        List<CompagnieTransportDTO> itemsDtos =  Collections.synchronizedList(new ArrayList<CompagnieTransportDTO>());
        for (CompagnieTransportDTO dto : request.getDatas()) {
            Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }
            if(itemsDtos.stream().anyMatch(a->a.getId().equals(dto.getId()))){
                response.setStatus(functionalError.DATA_DUPLICATE("Tentative de duplication de compagnie '" + dto.getDesignation() , locale));
                response.setHasError(true);
                return response;
            }
            itemsDtos.add(dto);
        }
        for (CompagnieTransportDTO dto :itemsDtos) {
            CompagnieTransport entityToSave = null;
            entityToSave = compagnieTransportRepository.findOne(dto.getId(), false);
            if (entityToSave == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("CompagnieTransport CompagnieTransportID -> " + dto.getId(), locale));
                response.setHasError(true);
                return response;
            }
            if (Utilities.isNotBlank(dto.getDesignation()) && !dto.getDesignation().equals(entityToSave.getDesignation())) {
                CompagnieTransport existingEntity = compagnieTransportRepository.findByDesignation(dto.getDesignation(), false);
                if (existingEntity != null && !existingEntity.getId().equals(entityToSave.getId())) {
                    response.setStatus(functionalError.DATA_EXIST("CompagnieTransport -> " + dto.getId(), locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setDesignation(dto.getDesignation());
            }
            if (Utilities.isValidID(dto.getVilleId()) && !dto.getVilleId().equals(entityToSave.getVille().getId())) {
                Ville existingVille=villeRepository.findOne(dto.getVilleId(),false);
                if (existingVille ==null ) {
                    response.setStatus(functionalError.DATA_NOT_EXIST("Ville  -> " + dto.getVilleId(), locale));
                    response.setHasError(true);
                    return response;
                }
                entityToSave.setVille(existingVille);
            }
            if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
                entityToSave.setUpdatedBy(dto.getUpdatedBy());
            }
            if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
                entityToSave.setCreatedBy(dto.getCreatedBy());
            }
            if (Utilities.isNotBlank(dto.getDeletedAt())) {
                entityToSave.setDeletedAt(dateFormat.parse(dto.getDeletedAt()));
            }
            if (dto.getDeletedBy() != null && dto.getDeletedBy() > 0) {
                entityToSave.setDeletedBy(dto.getDeletedBy());
            }
            if(Utilities.isNotBlank(dto.getTelephone()) && !dto.getTelephone().equals(entityToSave.getTelephone())){
                entityToSave.setTelephone(dto.getTelephone());
            }
            if(Utilities.isNotBlank(dto.getRaisonSociale()) && !dto.getRaisonSociale().equals(entityToSave.getRaisonSociale())){
                entityToSave.setRaisonSociale(dto.getRaisonSociale());
            }
            if(Utilities.isNotBlank(dto.getEmail()) && !dto.getEmail().equals(entityToSave.getEmail())){
                entityToSave.setEmail(dto.getEmail());
            }
            if(Utilities.isNotBlank(dto.getSigle()) && !dto.getSigle().equals(entityToSave.getSigle())){
                entityToSave.setSigle(dto.getSigle());
            }
            entityToSave.setUpdatedAt(Utilities.getCurrentDate());
            items.add(entityToSave);
        }
        if (CollectionUtils.isEmpty(items)) {
            response.setStatus(functionalError.DATA_NOT_EXIST("Erreur de modification ",locale));
            response.setHasError(true);
        }
        List<CompagnieTransport> itemsSaved = null;
        itemsSaved = compagnieTransportRepository.saveAll((Iterable<CompagnieTransport>) items);
        if (CollectionUtils.isEmpty(itemsSaved)) {
            response.setStatus(functionalError.SAVE_FAIL("CompagnieTransport", locale));
            response.setHasError(true);
            return response;
        }
        List<CompagnieTransportDTO> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                ? CompagnieTransportTransformer.INSTANCE.toLiteDtos(itemsSaved)
                : CompagnieTransportTransformer.INSTANCE.toDtos(itemsSaved);
        response.setItems(itemsDto);
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));
        return response;
    }

    @Override
    public Response<CompagnieTransportDTO> delete(Request<CompagnieTransportDTO> request, Locale locale) {
        /*log.info("----begin delete Profil-----");

        Response<ProfilDto> response = new Response<ProfilDto>();
        List<Profil> items = new ArrayList<Profil>();

        for (ProfilDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }
            // Verifier si la profil existe
            Profil existingEntity = null;
            existingEntity = statusUtilRepository.findOne(dto.getId(), false);
            if (existingEntity == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("profil -> " + dto.getId(), locale));
                response.setHasError(true);
                return response;
            }
            // -----------------------------------------------------------------------
            // ----------- CHECK IF DATA IS USED
            // -----------------------------------------------------------------------
            // user
            List<User> listOfUser = userRepository.findByProfilId(existingEntity.getId(), false);
            if (listOfUser != null && !listOfUser.isEmpty()) {
                response.setStatus(functionalError.DATA_NOT_DELETABLE("(" + listOfUser.size() + ")", locale));
                response.setHasError(true);
                return response;
            }
            // profilFonctionnalite
            List<ProfilFonctionnalite> listOfProfilFonctionnalite = profilFonctionnaliteRepository
                    .findByProfilId(existingEntity.getId(), false);
            if (listOfProfilFonctionnalite != null && !listOfProfilFonctionnalite.isEmpty()) {
                response.setStatus(
                        functionalError.DATA_NOT_DELETABLE("(" + listOfProfilFonctionnalite.size() + ")", locale));
                response.setHasError(true);
                return response;
            }
            existingEntity.setIsDeleted(true);
            existingEntity.setDeletedAt(Utilities.getCurrentDate());
            existingEntity.setDeletedBy((long) request.getUser());
            items.add(existingEntity);
        }
        if (!items.isEmpty()) {
            // supprimer les donnees en base
            statusUtilRepository.saveAll((Iterable<Profil>) items);

            response.setHasError(false);
            response.setStatus(functionalError.SUCCESS("", locale));
        }
        log.info("----end delete Profil-----");
        return response;*/
        return null;
    }

    @Override
    public Response<CompagnieTransportDTO> forceDelete(Request<CompagnieTransportDTO> request, Locale locale) {
       /* log.info("----begin forceDelete Profil-----");

        Response<ProfilDto> response = new Response<ProfilDto>();
        List<Profil> items = new ArrayList<Profil>();
        for (ProfilDto dto : request.getDatas()) {
            // Definir les parametres obligatoires
            Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
            fieldsToVerify.put("id", dto.getId());
            if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
                response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
                response.setHasError(true);
                return response;
            }
            // Verifier si la profil existe
            Profil existingEntity = null;
            existingEntity = statusUtilRepository.findOne(dto.getId(), false);
            if (existingEntity == null) {
                response.setStatus(functionalError.DATA_NOT_EXIST("profil -> " + dto.getId(), locale));
                response.setHasError(true);
                return response;
            }
            // -----------------------------------------------------------------------
            // ----------- CHECK IF DATA IS USED
            // -----------------------------------------------------------------------

            // user
            List<User> listOfUser = userRepository.findByProfilId(existingEntity.getId(), false);
            if (listOfUser != null && !listOfUser.isEmpty()) {
                Request<UserDto> deleteRequest = new Request<UserDto>();
                deleteRequest.setDatas(UserTransformer.INSTANCE.toDtos(listOfUser));
                deleteRequest.setUser(request.getUser());
                Response<UserDto> deleteResponse = userBusiness.delete(deleteRequest, locale);
                if (deleteResponse.isHasError()) {
                    response.setStatus(deleteResponse.getStatus());
                    response.setHasError(true);
                    return response;
                }
            }
            // profilFonctionnalite
            List<ProfilFonctionnalite> listOfProfilFonctionnalite = profilFonctionnaliteRepository
                    .findByProfilId(existingEntity.getId(), false);
            if (listOfProfilFonctionnalite != null && !listOfProfilFonctionnalite.isEmpty()) {
                Request<ProfilFonctionnaliteDto> deleteRequest = new Request<ProfilFonctionnaliteDto>();
                deleteRequest.setDatas(ProfilFonctionnaliteTransformer.INSTANCE.toDtos(listOfProfilFonctionnalite));
                deleteRequest.setUser(request.getUser());
                Response<ProfilFonctionnaliteDto> deleteResponse = profilFonctionnaliteBusiness.delete(deleteRequest,
                        locale);
                if (deleteResponse.isHasError()) {
                    response.setStatus(deleteResponse.getStatus());
                    response.setHasError(true);
                    return response;
                }
            }
            existingEntity.setIsDeleted(true);
            existingEntity.setDeletedAt(Utilities.getCurrentDate());
            existingEntity.setDeletedBy((long) request.getUser());
            items.add(existingEntity);
        }

        if (!items.isEmpty()) {
            // supprimer les donnees en base
            statusUtilRepository.saveAll((Iterable<Profil>) items);

            response.setHasError(false);
            response.setStatus(functionalError.SUCCESS("", locale));
        }

        log.info("----end forceDelete Profil-----");
        return response;*/
        return null;
    }

    @Override
    public Response<CompagnieTransportDTO> getAll(Locale locale) throws ParseException {
        return null;
    }

    @Override
    public Response<CompagnieTransportDTO> getByCriteria(Request<CompagnieTransportDTO> request, Locale locale) {
       /* log.info("----begin get Profil-----");

        Response<ProfilDto> response = new Response<ProfilDto>();

        if (Utilities.blank(request.getData().getOrderField())) {
            request.getData().setOrderField("");
        }
        if (Utilities.blank(request.getData().getOrderDirection())) {
            request.getData().setOrderDirection("asc");
        }

        List<Profil> items = statusUtilRepository.getByCriteria(request, em, locale);

        if (Utilities.isEmpty(items)) {
            response.setStatus(functionalError.DATA_EMPTY("profil", locale));
            response.setHasError(false);
            return response;
        }

        List<ProfilDto> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                ? ProfilTransformer.INSTANCE.toLiteDtos(items)
                : ProfilTransformer.INSTANCE.toDtos(items);

        final int size = items.size();
        List<String> listOfError = Collections.synchronizedList(new ArrayList<String>());
        itemsDto.parallelStream().forEach(dto -> {
            try {
                dto = getFullInfos(dto, size, request.getIsSimpleLoading(), locale);
            } catch (Exception e) {
                listOfError.add(e.getMessage());
                e.printStackTrace();
            }
        });
        if (Utilities.isNotEmpty(listOfError)) {
            Object[] objArray = listOfError.stream().distinct().toArray();
            throw new RuntimeException(StringUtils.join(objArray, ", "));
        }

        response.setItems(itemsDto);
        response.setCount(statusUtilRepository.count(request, em, locale));
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));

        log.info("----end get Profil-----");
        return response;*/
        return null;
    }

   /* private ProfilDto getFullInfos(ProfilDto dto, Integer size, Boolean isSimpleLoading, Locale locale)
            throws Exception {
        // put code here
        if (Utilities.isTrue(isSimpleLoading)) {
            return dto;
        }
        if (size > 1) {
            return dto;
        }
        var datasFonctionnalites = profilFonctionnaliteRepository.getFonctionnalitedByProfilId(dto.getId(), Boolean.FALSE);
        if (Utilities.isNotEmpty(datasFonctionnalites)) {
            dto.setDatasFonctionnalite(FonctionnaliteTransformer.INSTANCE.toDtos(datasFonctionnalites));
        }
        return dto;
    }*/


    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public  Response<CompagnieTransportDTO> getAllProcessingCompagnies(Request<CompagnieTransportDTO> request, Locale locale) throws ParseException {
        Response<CompagnieTransportDTO> response = new Response<CompagnieTransportDTO>();
        List<CompagnieTransport> items = new ArrayList<CompagnieTransport>();
        Map<String, Object> fieldsToVerify = new HashMap<String, Object>();
        fieldsToVerify.put("size",request.getSize());
        fieldsToVerify.put("index",request.getIndex());
        if (!Validate.RequiredValue(fieldsToVerify).isGood()) {
            response.setStatus(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
            response.setHasError(true);
            return response;
        }
        Long count=0L;
        count=compagnieTransportRepository.countAllProcessingCompagnies(StatusUtilConstants.COMPAGNIE_TRANSPORT_ENCOURS_TRAITEMENT,false);
        log.info("_493 COUNT=====:"+count); //TODO A effacer
        items=compagnieTransportRepository.getAllProcessingCompagnies(StatusUtilConstants.COMPAGNIE_TRANSPORT_ENCOURS_TRAITEMENT,false, PageRequest.of(request.getIndex(), request.getSize()));
        log.info("_494 ITEMS=====:"+items.toString()); //TODO A effacer
        if(CollectionUtils.isEmpty(items)){
            response.setStatus(functionalError.DATA_NOT_EXIST("Aucune compagnie de transport en de traiement",locale));
            response.setHasError(true);
            return response;
        }
        List<CompagnieTransportDTO> itemsDto = (Utilities.isTrue(request.getIsSimpleLoading()))
                             ? CompagnieTransportTransformer.INSTANCE.toLiteDtos(items)
                             : CompagnieTransportTransformer.INSTANCE.toDtos(items);
        response.setCount(count);
        response.setItems(itemsDto);
        response.setHasError(false);
        response.setStatus(functionalError.SUCCESS("", locale));
        return response;
    }
}