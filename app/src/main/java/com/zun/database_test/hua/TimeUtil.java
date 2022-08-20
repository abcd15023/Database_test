package com.zun.database_test.hua;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间转换工具类
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {
    private static final String TAG = "TimeUtil";

    private TimeUtil() {}

    //h am/pm 中的小时数（1-12）
    //H 一天中的小时数（0-23）
    //k 一天中的小时数（1-24）
    //K am/pm 中的小时数（0-11）

    public static final String FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_2 = "yyyy-MM-dd";
    public static final String FORMAT_3 = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_4 = "HH:mm:ss";
    public static final String FORMAT_5 = "HH:mm";
    public static final String FORMAT_6 = "mm:ss";
    public static final String FORMAT_7 = "MM.dd";
    public static final String FORMAT_8 = "MM";
    public static final String FORMAT_9 = "dd";
    public static final String FORMAT_10 = "yyyy-MM-dd kk:mm:ss";

    public static long getCurrentTimeLong() {
        return System.currentTimeMillis();
    }

    public static String getCurrentTimeStr() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String getFormatTime(long time, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date(time));
    }

    public static String getFormatTime(String time, String formatStr) {
        return getFormatTime(StringUtils.toLong(time), formatStr);
    }

    public static String getTime(long time) {
        return getFormatTime(time, FORMAT_1);
    }

    public static String getDate(long time) {
        return getFormatTime(time, FORMAT_2);
    }


    /**
     * 将时日期转换为时间戳
     */
    public static long formatStringToLong(String dateTime, String formatStr) {
        long mTimestamp = -1;
        if (StringUtils.isEmpty(dateTime)) {
            return -1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr, Locale.US);

        Date mDate;
        try {
            mDate = sdf.parse(dateTime);
            if (mDate != null) {
                mTimestamp = mDate.getTime();
            }
        } catch (Exception e) {
            L.e(L.LEVEL_TEST,"string date to long date error is " + e);
        }

        return mTimestamp;
    }


    /**
     * 判断当前日期是星期几
     *
     * @param time 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     */
    public static String getWeekStr(long time) {
        int weekInt = getWeekInt(time);
        String weekStr = "周";
        if (weekInt == Calendar.SUNDAY) {
            weekStr += "日";
        }
        if (weekInt == Calendar.MONDAY) {
            weekStr += "一";
        }
        if (weekInt == Calendar.TUESDAY) {
            weekStr += "二";
        }
        if (weekInt == Calendar.WEDNESDAY) {
            weekStr += "三";
        }
        if (weekInt == Calendar.THURSDAY) {
            weekStr += "四";
        }
        if (weekInt == Calendar.FRIDAY) {
            weekStr += "五";
        }
        if (weekInt == Calendar.SATURDAY) {
            weekStr += "六";
        }
        return weekStr;
    }


    public static String getWeekItem(int item) {
        String weekStr = "周";
        switch (item){
            case 0:
                weekStr += "一";
            break;
            case 1:
                weekStr += "二";
            break;
            case 2:
                weekStr += "三";
            break;
            case 3:
                weekStr += "四";
            break;
            case 4:
                weekStr += "五";
            break;
            case 5:
                weekStr += "六";
            break;
            default:
                weekStr += "日";
                break;
        }

        return weekStr;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param time 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     */

    public static int getWeekInt(long time) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(time));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 判断当前日期是星期几
     *
     * @param time 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     */

    public static int getWeekIntNormal(long time) {
        int weekInt = getWeekInt(time);
        switch (weekInt){
            case 1://周日
                return 7;
            case 2://周一
                return 1;
            case 3://周二
                return 2;
            case 4://周三
                return 3;
            case 5://周四
                return 4;
            case 6://周五
                return 5;
            default://周六
                return 6;
        }
    }

    /**
     * 将时间戳转换成 xx:xx
     */
    public static String stampToDate(long stamp) {
        String time = "";
        try {
            long hours = (stamp % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60); //小时
            long minutes = (stamp % (1000 * 60 * 60)) / (1000 * 60); //分钟
            long seconds = (stamp % (1000 * 60)) / 1000; //秒

            //时分秒，为0则无
            if (hours == 0) {
                if (minutes == 0) {
                    time = seconds + "秒";
                } else {
                    time = minutes + "分钟" + seconds + "秒";
                }
            } else if (minutes == 0) {
                if (seconds == 0) {
                    time = hours + "小时";
                } else {
                    time = hours + "小时" + seconds + "秒";
                }
            } else {
                if (seconds == 0) {
                    time = hours + "小时" + minutes + "分钟";
                } else {
                    time = hours + "小时" + minutes + "分钟" + seconds + "秒";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String stampToMinute(long stamp) {
        if (stamp <= 0) {
            return "0分钟";
        }
        String time = "";
        try {
            long hours = (stamp % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60); //小时
            long minutes = (stamp % (1000 * 60 * 60)) / (1000 * 60); //分钟

            //时分秒，为0则无
            if (hours == 0) {
                time = minutes + "分钟";
            } else if (minutes == 0) {
                time = hours + "小时";
            } else {
                time = hours + "小时" + minutes + "分钟";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将时间段转化为今天的时间点
     */
    public static long getTheSameTimeToday(long time,long currentTime){
        if (time <= 0) {
            return -1;
        }

        long hours = (time%86400000)/3600000;//(time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60); //小时
        long minutes = (time%3600000)/60000;//(time % (1000 * 60 * 60)) / (1000 * 60); //分钟

        String hourStr = String.valueOf(hours);
        String minuteStr = String.valueOf(minutes);
        if (hourStr.length() == 1) {
            hourStr = "0" + hourStr;
        }
        if (minuteStr.length() == 1) {
            minuteStr = "0" + minuteStr;
        }
        String currentDate = getFormatTime(currentTime,FORMAT_2);
        String timeStr = currentDate + " " + hourStr + ":" + minuteStr + ":00";

        return formatStringToLong(timeStr,FORMAT_1);
    }

}
