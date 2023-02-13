package com.example.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


/**
 * FastJSON的工具类
 * @author web
 */
public class FastJsonUtil {
	public static final String DATA_STYLE1 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATA_STYLE2 = "yyyy/MM/dd HH:mm:ss";
	public static final String SHORT_DATE_STYLE1 = "yyyy-MM-dd";
	public static final String SHORT_DATE_STYLE2 = "yyyy/MM/dd";

	/**
	 * FastJson序列化特性
	 */
	static SerializerFeature[] SERIALIZER_FEATURE = {
			//SerializerFeature.PrettyFormat,		//格式化
			//SerializerFeature.WriteNullStringAsEmpty,//String为null时输出""
			//使用dataFormat输出日期类型
			SerializerFeature.WriteDateUseDateFormat,
			//使用枚举的toString方法输出枚举值
			SerializerFeature.WriteEnumUsingToString
	};


	/**
	 * 将对象序列化成json串
	 * @param object 输入对象
	 * @return Json字符串
	 */
	public static String toJSONString(Object object) {
		return JSONObject.toJSONStringWithDateFormat(object, DATA_STYLE1, SERIALIZER_FEATURE);
	}

	/**
	 * 将对象序列化成json串后输出到OutputStream
	 * @param out 数据流
	 * @param object 输出对象
	 * @return 字节数
	 * @throws IOException
	 */
	public static int writeJSONString(OutputStream out, Object object) throws IOException {
		return JSONObject.writeJSONString(out, object, SERIALIZER_FEATURE);
	}

	/**
	 * 将JSON字符串反序列化成clazz类
	 * @param json 字符串
	 * @param clazz 目标类class
	 * @return 目标类对象
	 */
	 public static <T> T parseObject(String json, Class<T> clazz) {
        return JSONObject.parseObject(json, clazz);
    }

	/**
	 * 将JSON字符串反序列化成clazz列表
	 * @param text
	 * @return
	 */
	 public static <T> List<T> parseArray(String text, Class<T> clazz) {
		return JSONObject.parseArray(text, clazz);
    }
	
	/**
	 * 将JSON字符串反序列化成JSONObject
	 * @param text
	 * @return
	 */
	public static JSONObject parseObject(String text) {
		return JSONObject.parseObject(text);
    }

	/**
	 * 将JSON字符串反序列化成JSONArray
	 * 
	 * @param text
	 * @return
	 */
	public static JSONArray parseArray(String text) {
		return JSONObject.parseArray(text);
    }

	
	/**
	 * 将JavaBean转换为JSONObject或者JSONArray
	 * @param javaObject
	 * @return
	 */
	public static Object toJSON(Object javaObject) {
		return JSONObject.toJSON(javaObject);
	}

}
