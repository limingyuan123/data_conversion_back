package nnu.ogms.data_conversion_back.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author mingyuan
 * @Date 2020.10.20 19:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JsonResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code = 0;
    private String msg = "success";
    private T data;
}
