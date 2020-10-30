package nnu.ogms.data_conversion_back.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author mingyuan
 * @Date 2020.10.27 21:57
 */
@Document
@Data
public abstract class Operation {
    @Id
    String oid;
    String name;
    String description;
    String uname;
    String uemail;
    String author;
    String datetime;
    String snapshot;
    String available;
    String detail;
    String uid;
    String associate;
    String delete;
    String deletetime;
}
