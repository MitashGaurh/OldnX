package com.oldandx.oldnx.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Date utils
 */
public final class DateUtils {
    private static final String TAG = "DateUtils";
    public static final String yyyyMMDD = "yyyy-MM-dd";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String HHmmss = "HH:mm:ss";
    public static final String hhmmss = "hh:mm:ss a";
    public static final String DB_DATA_FORMAT = "yyyy-MM-DD HH:mm:ss";

  /*private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private static DateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale
            .ENGLISH);*/

    private static DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    private static DateFormat formatDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale
            .ENGLISH);


    public static String longToSimpleDateString(long DateTimeLong) {
        String parsedDateString;
        Date parsedDate = new Date(DateTimeLong);
        parsedDateString = format.format(parsedDate);
        return parsedDateString;
    }

    public static String longToSimpleDateTimeString(long DateTimeLong) {
        String parsedDateString;
        Date parsedDate = new Date(DateTimeLong);
        parsedDateString = formatDateTime.format(parsedDate);
        return parsedDateString;
    }

    public static Date stringToDateTime(String dateString) {
        Date date = null;
        formatDateTime.setTimeZone(TimeZone.getDefault());
        formatDateTime.setLenient(false);
        try {
            date = formatDateTime.parse(dateString);
        } catch (ParseException parseException) {
            Log.e(TAG, "Exception while parsing " + dateString + "  to " +
                    "DateTime !!", parseException);
        }

        return date;
    }


    public static Date stringToDate(String dateString) {
        Date date = null;
        format.setTimeZone(TimeZone.getDefault());
        format.setLenient(false);
        try {
            date = format.parse(dateString);
        } catch (ParseException parseException) {
            Log.e(TAG, "Exception while parsing " + dateString + " to Date " +
                    "!! ", parseException);
        }

        return date;
    }

    public static int[] splittedDate(String date) {
        int[] splitted = new int[3];
        String[] dateSpilt = date.split("-");

        for (int i = 0; i < dateSpilt.length; i++) {
            splitted[i] = Integer.parseInt(dateSpilt[i]);
        }

        return splitted;
    }

    public static String getTimeString(int minute, int hour) {
        StringBuilder time = new StringBuilder();
        if (hour < 10) {
            time.append("0");
        }
        time.append(hour);
        time.append(":");
        if (minute < 10) {
            time.append("0");
        }
        time.append(minute);
        time.append(":00");
        return time.toString();
    }

    public static String getDateString(int day, int month, int year) {
        StringBuilder stringBuilder = new StringBuilder();
        if (day < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(day);
        stringBuilder.append("-");
        if (month < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(month);
        stringBuilder.append("-");

        stringBuilder.append(year);

        return stringBuilder.toString();
    }

    public static String getMonth(int i) {
        return new DateFormatSymbols().getShortMonths()[i - 1];
    }


    public static boolean isThisDateValid(String dateToValidate) {
        boolean isValid;
        format.setTimeZone(TimeZone.getDefault());
        format.setLenient(false);
        try {
            format.parse(dateToValidate);
            isValid=true;
        } catch (ParseException parseException) {
            isValid = false;
        }
        return isValid;
    }


    private static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTime(date);
        return cal;
    }
    public static Calendar nextDayOfWeek(int dow) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY,10);
        date.set(Calendar.MINUTE, 0);
        int diff = dow - date.get(Calendar.DAY_OF_WEEK);
        if (!(diff >= 0)) {
            diff += 7;
        }
        date.add(Calendar.DAY_OF_MONTH, diff);
        return date;
    }

    public static String dateToString(Date date, String pattern)
            throws Exception {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    public static Date stringToDate(String dateStr, String pattern)
            throws Exception {
        return new SimpleDateFormat(pattern, Locale.getDefault()).parse(dateStr);
    }

    public static String formatDate(Date date, String type) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(type, Locale.getDefault());
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDate(String dateStr, String type) {
        SimpleDateFormat df = new SimpleDateFormat(type, Locale.getDefault());
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;

    }

    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static String translateDate(Long time) {
        long oneDay = 24 * 60 * 60 * 1000;
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        long todayStartTime = today.getTimeInMillis();

        if (time >= todayStartTime && time < todayStartTime + oneDay) { // today
            return "today";
        } else if (time >= todayStartTime - oneDay && time < todayStartTime) { // yesterday
            return "yesterday";
        } else if (time >= todayStartTime - oneDay * 2 && time < todayStartTime - oneDay) { // the day before yesterday
            return "day before yesterday";
        } else if (time > todayStartTime + oneDay) { // future
            return "future";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date(time);
            return dateFormat.format(date);
        }
    }

    private String translateDate(long time, long curTime) {
        long oneDay = 24 * 60 * 60;
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(curTime * 1000);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        long todayStartTime = today.getTimeInMillis() / 1000;
        if (time >= todayStartTime) {
            long d = curTime - time;
            if (d <= 60) {
                return "1 minute ago";
            } else if (d <= 60 * 60) {
                long m = d / 60;
                if (m <= 0) {
                    m = 1;
                }
                return m + " minutes ago";
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("today HH:mm", Locale.getDefault());
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        } else {
            if (time < todayStartTime && time > todayStartTime - oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yesterday HH:mm", Locale.getDefault());
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {

                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else if (time < todayStartTime - oneDay && time > todayStartTime - 2 * oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("day before yesterday HH:mm", Locale.getDefault());
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        }
    }

    public static Date nextYearDate(int i) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 3); // to get previous year add -1
        return cal.getTime();
    }
}
