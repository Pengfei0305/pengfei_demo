package com.pengfei.sell.VO;

import lombok.Data;

/**
 *
 * @author Pengfei
 * @date 2019-10-13 21:46
 */
@Data
public class ResultVO<T> {

    /**
     *
     */
    private Integer code;

    private String msg;

    private T date;

}
