package com.example.common.utils;

import com.example.common.exception.ServiceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    public static final String DATE_STR_TIME = "yyyy-MM-dd hh:mm:ss";
    public static final String DATE_STR = "yyyy-MM-dd";
    /**
     * 计算日期的年份（短形式）20190501 -> 19
     *
     * @param date yyyyMMdd
     * @return yy
     */
    public static int getShortYear(int date) {
        return date / 10000 % 100;
    }

    /**
     * 将跨年的时间区间 分割成数个不跨年的区间集合
     *
     * @param startDate 开始日期yyyyMMdd
     * @param endDate   结束日期yyyyMMdd
     * @return [startDateY1, endDateY1], [startDateY2, endDateY2]...
     */
    public static int[][] splitByYear(int startDate, int endDate) {
        // 参数检查
        if (String.valueOf(startDate).length() != 8 || String.valueOf(endDate).length() != 8) {
            throw new ServiceException("日期格式不正确");
        }
        if (startDate > endDate) {
            throw new ServiceException("开始日期不能大于结束日期");
        }
        int startYear = startDate / 10000;
        int endYear = endDate / 10000;
        int[][] result = new int[endYear - startYear + 1][2];
        int s, e;
        for (int y = startYear, i = 0; y <= endYear; y++, i++) {
            // start
            if (y == startYear) {
                s = startDate;
            } else {
                // 1月1日
                s = y * 10000 + 101;
            }
            // end
            if (y == endYear) {
                e = endDate;
            } else {
                // 12月31日
                e = y * 10000 + 1231;
            }
            result[i] = new int[]{s, e};
        }
        return result;
    }

    /**时间转时间戳*/
    public static long getTimeByDate(String date,String patten)throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patten);
        return simpleDateFormat.parse(date).getTime();
    }

    public static Boolean lg72Hours(Date date1) {
        long nh = 3600000;
        // 获得两个时间的毫秒时间差异
        long diff =  System.currentTimeMillis()-date1.getTime();
        // 计算差多少小时
        long hour = diff / nh;
        return hour>=72;
    }


    public static Boolean lg72Hour(Date date) {
        long nh = 3600000;
        // 获得两个时间的毫秒时间差异
        long diff =  new Date().getTime()-date.getTime();
        // 计算差多少小时
        long hour = diff / nh;
        return hour>=72 && hour%72 ==0;
    }


    /**
     * 计算两个时间差并返回（hh:mm:ss）
     **/
    public static String betweenTime(Date start,Date end) {
            long between=(end.getTime()-start.getTime())/1000;//除以1000是为了转换成秒
            long hour=between/3600;
            long minute=between%3600/60;
            long second=between%60;

            int hour1 = Integer.parseInt(String.valueOf(hour));
            int minute1 = Integer.parseInt(String.valueOf(minute));
            int second2 = Integer.parseInt(String.valueOf(second));

        return (hour1 >10?hour:"0"+hour)+"h"+ (minute1>10?minute:"0"+minute)+"m"+(second2>10?second:"0"+second)+"s";
    }



    /**时间转字符串*/
    public static String getStrDateByTime(long time, String str) {
        try {
            // 初始化时间
            Date date = new Date(time);
            // 要格式化的时间格式
            SimpleDateFormat format = new SimpleDateFormat(str);
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 计算与系统日期相差多少分钟
     *
     * @param startTime
     * @return
     */
    public static Long dateDiff(long startTime) {
        long diff =  System.currentTimeMillis() - startTime;
        return diff / 1000 / 60;// 计算差多少分钟
    }


    /**
     * 将LocalDateTime转为自定义的时间格式的字符串
     */
    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }
    /**
     * 将long类型的timestamp转为LocalDateTime
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }
    /**
     * 将LocalDateTime转为long类型的timestamp
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }
    /**
     * 将某时间字符串转为自定义时间格式的LocalDateTime
     */
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    /**
     * 字符串转日期
     */

    public static String getDateAsString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static Date getDateAsString(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        System.out.println(date);
        return date;
    }







    public static void main(String[] args) {
        LocalDateTime ld2 = LocalDateTime.of(2019, 2, 19, 11, 19, 59);
        System.out.println(ld2);//2019-02-19T11:19:59

        LocalDateTime ld3 = ld2.plusYears(2);//在当前的日期上在加两年
        System.out.println(ld3);//2021-02-19T11:19:59

        LocalDateTime ld4 = ld2.plusMonths(2);//在当前的日期上在加个月
        System.out.println(ld4);//2019-04-19T11:19:59

        LocalDateTime ld5 = ld2.plusWeeks(2);//在当前的日期上在加周  （2月28天 在加2周14天）
        //ld2.plusDays(14);  //相当于
        System.out.println(ld5);//2019-03-05T11:19:59

        LocalDateTime ld6 = ld2.plusDays(2);//在当前的日期上在加两天
        System.out.println(ld6);//2019-02-21T11:19:59

        LocalDateTime ld7 = ld2.plusHours(2);//在当前的日期上在加个小时
        System.out.println(ld7);//2019-02-19T13:19:59



        LocalDateTime ld8 = ld2.minusYears(2);//在当前的日期上在减两年
        System.out.println(ld8);//2017-02-19T11:19:59

        LocalDateTime ld9 = ld2.minusMonths(2);//在当前的日期上在减两个月
        System.out.println(ld9);//2018-12-19T11:19:59

        LocalDateTime ld10 = ld2.minusWeeks(2);//在当前的日期上在减两周
        System.out.println(ld10);//2019-02-05T11:19:59

        LocalDateTime ld11 = ld2.minusDays(2);//在当前的日期上在减两天
        System.out.println(ld11);//2019-02-17T11:19:59

        LocalDateTime ld12 = ld2.minusHours(2);//在当前的日期上在减两个小时
        System.out.println(ld12);//2019-02-19T09:19:59

        LocalDateTime ld13 = ld2.minusMinutes(2);//在当前的日期上在减两分钟
        System.out.println(ld13);//2019-02-19T11:17:59

        LocalDateTime ld14 = ld2.minusSeconds(2);//在当前的日期上在减两秒
        System.out.println(ld14);//2019-02-19T11:19:57

        LocalDateTime ld15 = ld2.minusNanos(2);//在当前的日期上在减两纳秒
        System.out.println(ld15);//2019-02-19T11:19:58.999999998
    }

}
