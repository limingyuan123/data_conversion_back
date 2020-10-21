package nnu.ogms.data_conversion_back.entity;

import lombok.Data;

/**
 * @Author mingyuan
 * @Date 2020.10.19 16:50
 */
@Data
public class User {
    String uid;
    String name;
    String email;
    String password;
}
