package 模块信息收集;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "Response")
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 4191302896964922821L;

    @ApiModelProperty(value = "返回代码", position = 1)
    private String code;

    @ApiModelProperty(value = "返回信息", position = 2)
    private String msg;

    @ApiModelProperty(value = "结果集", position = 3, allowEmptyValue = true)
    private T data;

    private BaseResponse() {

    }

    private BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private BaseResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseResponse<T> error() {
        return error(MsgCode.MSG_ERROR.getCode(), "系统内部错误");
    }


    public static <T> BaseResponse<T> error(String msg) {
        return error(MsgCode.MSG_ERROR.getCode(), msg);
    }

    public static <T> BaseResponse<T> error(String code, String msg) {
        return new BaseResponse<T>(code, msg);
    }

    public static <T> BaseResponse<T> error(String code, String msg, T data) {
        return new BaseResponse<T>(code, msg, data);
    }

    public static <T> BaseResponse<T> success() {
        return success("成功", null);
    }

    public static <T> BaseResponse<T> success(T data) {
        return success("成功", data);
    }

    public static <T> BaseResponse<T> success(String msg) {
        return success(msg, null);
    }

    public static <T> BaseResponse<T> success(String msg, T data) {
        return new BaseResponse<T>(MsgCode.MSG_SUCCESS.getCode(), msg, data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}