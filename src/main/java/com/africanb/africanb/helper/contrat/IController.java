<<<<<<< HEAD
package com.africanb.africanb.helper.contrat;

import com.africanb.africanb.helper.enums.FunctionalityEnum;


public interface IController<DTO> {

    Response<DTO> create(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum);

    Response<DTO> update(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum);

    Response<DTO> delete(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum);

    Response<DTO> forceDelete(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum);

    Response<DTO> getByCriteria(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum);

    Response<DTO> getAll(IBasicBusiness iBasicBusiness);

}
=======
package com.africanb.africanb.helper.contrat;

import com.africanb.africanb.helper.enums.FunctionalityEnum;


public interface IController<DTO> {

    Response<DTO> create(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum);

    Response<DTO> update(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum);

    Response<DTO> delete(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum);

    Response<DTO> forceDelete(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum);

    Response<DTO> getByCriteria(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum);

    Response<DTO> getAll(IBasicBusiness iBasicBusiness);

}
>>>>>>> 2623ac8ab9f0101a6fb81e37d6d14c50fd8b0d2b
