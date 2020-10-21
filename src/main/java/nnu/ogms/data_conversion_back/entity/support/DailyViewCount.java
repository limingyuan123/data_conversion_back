package nnu.ogms.data_conversion_back.entity.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author mingyuan
 * @Date 2020.10.21 14:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyViewCount implements Comparable<DailyViewCount> {
    Date date;
    int count;

    boolean flag = true;

    public DailyViewCount(Date date, int count){
        this.date = date;
        this.count = count;
    }

    @Override
    public int compareTo(DailyViewCount dailyViewCount){
        return date.compareTo(dailyViewCount.date);
    }
}
