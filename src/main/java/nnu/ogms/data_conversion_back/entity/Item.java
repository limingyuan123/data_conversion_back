package nnu.ogms.data_conversion_back.entity;

import lombok.Data;
import nnu.ogms.data_conversion_back.entity.support.AuthorInfo;
import nnu.ogms.data_conversion_back.entity.support.DailyViewCount;
import nnu.ogms.data_conversion_back.entity.support.Localization;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author mingyuan
 * @Date 2020.10.21 14:05
 */
@Document
@Data
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    //Basic Info

    @Id
    String id;
    String oid;
    String name;
    List<String> alias;
    //String image;
    String description;
    String detail;
    String author;
    List<String> keywords;

    Date createTime;

    Date lastModifyTime;

    //Public, Discoverable or Private
    String status;

    List<Localization> localizationList = new ArrayList<>();

    //authorship
    List<AuthorInfo> authorship;

    //version
    String lastModifier;
    List<String> contributors;
    List<String> versions;

    boolean lock = false;

    //statistic
    int shareCount = 0;
    int viewCount = 0;
    int thumbsUpCount = 0;

    List<DailyViewCount> dailyViewCount = new ArrayList<>();

}
