package com.uvan.common;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author easter
 * @data 2018/11/12 9:53
 */
public class DateUtil extends org.apache.commons.lang3.time.DateUtils{
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM.dd HH", "yyyy.MM",
            "yyyy年MM月dd日", "yyyy年MM月dd日 HH时mm分ss秒", "yyyy年MM月dd日 HH时mm分", "yyyy年MM月dd日 HH时", "yyyy年MM月",
            "yyyy"};

    /**
     * 得到日期字符串 ，转换格式（yyyy-MM-dd）
     */
    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(long dateTime, String pattern) {
        return formatDate(new Date(dateTime), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, String pattern) {
        String formatDate = null;
        if (date != null){
//			if (StringUtils.isNotBlank(pattern)) {
//				formatDate = DateFormatUtils.format(date, pattern);
//			} else {
//				formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
//			}
            if (StringUtils.isBlank(pattern)) {
                pattern = "yyyy-MM-dd";
            }
            formatDate = FastDateFormat.getInstance(pattern).format(date);
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
//		return DateFormatUtils.format(new Date(), pattern);
        return FastDateFormat.getInstance(pattern).format(new Date());
    }

    /**
     * 得到当前日期前后多少天，月，年的日期字符串
     * @param pattern 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     * @param amont 数量，前为负数，后为正数
     * @param type 类型，可参考Calendar的常量(如：Calendar.HOUR、Calendar.MINUTE、Calendar.SECOND)
     * @return
     */
    public static String getDate(String pattern, int amont, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(type, amont);
//		return DateFormatUtils.format(calendar.getTime(), pattern);
        return FastDateFormat.getInstance(pattern).format(calendar.getTime());
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式   see to DateUtils#parsePatterns
     */
    public static Date parseDate(Object str) {
        if (str == null){
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = System.currentTimeMillis()-date.getTime();
        return t/(24*60*60*1000);
    }

    /**
     * 获取过去的小时
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = System.currentTimeMillis()-date.getTime();
        return t/(60*60*1000);
    }

    /**
     * 获取过去的分钟
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = System.currentTimeMillis()-date.getTime();
        return t/(60*1000);
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取某月有几天
     * @param date 日期
     * @return 天数
     */
    public static int getMonthHasDays(Date date){
//		String yyyyMM = new SimpleDateFormat("yyyyMM").format(date);
        String yyyyMM = FastDateFormat.getInstance("yyyyMM").format(date);
        String year = yyyyMM.substring(0, 4);
        String month = yyyyMM.substring(4, 6);
        String day31 = ",01,03,05,07,08,10,12,";
        String day30 = "04,06,09,11";
        int day = 0;
        if (day31.contains(month)) {
            day = 31;
        } else if (day30.contains(month)) {
            day = 30;
        } else {
            int y = Integer.parseInt(year);
            if ((y % 4 == 0 && (y % 100 != 0)) || y % 400 == 0) {
                day = 29;
            } else {
                day = 28;
            }
        }
        return day;
    }

    /**
     * 获取日期是当年的第几周
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取一天的开始时间（如：2015-11-3 00:00:00.000）
     * @param date 日期
     * @return
     */
    public static Date getOfDayFirst(Date date) {
        if (date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取一天的最后时间（如：2015-11-3 23:59:59.999）
     * @param date 日期
     * @return
     */
    public static Date getOfDayLast(Date date) {
        if (date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取服务器启动时间
     * @param
     * @return
     */
    public static Date getServerStartDate(){
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 格式化为日期范围字符串
     * @param beginDate 2018-01-01
     * @param endDate 2018-01-31
     * @return 2018-01-01 ~ 2018-01-31
     * @author ThinkGem
     */
    public static String formatDateBetweenString(Date beginDate, Date endDate){
        String begin = DateUtil.formatDate(beginDate);
        String end = DateUtil.formatDate(endDate);
        if (StringUtils.isNoneBlank(begin, end)){
            return begin + " ~ " + end;
        }
        return null;
    }

    /**
     * 解析日期范围字符串为日期对象
     * @param dateString 2018-01-01 ~ 2018-01-31
     * @return new Date[]{2018-01-01, 2018-01-31}
     * @author ThinkGem
     */
    public static Date[] parseDateBetweenString(String dateString){
        Date beginDate = null; Date endDate = null;
        if (StringUtils.isNotBlank(dateString)){
            String[] ss = StringUtils.split(dateString, "~");
            if (ss != null && ss.length == 2){
                String begin = StringUtils.trim(ss[0]);
                String end = StringUtils.trim(ss[1]);
                if (StringUtils.isNoneBlank(begin, end)){
                    beginDate = DateUtil.parseDate(begin);
                    endDate = DateUtil.parseDate(end);
                }
            }
        }
        return new Date[]{beginDate, endDate};
    }

    /**
     * 根据时间类型获取不同时间类型的开始时间  ,在写sql的时候用<,不要用<=
     * @param type 月季度年,自己选  2:月 3:季度 4:年
     * @param monthStartTime 前端传个月份第一天的时间 ,格式为:"2018-11-01 00:00:00"
     * @return
     */
    public static Date getStartDateByMonthStartTime(int type,  String monthStartTime)throws Exception{
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String year = monthStartTime.substring(0, 5);
        Date startTime = null;
        if (type == 1) {
            //startTime = TimeUtils.getBeginDayOfWeek();   星期就不用这个选项了
        } else if (type == 2) {
            startTime = new Timestamp(longSdf.parse(monthStartTime).getTime());
        } else if (type == 3) {
            int month = Integer.parseInt(monthStartTime.substring(5, 7)); //得到第几月
            int i;  //第几季度
            if (month / 3 == 0) {
                i = month / 3;
            } else {
                i = month / 3 + 1;
            }
            if (i == 1) {
                startTime = new Timestamp(longSdf.parse(year + "01-01 00:00:00").getTime());
            } else if (i == 2) {
                startTime = new Timestamp(longSdf.parse(year + "04-01 00:00:00").getTime());
            } else if (i == 3) {
                startTime = new Timestamp(longSdf.parse(year + "07-01 00:00:00").getTime());
            } else if (i == 4) {
                startTime = new Timestamp(longSdf.parse(year + "10-01 00:00:00").getTime());
            }
        } else if (type == 4) {
            Integer thisYear = Integer.parseInt(monthStartTime.substring(0, 4));
            startTime = new Timestamp(longSdf.parse(thisYear + "-01-01 00:00:00").getTime());
        }
        return startTime;
    }


    /**
     * 根据时间类型获取不同时间类型的结束时间
     * @param type 月季度年,自己选  2:月 3:季度 4:年
     * @param monthStartTime 前端传个月份第一天的时间 ,格式为:"2018-11-01 00:00:00"
     * @return
     */
    public static Date getEndDateByMonthStartTime(int type,  String monthStartTime)throws Exception{
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String year = monthStartTime.substring(0, 5);
        Date endTime = null;
        if (type == 1) {
            //  星期就不用这个选项了
        } else if (type == 2) {
            //获取下个月时间
            int nextMonth = Integer.parseInt(monthStartTime.substring(5, 7)) + 1;
            //如果是12月
            if (nextMonth == 13) {
                endTime = new Timestamp(longSdf.parse(Integer.parseInt(monthStartTime.substring(0, 4)) + 1 + "-01-01 00:00:00").getTime());
            } else {
                endTime = new Timestamp(longSdf.parse(year + nextMonth + "-01 00:00:00").getTime());
            }
        } else if (type == 3) {
            int month = Integer.parseInt(monthStartTime.substring(5, 7)); //得到第几月
            int i;  //第几季度
            if (month / 3 == 0) {
                i = month / 3;
            } else {
                i = month / 3 + 1;
            }
            if (i == 1) {
                endTime = new Timestamp(longSdf.parse(year + "04-01 00:00:00").getTime());
            } else if (i == 2) {
                endTime = new Timestamp(longSdf.parse(year + "07-01 00:00:00").getTime());
            } else if (i == 3) {
                endTime = new Timestamp(longSdf.parse(year + "10-01 00:00:00").getTime());
            } else if (i == 4) {
                endTime = new Timestamp(longSdf.parse(year + "12-31 12:59:59").getTime());
            }
        } else if (type == 4) {
            //下一年年初
            Integer thisYear = Integer.parseInt(monthStartTime.substring(0, 4)) + 1;
            endTime = new Timestamp(longSdf.parse(thisYear + "-01-01 00:00:00").getTime());
        }
        return endTime;
    }

    /**
     * 校验日期YYYY-MM-DD格式是否正确
     *
     * @param date
     * @return
     */
    public static boolean checkDateForm(String date) {
        if (date == null || "".equals(date))
            return false;
        String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        return Pattern.compile(eL).matcher(date).matches();
    }

    /**
     * 取得指定天数后的时间
     *
     * @param date 基准时间
     * @param dayAmount 指定天数，允许为负数
     * @return 指定天数后的时间
     */
    public static Date addDay(Date date, int dayAmount) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dayAmount);
        return calendar.getTime();
    }

    /**
     * 取得指定分钟数后的时间
     *
     * @param date 基准时间
     * @param minuteAmount 指定分钟数，允许为负数
     * @return 指定分钟数后的时间
     */
    public static Date addMinute(Date date, int minuteAmount) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minuteAmount);
        return calendar.getTime();
    }


}
