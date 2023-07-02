package com.atguigu.edu.util;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/5/26 3:09
 */

public class ResultEntity<T> {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAILD = "FIALD";

    private static final String NO_MESSAGE = "NO_MESSAGE";
    private static final String NO_DATA = "NO_DATA";

    //结果
    private String result;
    //消息
    private String message;
    //数据
    private T data;

    public ResultEntity() {
    }

    public ResultEntity(String result, String message, T data) {
        super();
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    //返回成功带数据
    public static <E> ResultEntity<E> successWithData(E data) {
        return new ResultEntity<E>(SUCCESS,NO_MESSAGE, data);
    }

    //返回成功不带数据
    public static <E> ResultEntity<E> successWithOutData() {
        return new ResultEntity<E>(SUCCESS,NO_MESSAGE, null);
    }

    //失败
    //返回成功带数据
    public static <E> ResultEntity<E> failed(String message) {
        return new ResultEntity<E>(FAILD,message, null);
    }

    //返回成功不带数据带消息
    public static <E> ResultEntity<E> successWithOutDataWithMessage(String message) {
        return new ResultEntity<E>(SUCCESS,message, null);
    }
}
