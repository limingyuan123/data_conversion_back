package nnu.ogms.data_conversion_back.dao;

import nnu.ogms.data_conversion_back.entity.Refactor;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author mingyuan
 * @Date 2020.10.27 21:59
 */
public interface RefactorDao extends MongoRepository<Refactor, String> {
    Refactor findFirstByOid(String oid);
}
