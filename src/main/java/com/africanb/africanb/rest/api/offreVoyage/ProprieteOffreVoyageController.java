package com.africanb.africanb.rest.api.offreVoyage;


import com.africanb.africanb.Business.offreVoyage.JourSemaineBusiness;
import com.africanb.africanb.Business.offreVoyage.ProprieteOffreVoyageBusiness;
import com.africanb.africanb.helper.ExceptionUtils;
import com.africanb.africanb.helper.TechnicalError;
import com.africanb.africanb.helper.contrat.Request;
import com.africanb.africanb.helper.contrat.Response;
import com.africanb.africanb.helper.dto.offreVoyage.JourSemaineDTO;
import com.africanb.africanb.helper.dto.offreVoyage.ProprieteOffreVoyageDTO;
import com.africanb.africanb.helper.enums.FunctionalityEnum;
import com.africanb.africanb.rest.fact.ControllerFactory;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/proprieteOffreVoyages")
public class ProprieteOffreVoyageController {

    @Autowired
    private ControllerFactory<ProprieteOffreVoyageDTO> controllerFactory;
    @Autowired
    private ProprieteOffreVoyageBusiness proprieteOffreVoyageBusiness;
    @Autowired
    private TechnicalError technicalError;
    @Autowired
    private ExceptionUtils exceptionUtils;
    @Autowired
    private HttpServletRequest requestBasic;

    @RequestMapping(value="",method= RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProprieteOffreVoyageDTO> create(@RequestBody Request<ProprieteOffreVoyageDTO> request) {
        log.info("start method create");
        Response<ProprieteOffreVoyageDTO> response = controllerFactory.create(proprieteOffreVoyageBusiness, request, FunctionalityEnum.CREATE_JOURSEMAINE);
        log.info("end method create");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.PUT,consumes = {"application/json"},produces={"application/json"})
    public Response<ProprieteOffreVoyageDTO> update(@RequestBody Request<ProprieteOffreVoyageDTO> request) {
        log.info("start method update");
        Response<ProprieteOffreVoyageDTO> response = controllerFactory.update(proprieteOffreVoyageBusiness, request, FunctionalityEnum.UPDATE_JOURSEMAINE);
        log.info("end method update");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.DELETE,consumes = {"application/json"},produces={"application/json"})
    public Response<ProprieteOffreVoyageDTO> delete(@RequestBody Request<ProprieteOffreVoyageDTO> request) {
        log.info("start method delete");
        Response<ProprieteOffreVoyageDTO> response = controllerFactory.delete(proprieteOffreVoyageBusiness, request, FunctionalityEnum.DELETE_JOURSEMAINE);
        log.info("end method delete");
        return response;
    }

    @RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ProprieteOffreVoyageDTO> getByCriteria(@RequestBody Request<ProprieteOffreVoyageDTO> request) {
        log.info("start method /jourSemaine/getByCriteria");
        Response<ProprieteOffreVoyageDTO> response = controllerFactory.getByCriteria(proprieteOffreVoyageBusiness, request, FunctionalityEnum.VIEW_JOURSEMAINE);
        log.info("end method /jourSemaine/getByCriteria");
        return response;
    }
}
