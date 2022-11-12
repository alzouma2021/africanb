package com.africanb.africanb.rest.api.compagnie;


import com.africanb.africanb.Business.compagnie.CompagnieTransportBusiness;
import com.africanb.africanb.helper.contrat.Request;
import com.africanb.africanb.helper.contrat.Response;
import com.africanb.africanb.helper.dto.compagnie.CompagnieTransportDTO;
import com.africanb.africanb.helper.dto.compagnie.PaysDTO;
import com.africanb.africanb.helper.enums.FunctionalityEnum;
import com.africanb.africanb.rest.fact.ControllerFactory;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/compagnieTransport")
public class CompagnieTransportController {

    @Autowired
    private ControllerFactory<CompagnieTransportDTO> controllerFactory;
    @Autowired
    private CompagnieTransportBusiness compagnieTransportBusiness;


    @RequestMapping(value="",method= RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<CompagnieTransportDTO> create(@RequestBody Request<CompagnieTransportDTO> request) {
        log.info("start method create");
        Response<CompagnieTransportDTO> response = controllerFactory.create(compagnieTransportBusiness, request, FunctionalityEnum.CREATE_COMPAGNIETRANSPORT);
        log.info("end method create");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.PUT,consumes = {"application/json"},produces={"application/json"})
    public Response<CompagnieTransportDTO> update(@RequestBody Request<CompagnieTransportDTO> request) {
        log.info("start method update");
        Response<CompagnieTransportDTO> response = controllerFactory.update(compagnieTransportBusiness, request, FunctionalityEnum.UPDATE_COMPAGNIETRANSPORT);
        log.info("end method update");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.DELETE,consumes = {"application/json"},produces={"application/json"})
    public Response<CompagnieTransportDTO> delete(@RequestBody Request<CompagnieTransportDTO> request) {
        log.info("start method delete");
        Response<CompagnieTransportDTO> response = controllerFactory.delete(compagnieTransportBusiness, request, FunctionalityEnum.DELETE_COMPAGNIETRANSPORT);
        log.info("end method delete");
        return response;
    }

    @RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<CompagnieTransportDTO> getByCriteria(@RequestBody Request<CompagnieTransportDTO> request) {
        log.info("start method /pays/getByCriteria");
        Response<CompagnieTransportDTO> response = controllerFactory.getByCriteria(compagnieTransportBusiness, request, FunctionalityEnum.VIEW_COMPAGNIETRANSPORT);
        log.info("end method /pays/getByCriteria");
        return response;
    }

}
