package nnu.ogms.data_conversion_back.dao;

import nnu.ogms.data_conversion_back.entity.Datamap;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * @Author mingyuan
 * @Date 2020.10.27 16:38
 */

public interface DatamapDao extends MongoRepository<Datamap, String> {
    Datamap findFirstByName(String name);
    Datamap findFirstByOid(String oid);
}
