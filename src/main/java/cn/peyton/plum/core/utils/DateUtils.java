package cn.peyton.plum.core.utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <h3>日期时间工具类</h3>
 * <pre>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date:2018/11/16 15:56
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @version 1.0.0
 * </pre>
 */
@SuppressWarnings("ALL")
public final class DateUtils implements Serializable {
    //private static final String FORMAT = "yyyy-MM-dd";

    /**
     * <h4>生成 10 位 int 型的 时间戳</h4>
     * @param time 13位时间
     * @return 10位 时间戳
     */
    public static Integer dateToTimestamp(long time) {
        return _dateToTimestamp(time);
    }

    /**
     * <h4>生成 10 位 int 型的 时间戳</h4>
     * @return 10位 时间戳
     */
    public static Integer dateToTimestamp() {
        return _dateToTimestamp(System.currentTimeMillis());
    }

    /**
     * <h4>生成 10 位 int 型的 时间戳</h4>
     * @param date 时间
     * @return 10位 时间戳
     */
    public static Integer dateToTimestamp(Date date) {
        return _dateToTimestamp(date.getTime());
    }

    /**
     * <h4>生成 10 位 int 型的 时间戳</h4>
     * @param date 时间
     * @return 10位 时间戳
     */
    public static Integer dateToTimestamp(String date) {
        return _dateToTimestamp(date,null);
    }

    /**
     * <h4>生成 10 位 int 型的 时间戳</h4>
     * @param date 时间
     * @param format 格式化时间样式
     * @return 10位 时间戳
     */
    public static Integer dateToTimestamp(String date, String format) {
        return _dateToTimestamp(date,format);
    }


    /**
     * <h4>时间格式字符串</h4>
     * @param time 10位 时间戳
     * @return 时间格式字符串
     */
    public static String timestampToStrDate(Integer time) {
        if (null == time){return null;}
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT.DATE_FORMAT_TIME);
        long _timeLong = Long.valueOf(time);
        return simpleDateFormat.format(new Date(_timeLong * 1000L));
    }

    /**
     * <h4>时间格式字符串</h4>
     * @param time 13位 时间戳
     * @return 时间格式字符串
     */
    public static String timestampToStrDate(Long time) {
        if (null == time){return null;}
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT.DATE_FORMAT_TIME);
        return simpleDateFormat.format(new Date(time));
    }

    /**
     * <h4>时间格式字符串</h4>
     * @param date 时间
     * @return 时间格式字符串
     */
    public static String timestampToStrDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT.DATE_FORMAT_TIME);
        return simpleDateFormat.format(date);
    }

    /**
     * <h4>时间格式字符串</h4>
     * @param time 10位 时间戳
     * @param format 格式化时间样式
     * @return 时间格式字符串
     */
    public static String timestampToStrDate(Integer time,String format) {
        if (null == time){return null;}
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        long _timeLong = Long.valueOf(time);
        return simpleDateFormat.format(new Date(_timeLong * 1000L));
    }

    /**
     * <h4>10位时间戳转成时间格式</h4>
     * @param time 10位 时间戳
     * @return 时间
     */
    public static Date timestampToDate(Integer time){
        return new Date(time * 1000L);
    }


    /**
     * <h4>时间转字符串</h4>
     * @param date 时间
     * @return
     */
    public static String conventDate2Str(Date date) {
        return conventDate2Str(date, FORMAT.DATE_FORMAT);
    }
    /**
     * <h4>时间转字符串</h4>
     * @param date 时间
     * @param f 格式化样式
     * @return
     */
    public static String conventDate2Str(Date date,String f) {
        SimpleDateFormat format = new SimpleDateFormat(f);
        if (null != date && !"".equals(date)) {
            return format.format(date);
        }
        return null;
    }

    /**
     * <h4>字符串转时间</h4>
     * @param str 字符串
     * @param f 格式化样式 格式: yyyy-MM-dd
     * @return
     */
    public static Date conventStr2Date(String str,String f) {
        SimpleDateFormat format = new SimpleDateFormat(f);
        if (null != str && !"".equals(str.trim())) {
            try {
                Date date = format.parse(str);
                return date;
            } catch (ParseException e) {
                e.getStackTrace();
            }
        }
        return null;
    }
    /**
     * <h4>字符串转时间</h4>
     * @param str 字符串 格式: yyyy-MM-dd
     * @return
     */
    public static Date conventStr2Date(String str) {
        return conventStr2Date(str, FORMAT.DATE_FORMAT);
    }

    /**
     * <h4>生成 10 位 int 型的 时间戳</h4>
     * @param time 13位 时间戳
     * @return 10位 时间戳
     */
    private static Integer _dateToTimestamp(Long time) {
        try {
            return (int) (time/1000);
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return 0;
        }
    }

    /**
     * <h4>生成 10 位 int 型的 时间戳</h4>
     * @param date 时间
     * @param format 格式化时间样式
     * @return 10位 时间戳
     */
    private static Integer _dateToTimestamp(String date,String format){
        if (null == date) {
            return 0;
        }
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(format == null? FORMAT.DATE_FORMAT_TIME:format);
        try {
            return (int) (simpleDateFormat.parse(date).getTime()/1000);
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return 0;
        }
    }

    public interface FORMAT{
        String DATE_FORMAT = "yyyy-MM-dd";
        String DATE_FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
        String DATE_FORMAT_STR = "yyyy年MM月dd日";
    }

    public static void main(String[] args) {
        //String s ="2022-03-17 20:15:13";
        //Date date = conventStr2Date(s, "yyyy-MM-dd HH:mm:ss");
        //System.out.println(date);

    }
//    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//        /*天数差*/
//        Date fromDate = simpleFormat.parse("2001-06-28 8:00");
//        Date toDate = simpleFormat.parse("2018-06-28 8:00");
//        long from = fromDate.getYear();
//        long to = toDate.getYear();
//        int ys = (int)(to - from);
//        System.out.println("两个时间之间的年数差为：" + ys);
//        /*天数差*/
//        Date fromDate1 = simpleFormat.parse("2001-06-28 8:00");
//        Date toDate1 = simpleFormat.parse("2018-06-28 8:00");
//
//        long from1 = fromDate1.getTime();
//        long to1 = toDate1.getTime();
//        int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));
//        System.out.println("两个时间之间的天数差为：" + days);
//
//        /*小时差*/
//        Date fromDate2 = simpleFormat.parse("2001-06-28 8:00");
//        Date toDate2 = simpleFormat.parse("2018-06-28 8:00");
//        long from2 = fromDate2.getTime();
//        long to2 = toDate2.getTime();
//        int hours = (int) ((to2 - from2) / (1000 * 60 * 60));
//        System.out.println("两个时间之间的小时差为：" + hours);
//
//        /*分钟差*/
//        Date fromDate3 = simpleFormat.parse("2001-06-28 8:00");
//        Date toDate3 = simpleFormat.parse("2018-06-28 8:00");
//        long from3 = fromDate3.getTime();
//        long to3 = toDate3.getTime();
//        int minutes = (int) ((to3 - from3) / (1000 * 60));
//        System.out.println("两个时间之间的分钟差为：" + minutes);
//    }
}
