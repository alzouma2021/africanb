package com.africanb.africanb.rest.api.offreVoyage;


import com.africanb.africanb.Business.offreVoyage.PrixOffreVoyageBusiness;
import com.africanb.africanb.helper.ExceptionUtils;
import com.africanb.africanb.helper.TechnicalError;
import com.africanb.africanb.helper.contrat.Request;
import com.africanb.africanb.helper.contrat.Response;
import com.africanb.africanb.helper.dto.compagnie.CompagnieTransportDTO;
import com.africanb.africanb.helper.dto.offreVoyage.PrixOffreVoyageDTO;
import com.africanb.africanb.helper.enums.FunctionalityEnum;
import com.africanb.africanb.helper.status.StatusCode;
import com.africanb.africanb.helper.status.StatusMessage;
import com.africanb.africanb.rest.fact.ControllerFactory;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;


@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/prixOffreVoyages")
public class PrixOffreVoyageController {

    @Autowired
    private ControllerFactory<PrixOffreVoyageDTO> controllerFactory;
    @Autowired
    private PrixOffreVoyageBusiness prixOffreVoyageBusiness;

    @Autowired
    private TechnicalError technicalError;
    @Autowired
    private ExceptionUtils exceptionUtils;
    @Autowired
    private HttpServletRequest requestBasic;

    @RequestMapping(value="",method= RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<PrixOffreVoyageDTO> create(@RequestBody Request<PrixOffreVoyageDTO> request) {
        log.info("start method create");
        Response<PrixOffreVoyageDTO> response = controllerFactory.create(prixOffreVoyageBusiness, request, FunctionalityEnum.CREATE_COMPAGNIETRANSPORT);
        log.info("end method create");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.PUT,consumes = {"application/json"},produces={"application/json"})
    public Response<PrixOffreVoyageDTO> update(@RequestBody Request<PrixOffreVoyageDTO> request) {
        log.info("start method update");
        Response<PrixOffreVoyageDTO> response = controllerFactory.update(prixOffreVoyageBusiness, request, FunctionalityEnum.UPDATE_COMPAGNIETRANSPORT);
        log.info("end method update");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.DELETE,consumes = {"application/json"},produces={"application/json"})
    public Response<PrixOffreVoyageDTO> delete(@RequestBody Request<PrixOffreVoyageDTO> request) {
        log.info("start method delete");
        Response<PrixOffreVoyageDTO> response = controllerFactory.delete(prixOffreVoyageBusiness, request, FunctionalityEnum.DELETE_COMPAGNIETRANSPORT);
        log.info("end method delete");
        return response;
    }

    @RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<PrixOffreVoyageDTO> getByCriteria(@RequestBody Request<PrixOffreVoyageDTO> request) {
        log.info("start method /prixOffreVoyage/getByCriteria");
        Response<PrixOffreVoyageDTO> response = controllerFactory.getByCriteria(prixOffreVoyageBusiness, request, FunctionalityEnum.VIEW_COMPAGNIETRANSPORT);
        log.info("end method /prixOffreVoyage/getByCriteria");
        return response;
    }

}
