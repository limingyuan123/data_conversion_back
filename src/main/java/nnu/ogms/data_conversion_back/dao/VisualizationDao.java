package nnu.ogms.data_conversion_back.dao;

import nnu.ogms.data_conversion_back.entity.Visualization;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author mingyuan
 * @Date 2020.10.27 22:06
 */
public interface VisualizationDao extends MongoRepository<Visualization, String> {
    Visualization findFirstByOid(String oid);
}
