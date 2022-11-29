package com.example.userservice1.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangyu on 2016/12/12.
 */
public interface IRedisService {
    /**
     * 存储json格式数据(普通对象类型)
     *
     * @param key
     * @param value
     * @throws JsonProcessingException
     */
    void saveObjectToJson(String key, Object value)
            throws JsonProcessingException;

    /**
     * 存储为hash存储结构(普通对象类型)
     *
     * @param key1
     * @param key2
     * @param value
     */
    void saveObjectToJson(final String key1, final String key2, Object value)
            throws JsonProcessingException;

    /**
     * 插入key并设置有效期(普通对象类型)
     *
     * @param key      键
     * @param value    值
     * @param liveTime 有效期 毫秒
     */

    void saveObjectToJson(String key, Object value, long liveTime)
            throws JsonProcessingException;

    /**
     * byte[] 存储为String类型数据
     *
     * @param key
     * @param value
     */
    void saveByteToString(String key, byte[] value);

    /**
     * byte[] 存储为hash存储结构
     *
     * @param key1
     * @param key2
     * @param value
     */
    void saveByteToString(final String key1, final String key2, final byte[] value)
            throws JsonProcessingException;

    /**
     * 获取json类型数据(普通对象类型)
     *
     * @param key
     * @param elementType
     * @throws JsonProcessingException
     */
    <T> T getJson(String key, Class<T> elementType);

    /**
     * 获取hash存储结构的一个元素(普通对象类型)
     *
     * @param key1
     * @param key2
     * @return
     */
    <T> T getJson(final String key1, final String key2, Class<T> elementType);

    /**
     * 获取json类型数据(普通对象类型),并刷新过期时间
     *
     * @param key
     * @param elementType
     * @throws JsonProcessingException
     */
    <T> T getJsonAndRefreshExpTime(String key, Class<T> elementType, long liveTime) throws JsonProcessingException;

    /**
     * 获取hash存储结构的一个元素集合(普通对象类型)
     *
     * @param key
     * @param elementType
     * @return
     */
    <T> List<T> getAllListJson(final String key, Class<T> elementType);

    /**
     * 获取hash存储结构的一个元素Map(普通对象类型)
     *
     * @param key
     * @param elementType
     * @return
     */
    <T> Map<String, T> getAllMapJson(final String key, Class<T> elementType);

    /**
     * 获取String类型数据( byte[]类型)
     *
     * @param key
     * @return
     */
    byte[] getByte(String key);

    /**
     * 获取hash存储结构的一个元素( byte[]类型)
     *
     * @param key1
     * @param key2
     * @return
     */
    byte[] getByte(final String key1, final String key2);

    /**
     * 获取hash存储结构的所有集合( byte[]类型)
     *
     * @param key
     * @return
     */
    List<byte[]> getAllListByte(final String key);

    /**
     * 获取hash存储结构的所有Map( byte[]类型)
     *
     * @param key
     * @return
     */
    Map<String, byte[]> getAllMapByte(final String key);

    /**
     * 按照key删除
     *
     * @param key
     */
    void del(String key);

    /**
     * 删除hash存储结构
     *
     * @param key1
     * @param key2
     */
    void del(final String key1, final String key2);

    /**
     * 模糊获取所有key
     *
     * @param key
     * @return
     */
    Set<String> getKeys(String key);

    /**
     * 模糊删除所有key
     *
     * @param keys
     */
    void delKeys(String keys);


    /**
     * 获取json类型数据(普通对象类型)
     */
    Boolean hasKey(String key);

    /**
     * 获取String类型数据( String类型)
     * @param key
     * @return
     */
    String getKey(String key);

    /**
     * 存储String类型数据( String类型)
     * @param key
     * @return
     */
    void setKey(String key,String value);

}
