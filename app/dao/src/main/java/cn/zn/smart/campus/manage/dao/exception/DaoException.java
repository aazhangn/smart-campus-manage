package cn.zn.smart.campus.manage.dao.exception;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 持久层自定义异常
 * @Author: zhangnan
 * @Date: 2021/05/14 20:17
 */
@Data
public class DaoException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1482498248640632533L;
    private String code;
    private String msg;

    public DaoException() {}

    public DaoException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public DaoException(ExceEnum exceEnum){
        this.code = exceEnum.getCode();
        this.msg = exceEnum.getMsg();
    }
}
