package nnu.ogms.data_conversion_back.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import lombok.extern.slf4j.Slf4j;
import nnu.ogms.data_conversion_back.bean.JsonResult;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.ObjectName;
import javax.naming.event.ObjectChangeListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static nnu.ogms.data_conversion_back.utils.Tools.JsonToXml;
import static nnu.ogms.data_conversion_back.utils.Tools.xmlToJson;

/**
 * @Author mingyuan
 * @Date 2020.10.28 22:38
 */
@Service
@Slf4j
public class MappingService {

    /**
     * 将ascii文件转为udxData
     * @param ascii 输入文件
     * @param udxSchema udxschema
     * @return 解析完成的udxData
     */
    public JsonResult asciiToUdx(MultipartFile ascii, String udxSchema){
        //因为新版本限制了autotype，导致无法解析json，故添加autotype白名单
        ParserConfig.getGlobalInstance().addAccept("com.taobao.pac.client.sdk.dataobject.");
        //打开autotype功能
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        JsonResult jsonResult = new JsonResult();
        //解析udxSchema
        JSONObject jsonObj = parseSchema(udxSchema);

        //解析ascii文件
        List<Object> asc = parseAscii(ascii);
        Map<String, Object> rawHeadString = new HashMap<>();
        ArrayList<String> rawBodyString = new ArrayList<>();
        rawHeadString = (Map<String, Object>) asc.get(0);
        rawBodyString = (ArrayList<String>) asc.get(1);

        //更改head属性值
        JSONArray all = JSONArray.parseArray(jsonObj.getString("XDO"));
        JSONObject head = JSONObject.parseObject(all.getString(0));
        JSONArray headArray = JSONArray.parseArray(head.getString("XDO"));
        JSONObject body = JSONObject.parseObject(all.getString(1));
        JSONObject bodyObject = JSONObject.parseObject(body.getString("XDO"));


//        ArrayList<Object> rawHeadKey = new ArrayList<>();
//        ArrayList<Object> rawHeadValue = new ArrayList<>();
//        //使用lambda表达式
//        rawHeadString.forEach((key,value) ->{
//            rawHeadKey.add(key);
//            rawHeadValue.add(value);
//        });

        //这个也可以解耦出去，将head的数据修正
        for (int i=0;i<headArray.size();i++){
            ((JSONObject) headArray.get(i)).put("@value", rawHeadString.get(((JSONObject) headArray.get(i)).get("@name").toString()));
            if (((JSONObject) headArray.get(i)).get("@kernelType").toString().equals("DTKT_INT")){
                ((JSONObject) headArray.get(i)).put("@kernelType", "@int");
            }else if (((JSONObject) headArray.get(i)).get("@kernelType").toString().equals("DTKT_REAL")){
                ((JSONObject) headArray.get(i)).put("@kernelType", "@real");
            }

        }
        head.put("XDO", headArray);//将解析好的数据放回主文件

        //修正body数据
        if (body.get("@kernelType").toString().equals("DTKT_LIST")){
            body.put("@kernelType", "@list");
        }
        JSONArray ja = new JSONArray();
        for (int i=0;i<rawBodyString.size();i++){
            JSONObject jo = bodyObject;
            jo.put("@value", rawBodyString.get(i));
            body.put("XDO", jo);
            String xml = JsonToXml(body.toString());
            ja.add(jo);
        }
        body.put("XDO", ja);
        JSONObject all11 = new JSONObject();
        JSONArray all1 = new JSONArray();
        all1.add(head);
        all1.add(body);
        jsonObj.put("XDO",all1);


        log.info(jsonObj.toJSONString()+"");
        //转为xml
        String xmlTest = JsonToXml(jsonObj.toString());


        //映射body部分

        return jsonResult;
    }

    /**
     * 获取ascii文件中的head以及body
     * @param raw 源文件
     * @return 提取出来的head与body
     */
    public List<Object> parseAscii(MultipartFile raw){
        Map<String, Object> rawHeadString = new HashMap<>();
        ArrayList<String> rawBodyString = new ArrayList<>();
        List<Object> res = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(raw.getInputStream()));
            String lineTxt;
            try {
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (Character.isLetter(lineTxt.charAt(0))) {
                        String[] names = lineTxt.split("    ");
                        rawHeadString.put(names[0], names[1]);
                    }else {
                        rawBodyString.add(lineTxt);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        res.add(rawHeadString);
        res.add(rawBodyString);


        return res;
    }

    /**
     * 解析udxSchema
     * @param udxSchema 待解析的schema
     * @return 解析后的JSONObject数据
     */
    public JSONObject parseSchema(String udxSchema){
        String jsonSchema = xmlToJson(udxSchema);
        JSONObject schema = JSONObject.parseObject(jsonSchema);

        //获取udxData未定前版本
        JSONObject udxData = JSONObject.parseObject(JSONObject.parseObject(schema.getString("UdxDeclaration")).getString("UdxNode"));
        log.info(udxData+"");
        //映射head部分
        Map<String, String> keyMap = new HashMap<String, String>();
        //更改节点名
        keyMap.put("UdxNode", "XDO");
        keyMap.put("@type", "@kernelType");
        keyMap.put("@description", "@value");
        return changeJsonObj(udxData,keyMap);//当前为json格式的data描述文件
    }

    /**
     * 将json格式的数据更改节点名称
     * @param jsonObj 待修改的jsonobject
     * @param keyMap 修改规则
     * @return 修改结果
     */
    public static JSONObject changeJsonObj(JSONObject jsonObj,Map<String, String> keyMap) {
        JSONObject resJson = new JSONObject();
        Set<String> keySet = jsonObj.keySet();
        for (String key : keySet) {
            String resKey = keyMap.get(key) == null ? key : keyMap.get(key);
            try {
                JSONObject jsonobj1 = jsonObj.getJSONObject(key);
                resJson.put(resKey, changeJsonObj(jsonobj1, keyMap));
            } catch (Exception e) {
                try {
                    JSONArray jsonArr = jsonObj.getJSONArray(key);
                    resJson.put(resKey, changeJsonArr(jsonArr, keyMap));
                } catch (Exception x) {
                    resJson.put(resKey, jsonObj.get(key));
                }
            }
        }
        return resJson;
    }

    /**
     * 转换函数的子函数
     * @param jsonArr JSONArray
     * @param keyMap 修改规则
     * @return JSONArray
     */
    public static JSONArray changeJsonArr(JSONArray jsonArr,Map<String, String> keyMap) {
        JSONArray resJson = new JSONArray();
        for (int i = 0; i < jsonArr.size(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            resJson.add(changeJsonObj(jsonObj, keyMap));
        }
        return resJson;
    }
}
