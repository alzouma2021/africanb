package com.africanb.africanb.rest.api.offreVoyage;


import com.africanb.africanb.Business.offreVoyage.JourSemaineBusiness;
import com.africanb.africanb.helper.ExceptionUtils;
import com.africanb.africanb.helper.TechnicalError;
import com.africanb.africanb.helper.contrat.Request;
import com.africanb.africanb.helper.contrat.Response;
import com.africanb.africanb.helper.dto.offreVoyage.JourSemaineDTO;
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
@RequestMapping(value="/jourSemaines")
public class JourSemaineController {

    @Autowired
    private ControllerFactory<JourSemaineDTO> controllerFactory;
    @Autowired
    private JourSemaineBusiness jourSemaineBusiness;
    @Autowired
    private TechnicalError technicalError;
    @Autowired
    private ExceptionUtils exceptionUtils;
    @Autowired
    private HttpServletRequest requestBasic;

    @RequestMapping(value="",method= RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<JourSemaineDTO> create(@RequestBody Request<JourSemaineDTO> request) {
        log.info("start method create");
        Response<JourSemaineDTO> response = controllerFactory.create(jourSemaineBusiness, request, FunctionalityEnum.CREATE_JOURSEMAINE);
        log.info("end method create");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.PUT,consumes = {"application/json"},produces={"application/json"})
    public Response<JourSemaineDTO> update(@RequestBody Request<JourSemaineDTO> request) {
        log.info("start method update");
        Response<JourSemaineDTO> response = controllerFactory.update(jourSemaineBusiness, request, FunctionalityEnum.UPDATE_JOURSEMAINE);
        log.info("end method update");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.DELETE,consumes = {"application/json"},produces={"application/json"})
    public Response<JourSemaineDTO> delete(@RequestBody Request<JourSemaineDTO> request) {
        log.info("start method delete");
        Response<JourSemaineDTO> response = controllerFactory.delete(jourSemaineBusiness, request, FunctionalityEnum.DELETE_JOURSEMAINE);
        log.info("end method delete");
        return response;
    }

    @RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<JourSemaineDTO> getByCriteria(@RequestBody Request<JourSemaineDTO> request) {
        log.info("start method /jourSemaine/getByCriteria");
        Response<JourSemaineDTO> response = controllerFactory.getByCriteria(jourSemaineBusiness, request, FunctionalityEnum.VIEW_JOURSEMAINE);
        log.info("end method /jourSemaine/getByCriteria");
        return response;
    }
}
