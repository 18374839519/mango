package com.louis.mango.common.utils.http;

/**
 * 返回结果封装（HttpResult封装的太烂了，我要重新写）
 */
public class HttpResultUtils {

    // 成功
    public static HttpResult success() {
        HttpResult httpResult = new HttpResult();
        httpResult.setCode(200);
        httpResult.setMsg("成功");
        return httpResult;
    }

    public static HttpResult success(Object data) {
        HttpResult httpResult = new HttpResult();
        httpResult.setCode(200);
        httpResult.setMsg("成功");
        httpResult.setData(data);
        return httpResult;
    }


    // 失败
    public static HttpResult error(int code, String msg) {
        HttpResult httpResult = new HttpResult();
        httpResult.setCode(code);
        httpResult.setMsg(msg);
        return httpResult;
    }

    public static HttpResult error() {
        HttpResult httpResult = new HttpResult();
        httpResult.setCode(500);
        httpResult.setMsg("错误");
        return httpResult;
    }

}
