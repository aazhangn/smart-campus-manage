package cn.zn.smart.campus.manage.biz.exception;

/**
 * @Description: 自定义业务异常
 * @Author: zhangnan
 * @Date: 2021/05/13 23:43
 */
public class BizException extends RuntimeException{
    private static final long serialVersionUID = -8298930668229795484L;

    private String code;
    private String msg;

    public BizException() {}

    public BizException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BizException(ErrorEnum errorEnum){
        this.code = errorEnum.getCode();
        this.msg = errorEnum.getMsg();

    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
