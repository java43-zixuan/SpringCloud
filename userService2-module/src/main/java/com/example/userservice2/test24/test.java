package com.example.userservice2.test24;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Java对象与JSON字符串的互转
 */
public class test {


    public static void main(String[] args) {
        List<FieldDetectorRecord> fieldDetectorRecordList = new ArrayList<>();
        FieldDetectorRecord a = new FieldDetectorRecord();
        a.setAlarmType("type");
        a.setBigImageUrl("asdasdasdas/asd123131/hashiahidha");
        a.setId("asdasd");
        fieldDetectorRecordList.add(a);
        String json = JSONObject.toJSONString(fieldDetectorRecordList);
        System.out.println(json);

        JSONArray jsonArray = JSONObject.parseArray(json);
        List<FieldDetectorRecord> asd = jsonArray.toJavaList(FieldDetectorRecord.class);
        FieldDetectorRecord qqq = asd.get(0);
    }

}
