package builder.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author pengshuaifeng
 * 2023/12/27
 */
@Slf4j
public class DateUtils {

    public static final String defaultFormat="yyyy-MM-dd HH:mm:ss";

    //默认DateFormat
    public static final DateFormat format = new SimpleDateFormat(defaultFormat);

    public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(defaultFormat);

    /**
     * 格式化时间
     * @param date 时间对象
     * @param pattern 格式化模型
     * 2023/12/27 22:08
     * @author pengshuaifeng
     */
    public static String format(Date date,String pattern){
        DateFormat dateFormat = pattern==null?format:new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String format(LocalDate localDate,String pattern){
        DateTimeFormatter dateTimeFormatter= pattern==null?dateTimeFormat:DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(localDate);
    }

    public static String format(LocalDateTime localDate,String pattern){
        DateTimeFormatter dateTimeFormatter=pattern==null?dateTimeFormat:DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(localDate);
    }

    public static String format(Date date){
        return format(date,null);
    }


    /**
     * 调整时间
     * @param now 操作时间
     * @param dateUnitType 时间单位
     * @param operateType 操作类型
     * @param count 操作量
     * 2023/12/11 0011 15:04
     * @author fulin-peng
     */
    public static LocalDateTime operateTime(LocalDateTime now, DateUnitType dateUnitType, DateOperateType operateType, int count){
        LocalDateTime currentTime =now==null?LocalDateTime.now():now;
        LocalDateTime newTime=null;
        if (operateType== DateOperateType.INCREASE) {
            switch (dateUnitType){
                case SECOND:
                    newTime = currentTime.plusSeconds(count);
                    break;
                case MINUTE:
                    newTime = currentTime.plusMinutes(count);
                    break;
                case HOUR:
                    newTime = currentTime.plusHours(count);
                    break;
                case DAY:
                    newTime = currentTime.plusDays(count);
                    break;
                case MOUTH:
                    newTime = currentTime.plusMonths(count);
                    break;
                case YEAR:
                    newTime = currentTime.plusYears(count);
                    break;
            }
        }else{
            switch (dateUnitType){
                case SECOND:
                    newTime = currentTime.minusSeconds(count);
                    break;
                case MINUTE:
                    newTime = currentTime.minusMinutes(count);
                    break;
                case HOUR:
                    newTime = currentTime.minusHours(count);
                    break;
                case DAY:
                    newTime = currentTime.minusDays(count);
                    break;
                case MOUTH:
                    newTime = currentTime.minusMonths(count);
                    break;
                case YEAR:
                    newTime = currentTime.minusYears(count);
                    break;
            }
        }
        return newTime;
    }

    public static Date operateTime(Date now,DateUnitType dateUnitType,DateOperateType operateType,int count){
        now = now == null ? new Date() : now;
        return localDateTimeToDate(operateTime(dateToLocalDateTime(now), dateUnitType, operateType, count));
    }

    /**
     * Date转LocalDateTime
     * 2023/12/27 22:37
     * @author pengshuaifeng
     */
    public static LocalDateTime dateToLocalDateTime(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDateTime转Date
     * 2023/12/27 22:48
     * @author pengshuaifeng
     */
    public static Date localDateTimeToDate(LocalDateTime localDate){
        return Date.from(localDate.atZone(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * 字符串转Date
     * 2024/3/6 0006 16:10
     * @author fulin-peng
     */
    public static Date stringToDate(String value,DateFormat dateFormat) {
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            throw new RuntimeException("字符串转Date异常",e);
        }
    }

    /**
     * 字符串转Date
     * <p>使用默认格式</p>
     * 2024/3/6 0006 16:16
     * @author fulin-peng
     */
    public static Date stringToDate(String value) {
        return stringToDate(value,format);
    }

    /**
     * 字符串转Date
     * 2024/3/6 0006 16:16
     * @param value 时间字符串
     * @param dateFormat 时间格式
     * @author fulin-peng
     */
    public static Date stringToDate(String value,String dateFormat){
        return stringToDate(value,new SimpleDateFormat(dateFormat));
    }

    /**
     * 时间戳转Date
     * 2024/3/27 21:09
     * @author pengshuaifeng
     */
    public static Date timestampToDate(long timestamp){
        return new Date(timestamp);
    }

    /**
     * 时间戳转LocalDateTime
     * 2024/3/27 21:10
     * @author pengshuaifeng
     */
    public static LocalDateTime timestampToLocalDateTime(long timestamp){
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    public enum DateUnitType {
        SECOND,
        MINUTE,
        HOUR,
        DAY,
        MOUTH,
        YEAR
    }

    public enum DateOperateType{
        INCREASE,
        REDUCE
    }
}
