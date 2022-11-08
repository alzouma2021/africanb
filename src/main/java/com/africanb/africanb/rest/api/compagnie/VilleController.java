package com.africanb.africanb.rest.api.compagnie;


import com.africanb.africanb.Business.compagnie.VilleBusiness;
import com.africanb.africanb.helper.contrat.Request;
import com.africanb.africanb.helper.contrat.Response;
import com.africanb.africanb.helper.dto.compagnie.PaysDTO;
import com.africanb.africanb.helper.dto.compagnie.VilleDTO;
import com.africanb.africanb.helper.enums.FunctionalityEnum;
import com.africanb.africanb.rest.fact.ControllerFactory;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/villes")
public class VilleController {

    @Autowired
    private ControllerFactory<VilleDTO> controllerFactory;
    @Autowired
    private VilleBusiness villeBusiness;


    @RequestMapping(value="",method= RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<VilleDTO> create(@RequestBody Request<VilleDTO> request) {
        log.info("start method create");
        Response<VilleDTO> response = controllerFactory.create(villeBusiness, request, FunctionalityEnum.CREATE_PAYS);
        log.info("end method create");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.PUT,consumes = {"application/json"},produces={"application/json"})
    public Response<VilleDTO> update(@RequestBody Request<VilleDTO> request) {
        log.info("start method update");
        Response<VilleDTO> response = controllerFactory.update(villeBusiness, request, FunctionalityEnum.UPDATE_PAYS);
        log.info("end method update");
        return response;
    }

    @RequestMapping(value="",method=RequestMethod.DELETE,consumes = {"application/json"},produces={"application/json"})
    public Response<VilleDTO> delete(@RequestBody Request<VilleDTO> request) {
        log.info("start method delete");
        Response<VilleDTO> response = controllerFactory.delete(villeBusiness, request, FunctionalityEnum.DELETE_PAYS);
        log.info("end method delete");
        return response;
    }

    @RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<VilleDTO> getByCriteria(@RequestBody Request<VilleDTO> request) {
        log.info("start method /pays/getByCriteria");
        Response<VilleDTO> response = controllerFactory.getByCriteria(villeBusiness, request, FunctionalityEnum.VIEW_PAYS);
        log.info("end method /pays/getByCriteria");
        return response;
    }

}
