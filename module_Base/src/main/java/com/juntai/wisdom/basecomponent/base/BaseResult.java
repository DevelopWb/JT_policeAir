package com.juntai.wisdom.basecomponent.base;

public class BaseResult  {


    public int status;
    public String message;
    public String error;
    public String msg;//已弃用
    public int code;
    public String type;
    public boolean success;



    @Override
    public String toString() {
        return "BaseResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                ", type='" + type + '\'' +
                ", success=" + success +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message == null ? "" : message;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? "" : msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error == null ? "" : error;
    }

    public void setError(String error) {
        this.error = error == null ? "" : error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
