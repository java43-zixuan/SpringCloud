package com.example.userservice2.test23;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * SimpleDateFormat 是线程不安全的类，一般不要定义为 static 变量，如果定义为static，必须加锁，或者使用 DateUtils 工具类。
 */
public class Commons_DateUtils {

    public static void main(String[] args) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
        Date date = new Date();
        /**
        * String转换成Date
        * arg0 : 日期字符串 String
        * arg1 : 特定的地理，政治和文化地区.可以传null
        * arg3 : 日期格式.与arg0格式一致 String
        * 该方法对日期和时间的解释是宽松的
        * 宽松的解释日期（如 1996 年 2 月 42 日）将被视为等同于 1996 年 2 月 1 日后的第 41 天
        * 如果是严格的解释，此类日期就会引发异常
        */
        Date date1 = DateUtils.parseDate("20171012 14:30:12", Locale.TRADITIONAL_CHINESE, "yyyyMMdd hh:mm:ss");
        Date date2 = DateUtils.parseDate("20171012 14:30:12", Locale.TRADITIONAL_CHINESE, "yyyyMMdd hh:mm:ss");

        /**
        * String转换成Date 严格的
        * arg0 : 日期字符串 String
        * arg1 : 特定的地理，政治和文化地区.可以传null
        * arg3 : 日期格式.与arg0格式一致 String
        * 该方法对日期和时间的解释是严格的
        */
        Date date3 = DateUtils.parseDateStrictly("20171012", Locale.TRADITIONAL_CHINESE, "yyyyMMdd");
        Date date4 = DateUtils.parseDateStrictly("20171012", Locale.TRADITIONAL_CHINESE, "yyyyMMdd");

        /**
        * 判断两个日期是否是同一天
        * arg0 arg1 数据类型 : Date Calendar
        * 比较arg0 arg1的
        * ERA = 0 年代
        * YEAR = 1 年
        * DAY_OF_YEAR = 6 年中的第几天
        */
        DateUtils.isSameDay(date3, date4);
        System.out.println("isSameDay = " + DateUtils.isSameDay(date3, date4));

        /**
        * 判断两个日期是不是同一毫秒
        * arg0 arg1 数据类型 : Date Calendar
        * 自1970年1月1日00:00:00 GMT 的毫秒数是否相等
        */
        DateUtils.isSameInstant(date1, date2);
        System.out.println("isSameInstant = " + DateUtils.isSameInstant(date1, date2));

        /**
        * 判断是否是同一个本地时间
        * arg0 arg1 数据类型 : Calendar
        * 比较arg0 arg1的
        * 数据类型
        * ERA = 0 年代
        * YEAR = 1 年
        * DAY_OF_YEAR = 6 年中的第几天
        * HOUR_OF_DAY = 11 天中的第几个小时
        * MINUTE = 12 分钟
        * SECOND = 13 秒
        * MILLISECOND = 14 毫秒
        */
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        DateUtils.isSameLocalTime(cal1, cal2);
        System.out.println("isSameLocalTime = " + DateUtils.isSameLocalTime(cal1, cal2));

        /**
        * 获取指定日期前后arg1年
        * arg0 : 指定日期 Date类型
        * arg1 : int型,正数向后天数,0当天,负数向前天数
        */
        date = DateUtils.addYears(date1, 4);
        System.out.println("addYears = " + sdf.format(date));

        /**
        * 获取指定日期前后arg1月
        * arg0 : 指定日期 Date类型
        * arg1 : int型,正数向后天数,0当天,负数向前天数
        */
        date = DateUtils.addMonths(date1, 4);
        System.out.println("addMonths = " + sdf.format(date));

        /**
        * 获取指定日期前后arg1周
        * arg0 : 指定日期 Date类型
        * arg1 : int型,正数向后天数,0当天,负数向前天数
        */
        date = DateUtils.addWeeks(date1, 4);
        System.out.println("addWeeks = " + sdf.format(date));

        /**
        * 获取指定日期前后arg1天
        * arg0 : 指定日期 Date类型
        * arg1 : int型,正数向后天数,0当天,负数向前天数
        */
        date = DateUtils.addDays(date1, 4);
        System.out.println("addDays = " + sdf.format(date));

        /**
        * 获取指定日期前后arg1小时
        * arg0 : 指定日期 Date类型
        * arg1 : int型,正数向后天数,0当天,负数向前天数
        */
        date = DateUtils.addHours(date1, 4);
        System.out.println("addHours = " + sdf.format(date));

        /**
        * 获取指定日期前后arg1分钟
        * arg0 : 指定日期 Date类型
        * arg1 : int型,正数向后天数,0当天,负数向前天数
        */
        date = DateUtils.addMinutes(date1, 4);
        System.out.println("addMinutes = " + sdf.format(date));

        /**
        * 获取指定日期前后arg1秒
        * arg0 : 指定日期 Date类型
        * arg1 : int型,正数向后天数,0当天,负数向前天数
        */
        date = DateUtils.addSeconds(date1, 4);
        System.out.println("addSeconds = " + sdf.format(date));

        /**
        * 获取指定日期前后arg1毫秒
        * arg0 : 指定日期 Date类型
        * arg1 : int型,正数向后天数,0当天,负数向前天数
        */
        date = DateUtils.addMilliseconds(date1, 4);
        System.out.println("addMilliseconds = " + sdf.format(date));

        /**
        * 指定日期年的值
        * arg0 : 日期 Date类型
        * arg1 : int型
        */
        date = DateUtils.setYears(date1, 2008);
        System.out.println("setYears = " + sdf.format(date));

