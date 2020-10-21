package nnu.ogms.data_conversion_back.entity.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author mingyuan
 * @Date 2020.10.21 14:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document

public class AuthorInfo {
    String name;
    String ins;
    String email;
    String homepage;
}
