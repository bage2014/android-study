package com.bage.tutorials.utils;

import com.bage.tutorials.domain.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String format(long timeStamp, DateFormat format) {
        return format(new Date(timeStamp), format);
    }

    public static String format(Date date, DateFormat format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format.getFormat(), Locale.CHINA);
        return sdf.format(date);
    }

}