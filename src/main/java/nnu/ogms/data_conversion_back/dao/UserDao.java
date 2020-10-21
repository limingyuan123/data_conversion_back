package nnu.ogms.data_conversion_back.dao;

import nnu.ogms.data_conversion_back.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author mingyuan
 * @Date 2020.10.20 20:25
 */
public interface UserDao extends MongoRepository<User, String> {
    User findFirstByUid(String uid);
    User findFirstByName(String name);
}
