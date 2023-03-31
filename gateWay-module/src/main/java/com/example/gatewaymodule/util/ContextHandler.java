package com.example.gatewaymodule.util;


import org.apache.poi.ss.formula.functions.T;

/**
 * 后台当前上下文
 *
 * @author WEB
 * @version 2022-07-11
 */
public class ContextHandler {
    private static final ThreadLocal<Object> CONTEXT = new ThreadLocal<>();

    public static void setAuthKeyVo(Object value) {
        CONTEXT.set(value);
    }

    public static Object getAuthKeyVo() {
        return CONTEXT.get();
    }
    /**
     * 销毁上下文
     */
    public static void clear() {
        CONTEXT.remove();
    }
}
