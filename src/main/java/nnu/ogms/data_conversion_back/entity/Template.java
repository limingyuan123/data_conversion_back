package nnu.ogms.data_conversion_back.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @Author mingyuan
 * @Date 2020.10.21 12:17
 */
@Document
@Data
public class Template extends Item{
    String id;
    List<String> classifications;
    String image;

    String xml;
    String type;
    String parentId;
}
