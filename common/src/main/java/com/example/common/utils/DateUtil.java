package com.example.common.utils;

import com.example.common.exception.ServiceException;

import java.text.SimpleDateFormat;
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
}
