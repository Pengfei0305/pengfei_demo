package com.pengfei.sell.Exception;

import com.pengfei.sell.Enum.ResultEnum;

/**
 * @author Pengfei
 * @date 2019-10-19 14:46
 */
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public SellException(ResultEnum resultEnum,String message){
        super(message);

        this.code = resultEnum.getCode();
    }

}
