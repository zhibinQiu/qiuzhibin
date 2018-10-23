package com.mall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)   //不要NULL，保证序列化
public class ServerResponse<T> implements Serializable {                 //实现序列化接口
    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status){
        this.status=status;
    }
    private ServerResponse(int status,T data){
        this.status=status;
        this.data=data;
    }
    private ServerResponse(int status,String msg,T data){
        this.status=status;
        this.data=data;
        this.msg=msg;
    }
    private ServerResponse(int status,String msg){
        this.status=status;
        this.msg=msg;


    }
    @JsonIgnore
    //是之不在序列化
    public boolean isSuccess(){
        return this.status==ResponseCode.SUCCESS.getCode();
    }
    public int getStatus(){
        return status;
    }
    public T getData(){
        return data;
    }
    public String getMsg(){
        return msg;
    }
    public static <T> ServerResponse createBySuccess(){
        return new ServerResponse(ResponseCode.SUCCESS.getCode());
    }
    public static <T> ServerResponse createBySuucessMessage(String msg){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg);
    }
    public static <T> ServerResponse createBySuccess(T data){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),data);
    }
    public static <T> ServerResponse createBySuccess(String msg,T data){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg,data);
    }
    public static <T> ServerResponse createByError(){
        return new ServerResponse(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }
    public static <T> ServerResponse createByErrorMessage(String errorMessage){
        return new ServerResponse(ResponseCode.ERROR.getCode(),errorMessage);
    }
    public static <T> ServerResponse createByError(int errorCode,String errorMessage){
        return new ServerResponse(errorCode,errorMessage);
    }
}
