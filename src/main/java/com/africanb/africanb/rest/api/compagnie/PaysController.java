package com.africanb.africanb.rest.api.compagnie;


import com.africanb.africanb.Business.compagnie.PaysBusiness;
import com.africanb.africanb.helper.contrat.Request;
import com.africanb.africanb.helper.contrat.Response;
import com.africanb.africanb.helper.dto.compagnie.PaysDTO;
import com.africanb.africanb.helper.enums.FunctionalityEnum;
import com.africanb.africanb.rest.fact.ControllerFactory;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/pays")
public class PaysController {

    @Autowired
    private ControllerFactory<PaysDTO> controllerFactory;
    @Autowired
    private PaysBusiness paysBusiness;


    @RequestMapping(value="",method= RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<PaysDTO> create(@RequestBody Request<PaysDTO> request) {
        log.info("start method create");
        Response<PaysDTO> response = controllerFactory.create(paysBusiness, request, FunctionalityEnum.CREATE_PAYS);
        log.info("end method create");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.PUT,consumes = {"application/json"},produces={"application/json"})
    public Response<PaysDTO> update(@RequestBody Request<PaysDTO> request) {
        log.info("start method update");
        Response<PaysDTO> response = controllerFactory.update(paysBusiness, request, FunctionalityEnum.UPDATE_PAYS);
        log.info("end method update");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.DELETE,consumes = {"application/json"},produces={"application/json"})
    public Response<PaysDTO> delete(@RequestBody Request<PaysDTO> request) {
        log.info("start method delete");
        Response<PaysDTO> response = controllerFactory.delete(paysBusiness, request, FunctionalityEnum.DELETE_PAYS);
        log.info("end method delete");
        return response;
    }

    @RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<PaysDTO> getByCriteria(@RequestBody Request<PaysDTO> request) {
        log.info("start method /pays/getByCriteria");
        Response<PaysDTO> response = controllerFactory.getByCriteria(paysBusiness, request, FunctionalityEnum.VIEW_PAYS);
        log.info("end method /pays/getByCriteria");
        return response;
    }

}
