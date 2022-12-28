package com.africanb.africanb.rest.api.offreVoyage;


import com.africanb.africanb.Business.offreVoyage.OffreVoyageBusiness;
import com.africanb.africanb.helper.ExceptionUtils;
import com.africanb.africanb.helper.TechnicalError;
import com.africanb.africanb.helper.contrat.Request;
import com.africanb.africanb.helper.contrat.Response;
import com.africanb.africanb.helper.dto.offreVoyage.OffreVoyageDTO;
import com.africanb.africanb.helper.dto.offreVoyage.PrixOffreVoyageDTO;
import com.africanb.africanb.helper.enums.FunctionalityEnum;
import com.africanb.africanb.rest.fact.ControllerFactory;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/offreVoyages")
public class OffreVoyageController {

    @Autowired
    private ControllerFactory<OffreVoyageDTO> controllerFactory;
    @Autowired
    private OffreVoyageBusiness offreVoyageBusiness;
    @Autowired
    private TechnicalError technicalError;
    @Autowired
    private ExceptionUtils exceptionUtils;
    @Autowired
    private HttpServletRequest requestBasic;

    @RequestMapping(value="",method= RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<OffreVoyageDTO> create(@RequestBody Request<OffreVoyageDTO> request) {
        log.info("start method create");
        Response<OffreVoyageDTO> response = controllerFactory.create(offreVoyageBusiness, request, FunctionalityEnum.CREATE_OFFREVOYAGE);
        log.info("end method create");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.PUT,consumes = {"application/json"},produces={"application/json"})
    public Response<OffreVoyageDTO> update(@RequestBody Request<OffreVoyageDTO> request) {
        log.info("start method update");
        Response<OffreVoyageDTO> response = controllerFactory.update(offreVoyageBusiness, request, FunctionalityEnum.UPDATE_OFFREVOYAGE);
        log.info("end method update");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.DELETE,consumes = {"application/json"},produces={"application/json"})
    public Response<OffreVoyageDTO> delete(@RequestBody Request<OffreVoyageDTO> request) {
        log.info("start method delete");
        Response<OffreVoyageDTO> response = controllerFactory.delete(offreVoyageBusiness, request, FunctionalityEnum.DELETE_OFFREVOYAGE);
        log.info("end method delete");
        return response;
    }

    @RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<OffreVoyageDTO> getByCriteria(@RequestBody Request<OffreVoyageDTO> request) {
        log.info("start method /offreVoyage/getByCriteria");
        Response<OffreVoyageDTO> response = controllerFactory.getByCriteria(offreVoyageBusiness, request, FunctionalityEnum.VIEW_OFFREVOYAGE);
        log.info("end method /offreVoyage/getByCriteria");
        return response;
    }
}
