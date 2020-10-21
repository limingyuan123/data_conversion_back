package nnu.ogms.data_conversion_back.dao;

import nnu.ogms.data_conversion_back.entity.Template;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author mingyuan
 * @Date 2020.10.21 14:16
 */
public interface TemplateDao extends MongoRepository<Template, String> {

}
