package cn.zn.smart.campus.manage.web.result;

import org.slf4j.MDC;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangnan
 * @Date: 2021/05/20 21:36
 */

public class Result<T> implements Serializable {
    private static final long serialVersionUID = -682057459960312229L;

    private boolean isSuccess;
    private String code;
    private String msg;
    private String requestId;
    private T data;

    public Result(T data) {
        this.requestId = MDC.get("traceID");
        this.isSuccess = true;
        this.data = data;
        this.code = "SC-0000";
        this.msg = "success";
    }

    public Result(boolean isSuccess, String code, String msg) {
        this.requestId = MDC.get("traceID");
        this.isSuccess = isSuccess;
        this.code = code;
        this.msg = msg;
    }

    public Result(boolean isSuccess, String code, String msg, T data) {
        this.requestId = MDC.get("traceID");
        this.isSuccess = isSuccess;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> succeed(){
        return new Result<T>(true,"SC-0000","success");
    }

    public static <T> Result<T> succeed(T data){
        return new Result<T>(data);
    }

    public static <T> Result succeed(String field, T data) {
        Map<String, T> params = new HashMap(1);
        params.put(field, data);
        return new Result(params);
    }

    public static <T> Result<T> fail(String code,String msg){
        return new Result<T>(false,code,msg);
    }

    public static <T> Result<T> fail() {
        return new Result<T>(false,"","fail");
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public Result<T> setSuccess(boolean success) {
        isSuccess = success;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Result<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getRequestId() {
        return requestId;
    }

    public Result<T> setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }
}
