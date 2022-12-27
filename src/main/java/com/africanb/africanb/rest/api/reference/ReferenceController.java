package com.africanb.africanb.rest.api.reference;


import com.africanb.africanb.helper.contrat.Request;
import com.africanb.africanb.helper.contrat.Response;
import com.africanb.africanb.helper.enums.FunctionalityEnum;
import com.africanb.africanb.rest.fact.ControllerFactory;
import com.africanb.africanb.utils.Reference.ReferenceBusines;
import com.africanb.africanb.utils.Reference.ReferenceDTO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/references")
public class ReferenceController {

    @Autowired
    private ControllerFactory<ReferenceDTO> controllerFactory;
    @Autowired
    private ReferenceBusines referenceBusines;

    @RequestMapping(value="",method= RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ReferenceDTO> create(@RequestBody Request<ReferenceDTO> request) {
        log.info("start method create");
        Response<ReferenceDTO> response = controllerFactory.create(referenceBusines, request, FunctionalityEnum.CREATE_REFERENCE);
        log.info("end method create");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.PUT,consumes = {"application/json"},produces={"application/json"})
    public Response<ReferenceDTO> update(@RequestBody Request<ReferenceDTO> request) {
        log.info("start method update");
        Response<ReferenceDTO> response = controllerFactory.update(referenceBusines, request, FunctionalityEnum.UPDATE_REFERENCE);
        log.info("end method update");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.DELETE,consumes = {"application/json"},produces={"application/json"})
    public Response<ReferenceDTO> delete(@RequestBody Request<ReferenceDTO> request) {
        log.info("start method delete");
        Response<ReferenceDTO> response = controllerFactory.delete(referenceBusines, request, FunctionalityEnum.DELETE_REFERENCE);
        log.info("end method delete");
        return response;
    }

    @RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<ReferenceDTO> getByCriteria(@RequestBody Request<ReferenceDTO> request) {
        log.info("start method /famillestatusutil/getByCriteria");
        Response<ReferenceDTO> response = controllerFactory.getByCriteria(referenceBusines, request, FunctionalityEnum.VIEW_REFERENCE);
        log.info("end method /famillestatusutil/getByCriteria");
        return response;
    }
}
