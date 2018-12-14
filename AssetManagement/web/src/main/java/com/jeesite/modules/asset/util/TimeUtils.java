package com.jeesite.modules.asset.util;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by tony on 2017/7/19.
 */
public class TimeUtils {

    //获取一月的所有时间
    public static List<Date> getAllTheDateOftheMonth() {
        Date date = new Date();
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        int month = cal.get(Calendar.MONTH);
        while (cal.get(Calendar.MONTH) == month) {
            list.add(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }
        return list;
    }

    //获取一月的所有时间
    public static List<String> getAllDateOftheMonth() {
        Date date = new Date();
        List<String> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        int month = cal.get(Calendar.MONTH);
        while (cal.get(Calendar.MONTH) == month) {
            list.add(DateUtils.formatDate(cal.getTime()));
            cal.add(Calendar.DATE, 1);
        }
        return list;
    }

    //获取一周的所有时间
    public static List<Date> dateToWeek() {
        Date date = new Date();
        int b = date.getDay();
        Date fdate;
        List<Date> list = new ArrayList<Date>();
        Long fTime = date.getTime() - b * 24 * 3600000;
        for (int a = 1; a <= 7; a++) {
            fdate = new Date();
            fdate.setTime(fTime + (a * 24 * 3600000));
            list.add(a - 1, fdate);
        }
        return list;
    }

    //获取本周的开始时间
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    //获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    //获取指定月的开始时间
    public static Date getBeginDayOfMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), month - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取指定月的结束时间
    public static Date getEndDayOfMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), month - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), month - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    //获取本年的开始时间
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }

    //获取本年的结束时间
    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    //获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    //根据日期取得星期几
    public static String getWeek(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    //根据指定日期获取该月的指定开始时间
    public static Date getBeginOfMonthByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYearByData(date), getNowMonthByData(date) - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    //根据指定日期获取该月的指定结束时间
    public static Date getEndDayOfMonthByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYearByData(date), getNowMonthByData(date) - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYearByData(date), getNowMonthByData(date) - 1, day);
        return getDayEndTime(calendar.getTime());
    }


    //获取本月是哪一月
    public static int getNowMonthByData(Date date) {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    //获取今年是哪一年
    public static Integer getNowYearByData(Date date) {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    //获取上一个月的开始时间
    public static Date getLastMonthendTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时yy分mm秒");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        date = calendar.getTime();
        date = TimeUtils.getBeginOfMonthByDate(date);
        return date;
    }

    //获取上一个月的结束时间
    public static Date getLastMonthBeginTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时yy分mm秒");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        date = calendar.getTime();
        date = TimeUtils.getEndDayOfMonthByDate(date);
        return date;
    }

    //获取某个日期的8.30
    public static Date getTimeFromData(Date d, int houeTime, int minuteTime) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), houeTime, minuteTime, 00);
//        calendar.set(Calendar.MILLISECOND, 999);
        Date date = new Date();
        date = calendar.getTime();
        return date;
    }

    //获取日期前一天的18.30
    public static Date getTimeFromDeforData(Date d, int houeTime, int minuteTime) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) - 1, houeTime, minuteTime, 00);
//        calendar.set(Calendar.MILLISECOND, 999);
        Date date = new Date();
        date = calendar.getTime();
        return date;
    }


    /**
     * 获取当前季度 *
     * 以每年阴历的1～3月为春季，4～6月为夏季，7～9月为秋季，10～12月为冬季
     */
    public static String getQuarter() {
        Calendar c = Calendar.getInstance();
        int month = c.get(c.MONTH) + 1;
        int quarter = 0;
        if (month >= 1 && month <= 3) {
            quarter = 1;
        } else if (month >= 4 && month <= 6) {
            quarter = 2;
        } else if (month >= 7 && month <= 9) {
            quarter = 3;
        } else {
            quarter = 4;
        }
        return quarter + "";
    }

    /**
     * 获取某季度的第一天和最后一天 *	@param num第几季度
     */
    public static String[] getCurrQuarter(int num) {
        String[] s = new String[2];
        String str = "";
        // 设置本年的季
        Calendar quarterCalendar = null;
        switch (num) {
            case 1:
                // 本年到现在经过了一个季度，在加上前4个季度
                quarterCalendar = Calendar.getInstance();
                quarterCalendar.set(Calendar.MONTH, 3);
                quarterCalendar.set(Calendar.DATE, 1);
                quarterCalendar.add(Calendar.DATE, -1);
                str = DateUtils.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd ");
                s[0] = str.substring(0, str.length() - 5) + "01-01";
                s[0] = s[0] + " 00:00:00";
                s[1] = str;
                s[1] = s[1] + " 23:59:59";
                break;
            case 2:
                // 本年到现在经过了二个季度，在加上前三个季度
                quarterCalendar = Calendar.getInstance();
                quarterCalendar.set(Calendar.MONTH, 6);
                quarterCalendar.set(Calendar.DATE, 1);
                quarterCalendar.add(Calendar.DATE, -1);
                str = DateUtils.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
                s[0] = str.substring(0, str.length() - 5) + "04-01";
                s[0] = s[0] + " 00:00:00";
                s[1] = str;
                s[1] = s[1] + " 23:59:59";
                break;
            case 3:
                // 本年到现在经过了三个季度，在加上前二个季度
                quarterCalendar = Calendar.getInstance();
                quarterCalendar.set(Calendar.MONTH, 9);
                quarterCalendar.set(Calendar.DATE, 1);
                quarterCalendar.add(Calendar.DATE, -1);
                str = DateUtils.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
                s[0] = str.substring(0, str.length() - 5) + "07-01";
                s[0] = s[0] + " 00:00:00";
                s[1] = str;
                s[1] = s[1] + " 23:59:59";
                break;
            case 4:
                // 本年到现在经过了四个季度，在加上前一个季度
                quarterCalendar = Calendar.getInstance();
                str = DateUtils.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
                s[0] = str.substring(0, str.length() - 5) + "10-01";
                s[0] = s[0] + " 00:00:00";
                s[1] = str.substring(0, str.length() - 5) + "12-31";
                s[1] = s[1] + " 23:59:59";
                break;
        }
        return s;
    }

    public static List<String> process(String date1, String date2){
        List<String> dateList = ListUtils.newArrayList();
        if(date1.equals(date2)){
            dateList.add(date1);
            return dateList;
        }

        String tmp;
        if(date1.compareTo(date2) >= 0){  //确保 date1的日期不晚于date2
            tmp = date1; date1 = date2; date2 = tmp;
        }

        tmp = DateUtils.formatDate(DateUtils.parseDate(date1));


        while(tmp.compareTo(date2) <= 0){
            dateList.add(tmp);
            tmp = getNextDay(DateUtils.parseDate(tmp));
        }
        return dateList;
    }

    /**
     * 获取指定日期后一天
     * @param date
     * @return
     */
    public static String getNextDay (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return DateUtils.formatDate(calendar.getTime());
    }

    /**
     * 获取指定日期前一天
     * @param date
     * @return
     */
    public static String getBeforeDay (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return DateUtils.formatDate(calendar.getTime());
    }

    /**
     * 获取两个日期之间的月份
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    public static List<String> getMonthBetween(String minDate, String maxDate){
        List<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        try{
            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        }catch (ParseException e) {

        }
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前季度为" + getQuarter());
        System.out.println("当前季度开始时间为" +longSdf.parse(getCurrQuarter(3)[0])  + "当前季度结束时间为" + getCurrQuarter(3)[1]);

    }
}
