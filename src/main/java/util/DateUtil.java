package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mogo on 2/6/17.
 */
public class DateUtil {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static Date getDate(String dateString){
        Date date = null;
        try {
            date = new Date(DEFAULT_DATE_FORMAT.parse(dateString).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getSqlDate(String dateString){
        return convertToSqlDate(getDate(dateString));
    }

    public static java.sql.Date convertToSqlDate(Date date) {
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());// here is NPE!
        return sqlDate;
    }

    public String dateToString(Date date){
        return DEFAULT_DATE_FORMAT.format(date);
    }
}
