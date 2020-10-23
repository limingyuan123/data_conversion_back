package nnu.ogms.data_conversion_back.service;

import com.sun.el.lang.ELArithmetic;
import lombok.extern.slf4j.Slf4j;
import nnu.ogms.data_conversion_back.bean.JsonResult;
import nnu.ogms.data_conversion_back.dao.TemplateDao;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author mingyuan
 * @Date 2020.10.19 16:51
 */
@Service
@Slf4j
public class DataConversionService {
    @Autowired
    TemplateDao templateDao;

    /**
     * udx数据转为原始数据
     *
     * @return
     */
    public JsonResult udxToRaw() {
        JsonResult jsonResult = new JsonResult();
        return jsonResult;
    }

    /**
     * 原始数据转换为udx数据service
     * @param raw 原始数据
     * @param udxSchema udxSchema
     * @return udxData
     */
    public JsonResult rawToUdx(MultipartFile raw, String udxSchema) {
        JsonResult jsonResult = new JsonResult();
        String udxData = "<Dataset><XDO name=\"head\" kernelType=\"any\">";

        ArrayList<String> headString = new ArrayList<>();
        ArrayList<String> bodyString = new ArrayList<>();
        Map<String, Float> rawHeadString = new HashMap<>();
        ArrayList<String> rawBodyString = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(raw.getInputStream()));
            String lineTxt;
            //解析xml
            Document schema = DocumentHelper.parseText(udxSchema);
            //获取根元素
            Element root = schema.getRootElement();
            List<Element> father = root.element("UdxNode").elements();
            Element rootHead = father.get(0);
            Element rootBody = father.get(1);
            List<Element> head = rootHead.elements();
            List<Element> body = rootBody.elements();
            try {
                for (Element ele : head) {
                    headString.add(ele.attributeValue("name"));
                }
                for (Element ele:body){
                    bodyString.add(ele.attributeValue("name"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (Character.isLetter(lineTxt.charAt(0))) {
                        String[] names = lineTxt.split("    ");
                        rawHeadString.put(names[0], Float.parseFloat(names[1]));
                    }else {
                        rawBodyString.add(lineTxt);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        for (int i=0;i<headString.size();i++){
            //这边有一个kernelType需要更正
            udxData +=( "<XDO name=\"" + headString.get(i) + "\" " + "kernelType=\"int\" value=\"" + rawHeadString.get(headString.get(i)) + "\"/>");
        }
        udxData += "</XDO><XDO name=\"body\" kernelType=\"list\">";

        for (int i=0;i<rawBodyString.size();i++) {
            udxData += ("<XDO name=\"Row_Item\" kernelType=\"real_array\" value=\"" + rawBodyString.get(i) + "/>");
        }

        udxData+="</XDO>\n" +
                "    <XDO name=\"Projection\" kernelType=\"string\" value=\"\"/>\n" +
                "</Dataset>";
        jsonResult.setCode(0);
        jsonResult.setData(udxData);

        //写入文件，可提供下载
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File("E:/test/udxData.xml");
            if (!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(udxData);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bufferedWriter!=null){
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return jsonResult;
    }

    /**
     * 解析udx数据
     *
     * @return
     */
    public JsonResult udxPrase() {
        JsonResult jsonResult = new JsonResult();
        return jsonResult;
    }

}
