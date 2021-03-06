package nnu.ogms.data_conversion_back.controller;

import lombok.extern.slf4j.Slf4j;
import nnu.ogms.data_conversion_back.bean.JsonResult;
import nnu.ogms.data_conversion_back.dao.DatamapDao;
import nnu.ogms.data_conversion_back.dao.RefactorDao;
import nnu.ogms.data_conversion_back.dao.TemplateDao;
import nnu.ogms.data_conversion_back.dao.VisualizationDao;
import nnu.ogms.data_conversion_back.entity.Datamap;
import nnu.ogms.data_conversion_back.entity.Refactor;
import nnu.ogms.data_conversion_back.entity.Template;
import nnu.ogms.data_conversion_back.entity.Visualization;
import nnu.ogms.data_conversion_back.service.DataConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static nnu.ogms.data_conversion_back.utils.Tools.JsonToXml;
import static nnu.ogms.data_conversion_back.utils.Tools.xmlToJson;

/**
 * @Author mingyuan
 * @Date 2020.10.19 16:50
 */
@RestController
@RequestMapping(value = "/data")
@Slf4j
public class DataConversionController {
    @Autowired
    TemplateDao templateDao;

    @Autowired
    DataConversionService dataConversionService;

    @Autowired
    DatamapDao dataMapDao;

    @Autowired
    RefactorDao refactorDao;

    @Autowired
    VisualizationDao visualizationDao;

    @RequestMapping(value = "/getTemplate/{page}", method = RequestMethod.GET)
    public JsonResult getTemplate(@PathVariable("page") int page){
        JsonResult jsonResult = new JsonResult();
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<Template> list = templateDao.findAllBy(pageable);
        jsonResult.setCode(0);
        jsonResult.setData(list);
        return jsonResult;
    }

    @RequestMapping(value = "/getXmlData/{oid}", method = RequestMethod.GET)
    public JsonResult getXmlData(@PathVariable("oid") String oid){
        JsonResult jsonResult = new JsonResult();
        Template template = templateDao.findFirstByOid(oid);

        if (template==null){
            jsonResult.setCode(-1);
            jsonResult.setMsg("oid is not exist!");
            return jsonResult;
        }

        jsonResult.setCode(0);
        jsonResult.setData(template);
        return jsonResult;
    }

    /**
     * 原始数据转为udx数据
     * @param raw 原始文件
     * @param udxSchema udxSchema数据
     * @return udxData以及可提供udxData处理后数据下载
     */
    @RequestMapping(value = "/rawToUdx", method = RequestMethod.GET)
    public JsonResult rawToUdx(@RequestParam(value = "raw") MultipartFile raw,
                               @RequestParam(value = "udxSchema") String udxSchema){
        JsonResult jsonResult = new JsonResult();
        //解析原始数据,匹配udxSchema生成udxData
        jsonResult = dataConversionService.rawToUdx(raw, udxSchema);
        return jsonResult;
    }

    /**
     * 基于udx数据的数据可视化接口
     * @param udxData udx数据文件
     * @return 可视化文件
     */
    @RequestMapping(value = "/udxVisual", method = RequestMethod.GET)
    public JsonResult udxVisual(@RequestParam(value = "udxData") MultipartFile udxData){
        JsonResult jsonResult = new JsonResult();


        return jsonResult;
    }

    @RequestMapping(value = "/operation/{id}", method = RequestMethod.GET)
    public ModelAndView loadOperation(@PathVariable(value = "id") String id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("operation/operation");
        Template template = templateDao.findFirstByOid(id);
        modelAndView.addObject("info", template);
        return modelAndView;
    }

    @RequestMapping(value = "/getDataMap/{type}", method = RequestMethod.GET)
    public JsonResult getDataMap(@PathVariable(value = "type") String type){
        JsonResult jsonResult = new JsonResult();
        if (type.equals("map")) {
            List<Datamap> dataMaps = dataMapDao.findAll();
            jsonResult.setData(dataMaps);
        }else if (type.equals("refactor")){
            List<Refactor> refactors = refactorDao.findAll();
            jsonResult.setData(refactors);
        }else {
            List<Visualization> visualizations = visualizationDao.findAll();
            jsonResult.setData(visualizations);
        }
        return jsonResult;
    }

    /**
     * test xml-json
     */
    @RequestMapping(value = "/xmlToJSON", method = RequestMethod.GET)
    public String xmlToJSON(@RequestParam(value = "xml") String xml){
        String res = xmlToJson(xml);
        log.info(res+"");
        return res;
    }

    /**
     * json-xml
     * @param json
     * @return
     */
    @RequestMapping(value = "/jsonToXML", method = RequestMethod.GET)
    public String jsonToXML(@RequestParam(value = "json") String json){
        String res = JsonToXml(json);
        log.info(res+"");
        return res;
    }

}
