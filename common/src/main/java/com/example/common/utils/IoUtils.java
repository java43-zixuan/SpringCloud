package com.example.common.utils;



import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author wt
 * @version 1.0
 * @date 2018/1/10
 * @modify
 * @copyright
 */
@Slf4j
public final class IoUtils {


    public static void close(final Closeable... streams) {
        try {
            for (Closeable io : streams) {
                if (io != null) {
                    io.close();
                }
            }
        } catch (IOException ioe) {
            log.error("IoUtils#close is error", ioe);
        }
    }

    private IoUtils() {

    }
}