        /**
        * 指定日期月的值
        * arg0 : 日期 Date类型
        * arg1 : int型 范围在 1-12
        */
        date = DateUtils.setMonths(date1, 1);
        System.out.println("setMonths = " + sdf.format(date));

        /**
        * 指定日期天的值
        * arg0 : 日期 Date类型
        * arg1 : int型 范围在 1-31(不同月份值略有不同)
        */
        date = DateUtils.setDays(date1, 24);
        System.out.println("setDays = " + sdf.format(date));

        /**
        * 指定日期小时的值
        * arg0 : 日期 Date类型
        * arg1 : int型 范围在1-23
        */
        date = DateUtils.setHours(date1, 23);
        System.out.println("setHours = " + sdf.format(date));

        /**
        * 指定日期分钟的值
        * arg0 : 日期 Date类型
        * arg1 : int型 范围在1-59
        */
        date = DateUtils.setMinutes(date1, 56);
        System.out.println("setMinutes = " + sdf.format(date));

        /**
        * 指定日期秒的值
        * arg0 : 日期 Date类型
        * arg1 : int型 范围在1-59
        */
        date = DateUtils.setSeconds(date1, 14);
        System.out.println("setMinutes = " + sdf.format(date));

        /**
        * 指定日期毫秒的值
        * arg0 : 日期 Date类型
        * arg1 : int型
        */
        date = DateUtils.setMilliseconds(date1, 100);
        System.out.println("setMinutes = " + sdf.format(date));

        /**
        * 相当于
        * Calendar cal3 = Calendar.getInstance();
        * cal3.setTime(date);
        * 得到的cal
        */
        Calendar cal3 = DateUtils.toCalendar(date1);

        /**
        * 获取时区
        * timeZone 系统默认
        * timeZone1 系统默认时区
        * timeZone2 设置时区
        */
        Calendar calendar = new GregorianCalendar();
        TimeZone timeZone = calendar.getTimeZone();
        TimeZone timeZone1 = TimeZone.getDefault();
        TimeZone timeZone2 = TimeZone.getTimeZone("Europe/Copenhagen");

        /**
        * Date 转换成 Calendar 带时区
        * arg0 : 日期 Date类型
        * arg1 : 时区
        */
        Calendar cal4 = DateUtils.toCalendar(date1, timeZone2);

        long fragment = 0;

        /**
        * 获取指定日期中从指定位置起的毫秒数
        * arg0 : 指定的日期 Date类型 或 Calendar类型
        * arg1 : 指定从什么位置开始 int类型:建议使用 Calendar.YEAR Calendar.MONTH 等常量
        */
        fragment = DateUtils.getFragmentInMilliseconds(date1, Calendar.MONDAY);
        System.out.println("getFragmentInMilliseconds = " + fragment);

        /**
        * 获取指定日期中从指定位置起的秒数
        * arg0 : 指定的日期 Date类型 或 Calendar类型
        * arg1 : 指定从什么位置开始 int类型:建议使用 Calendar.YEAR Calendar.MONTH 等常量
        */
        fragment = DateUtils.getFragmentInSeconds(date1, Calendar.MONDAY);
        System.out.println("getFragmentInSeconds = " + fragment);

        /**
        * 获取指定日期中从指定位置起的分钟数
        * arg0 : 指定的日期 Date类型 或 Calendar类型
        * arg1 : 指定从什么位置开始 int类型:建议使用 Calendar.YEAR Calendar.MONTH 等常量
        */
        fragment = DateUtils.getFragmentInMinutes(date1, Calendar.MONDAY);
        System.out.println("getFragmentInMinutes = " + fragment);

        /**
        * 获取指定日期中从指定位置起的小时数
        * arg0 : 指定的日期 Date类型 或 Calendar类型
        * arg1 : 指定从什么位置开始 int类型:建议使用 Calendar.YEAR Calendar.MONTH 等常量
        */
        fragment = DateUtils.getFragmentInHours(date1, Calendar.MONDAY);
        System.out.println("getFragmentInHours = " + fragment);

        /**
        * 获取指定日期中从指定位置起的天数
        * arg0 : 指定的日期 Date类型 或 Calendar类型
        * arg1 : 指定从什么位置开始 int类型:建议使用 Calendar.YEAR Calendar.MONTH 等常量
        */
        fragment = DateUtils.getFragmentInDays(date1, Calendar.MONDAY);
        System.out.println("getFragmentInDays = " + fragment);

        boolean isEquals = false;

        /**
        * 判断两个时间在指定的位置之上是否相等
        * arg0 : 时间1 Date类型 或 Calendar类型
        * arg1 : 时间2 Date类型 或 Calendar类型
        * arg2 : 指定在位置上开始比较 int类型:建议使用 Calendar.YEAR Calendar.MONTH 等常量
        */
        isEquals = DateUtils.truncatedEquals(date1, date2, Calendar.MONDAY);
        System.out.println("truncatedEquals = " + isEquals);

        int truncatedCompare = -1;

        /**
        * 比较arg0与arg1两个时间在指定的位置上的时间差值
        * arg0 : 时间1 Date类型 或 Calendar类型
        * arg1 : 时间2 Date类型 或 Calendar类型
        * arg2 : 指定在位置上开始比较 int类型:建议使用 Calendar.YEAR Calendar.MONTH 等常量
        */
        truncatedCompare = DateUtils.truncatedCompareTo(date1, date2, Calendar.MONDAY);
        System.out.println("truncatedCompareTo = " + truncatedCompare);
        }
}
