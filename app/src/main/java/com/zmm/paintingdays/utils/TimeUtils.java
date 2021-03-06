package com.zmm.paintingdays.utils;

import java.util.Date;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/10
 * Email:65489469@qq.com
 */
public class TimeUtils {

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年


    public static String getTimeFormatText(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r;

        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }

        return "刚刚";
    }

    public static String getTimeFormatTextHistory(long time) {

        long diff = new Date().getTime() - time;
        long r;

        if (diff > year) {
            return DateUtils.longToString(time,null);
        }
        if (diff > month) {
            return DateUtils.longToString(time,null);
        }
        if (diff > day) {

            r = (diff / day);
            if(r == 1){
                return "昨天";
            }else if(r == 2){
                return "前天";
            }
            return DateUtils.longToString(time,null);
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }

        return "刚刚";
    }

    public static String getTimeFormatTextDiary(long time) {

        long diff = new Date().getTime() - time;
        long r;

        if (diff > year) {
            return DateUtils.longToString(time,null);
        }
        if (diff > month) {
            return DateUtils.longToString(time,null);
        }
        if (diff > day) {
            r = (diff / day);
            if(r == 1){
                return "昨天";
            }else if(r == 2){
                return "前天";
            }
            return DateUtils.longToString(time,null);
        }

        return "今天";
    }


}
