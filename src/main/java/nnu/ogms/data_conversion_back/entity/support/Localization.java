package nnu.ogms.data_conversion_back.entity.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author mingyuan
 * @Date 2020.10.21 14:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Localization implements Comparable<Localization> {

    String localCode;
    String localName;
    String name;
    String description;

    @Override
    public int compareTo(Localization localization){
        return this.localName.compareTo(localization.localName);
    }
}
