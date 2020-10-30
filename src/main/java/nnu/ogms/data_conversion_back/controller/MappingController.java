package nnu.ogms.data_conversion_back.controller;

import nnu.ogms.data_conversion_back.bean.JsonResult;
import nnu.ogms.data_conversion_back.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mingyuan
 * @Date 2020.10.28 22:36
 */
@RestController
@RequestMapping("/map")
public class MappingController {
    @Autowired
    MappingService mappingService;

    @RequestMapping(value = "/ascToUdx", method = RequestMethod.GET)
    public JsonResult ascToUdx(){
        JsonResult jsonResult = new JsonResult();


        return jsonResult;
    }
}
