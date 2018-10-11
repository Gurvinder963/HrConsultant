package com.hrconsultant.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String dd_MM_yyyy = "dd/MM/yyyy";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String SERVER_DATE = "yyyy-MM-dd'T'HH:mm:ss";
    private static DateUtils dateFactory;


    private static Locale locale = Locale.US;

    private DateUtils() {
        // set locale
        locale = Locale.US;
    }

    public static DateUtils getInstance() {
        if (dateFactory == null) {
            dateFactory = new DateUtils();
        }

        return dateFactory;
    }
    public String getDateFromDateFormat(Date inputDate, String formatDest) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatDest, locale);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        Date d = calendar.getTime();
        return sdf.format(d);
    }
    public String getTodayDate(String outputFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(outputFormat, locale);
        return sdf.format(Calendar.getInstance().getTime());
    }
    public String getCurrentTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        return sdf.format(Calendar.getInstance().getTime());
    }
    public boolean isFutureDate(int dayToCheck, int monthToCheck, int yearToCheck) {
        Date currantDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayToCheck);
        calendar.set(Calendar.MONTH, monthToCheck);
        calendar.set(Calendar.YEAR, yearToCheck);
        Date selectedDate = calendar.getTime();
        return selectedDate.after(currantDate);
    }
    public String formatDate(String formatSrc, String formatDest, String date) {
        String str;
        SimpleDateFormat sdf = new SimpleDateFormat(formatSrc, locale);
        try {
            Date myDate = sdf.parse(date);
            str = new SimpleDateFormat(formatDest, locale).format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return str;
    }

    public String formatTime(String formatSrc, String formatDest, String time) {
        String time1 = time.replace("AM", "am").replace("PM", "pm");
        String str = null;

        SimpleDateFormat sdf = new SimpleDateFormat(formatSrc, locale);
        try {
            Date myDate = sdf.parse(time1);
            str = new SimpleDateFormat(formatDest, locale).format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (str != null) {
            str = str.replace("am", "AM").replace("pm", "PM");
        }
        return str;
    }
}
