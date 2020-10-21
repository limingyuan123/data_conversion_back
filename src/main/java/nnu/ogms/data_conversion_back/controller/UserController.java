package nnu.ogms.data_conversion_back.controller;

import nnu.ogms.data_conversion_back.bean.JsonResult;
import nnu.ogms.data_conversion_back.dao.UserDao;
import nnu.ogms.data_conversion_back.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @Author mingyuan
 * @Date 2020.10.19 16:50
 */
@CrossOrigin(origins = "http://localhost:8082",allowCredentials = "true")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserDao userDao;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonResult register(@RequestBody User user){
        JsonResult jsonResult = new JsonResult();
        User user1 = new User();
        user1.setEmail(user.getEmail());
        user1.setName(user.getName());
        user1.setPassword(user.getPassword());
        user1.setUid(UUID.randomUUID().toString());
        userDao.insert(user1);

        jsonResult.setCode(0);
        jsonResult.setMsg("insert success");
        jsonResult.setData(user1);

        return jsonResult;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResult login(@RequestBody User user){
        JsonResult jsonResult = new JsonResult();
        User user1 = userDao.findFirstByName(user.getName());
        if (null == user1){
            jsonResult.setCode(-1);
            jsonResult.setMsg("login failed");
            return jsonResult;
        }

        if (user1.getPassword().equals(user.getPassword())){
            jsonResult.setCode(0);
            jsonResult.setMsg("login success");
        }

        return jsonResult;
    }
}
