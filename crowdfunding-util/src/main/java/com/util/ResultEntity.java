package com.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qiu
 * @create 2021-12-24 12:05
 *
 * 用于统一项目中所有Ajax请求的返回值类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity<T> {
    private String operationResult;
    private String operationMessage;
    private T data;

    // operationResult
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILE = "FAILE";
    // operationMessage
    public static final String NO_MESSAGE = "NO_MESSAGE";
    public static final String NO_DATA = "NO_DATA";

    /**
     * 返回操作结果，成功，不带数据
     * @param <E> 泛型
     * @return
     */
    public static <E> ResultEntity<E> successWithoutData(){
        return new ResultEntity<E>(SUCCESS,NO_MESSAGE,null);
    }

    /**
     * 返回操作结果，成功，携带数据
     * @param data 请求的数据
     * @param <E> 数据类型
     * @return
     */
    public static <E> ResultEntity<E> successWithData(E data){
        return new ResultEntity<E>(SUCCESS,NO_MESSAGE,data);
    }

    /**
     * 返回操作结果，错误
     * @param message
     * @param <E>
     * @return
     */
    public static <E> ResultEntity<E> fail(String message){
        return new ResultEntity<E>(FAILE,message,null);
    }
}
