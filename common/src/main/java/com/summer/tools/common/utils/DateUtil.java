package com.summer.tools.common.utils;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;

/**
 * 基于java8 的日期工具类
 * @author john.wang
 */
public class DateUtil {

    public static final String YEAR_MONTH_DATE_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_MONTH_DATE = "yyyy-MM-dd";
    public static final String HOUR_MINUTE_SECOND = "HH:mm:ss";

    /**
     * 用默认格式把日期时间转换成字符串
     * @param temporal 日期时间
     * @return 格式化时间字符串
     */
    public static String format(TemporalAccessor temporal) {
        return formatter(YEAR_MONTH_DATE_HOUR_MINUTE_SECOND).format(temporal);
    }

    /**
     * 用指定格式把日期时间转换成字符串
     * @param temporal 日期时间
     * @param formatStr 格式化字符串
     * @return 格式化时间字符串
     */
    public static String format(TemporalAccessor temporal, String formatStr) {
        return formatter(formatStr).format(temporal);
    }

    /**
     * 用默认格式把日期时间字符串转换成日期时间
     * @param dateTimeStr 日期时间字符串
     * @return 日期时间
     */
    public static LocalDateTime parse(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, formatter(YEAR_MONTH_DATE_HOUR_MINUTE_SECOND));
    }

    /**
     * 用指定格式把日期时间字符串转换成日期时间
     * @param dateTimeStr 日期时间字符串
     * @param formatStr 格式化字符串
     * @return 日期时间
     */
    public static LocalDateTime parse(String dateTimeStr, String formatStr) {
        return LocalDateTime.parse(dateTimeStr, formatter(formatStr));
    }

    /**
     * 用默认格式把日期字符串转换成日期
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, formatter(YEAR_MONTH_DATE));
    }

    /**
     * 用指定格式把日期字符串转换成日期
     * @param dateStr 日期字符串
     * @param formatStr 格式化字符串
     * @return 日期
     */
    public static LocalDate parseLocalDate(String dateStr, String formatStr) {
        return LocalDate.parse(dateStr, formatter(formatStr));
    }

    /**
     * 用默认格式把时间字符串转换成日期
     * @param timeStr 时间字符串
     * @return 时间
     */
    public static LocalTime parseLocalTime(String timeStr) {
        return LocalTime.parse(timeStr, formatter(HOUR_MINUTE_SECOND));
    }

    /**
     * 用指定格式把时间字符串转换成日期
     * @param timeStr 时间字符串
     * @param formatStr 格式化字符串
     * @return 时间
     */
    public static LocalTime parseLocalTime(String timeStr, String formatStr) {
        return LocalTime.parse(timeStr, formatter(formatStr));
    }

    /**
     * 获取格式化对象
     * @param formatStr 格式化字符串
     * @return 时间格式化对象
     */
    private static DateTimeFormatter formatter(String formatStr) {
        return formatter(formatStr, Locale.getDefault());
    }

    /**
     * 获取格式化对象
     * @param formatStr 格式化字符串
     * @param locale 区域信息
     * @return 时间格式化对象
     */
    private static DateTimeFormatter formatter(String formatStr, Locale locale) {
        return DateTimeFormatter.ofPattern(formatStr, locale);
    }

    /**
     * 日期时间转 java.util.Date
     * @param dateTime 日期时间
     * @return java.util.Date
     */
    public static Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期时间转 java.util.Date
     * @param date 日期
     * @return java.util.Date
     */
    public static Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * java.util.Date 转日期时间
     * @param date java.util.Date
     * @return 日期时间
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 获取一天的开始时间
     * @return 一天的开始时间
     */
    public static LocalTime startTimeOfDay() {
        return LocalTime.of(0, 0, 0, 0);
    }

    /**
     * 获取指定年月的那个月的第一天
     * @param year 年
     * @param month 月
     * @return 指定年月的那个月的第一天
     */
    public static LocalDate startDayOfMonth(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    /**
     * 获取当月第一天
     * @return 当月的第一天
     */
    public static LocalDate startDayOfMonth() {
        return LocalDate.now().withDayOfMonth(1);
    }

    /**
     * 获取当年第一天
     * @return 当年的第一天
     */
    public static LocalDate startDayOfYear() {
        return LocalDate.now().withDayOfYear(1);
    }

    /**
     * 获取一周第一天
     * @return 一周第一天
     */
    public static LocalDate startDayOfWeek() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    /**
     * 获取星期值
     * @return 星期值
     */
    public static int weekIndex() {
        return LocalDate.now().getDayOfWeek().getValue();
    }

    /**
     * 获取时间差
     * @param before 较后的时间
     * @param after 较前的时间
     * @param timeUnit 时间单位
     * @return 时间差值
     */
    public static long diff(LocalDateTime before, LocalDateTime after, ChronoUnit timeUnit) {
        return timeUnit.between(before, after);
    }

    /**
     * 加时间
     * @param dateTime 基础时间
     * @param amountToAdd 需要添加的值
     * @param timeUnit 时间单位
     * @return 添加 amountToAdd 后的时间
     */
    public static LocalDateTime plus(LocalDateTime dateTime, long amountToAdd, ChronoUnit timeUnit) {
        return dateTime.plus(amountToAdd, timeUnit);
    }

    /**
     * 减时间
     * @param dateTime 基础时间
     * @param amountToAdd 需要减的值
     * @param timeUnit 时间单位
     * @return 减 amountToAdd 后的时间
     */
    public static LocalDateTime minus(LocalDateTime dateTime, long amountToAdd, ChronoUnit timeUnit) {
        return dateTime.minus(amountToAdd, timeUnit);
    }
}
