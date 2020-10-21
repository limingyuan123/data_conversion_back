package nnu.ogms.data_conversion_back.controller;

import nnu.ogms.data_conversion_back.bean.JsonResult;
import nnu.ogms.data_conversion_back.dao.TemplateDao;
import nnu.ogms.data_conversion_back.entity.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mingyuan
 * @Date 2020.10.19 16:50
 */
@RequestMapping(value = "/data")
public class DataConversionController {
    @Autowired
    TemplateDao templateDao;

    @RequestMapping(value = "/getTemplate", method = RequestMethod.GET)
    public JsonResult getTemplate(){
        JsonResult jsonResult = new JsonResult();
        List<Template> list = templateDao.findAll();
        jsonResult.setCode(0);
        jsonResult.setData(list);
        return jsonResult;
    }



}
