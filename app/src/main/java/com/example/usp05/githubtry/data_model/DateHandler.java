package com.example.usp05.githubtry.data_model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nathan on 4/22/18.
 */

public class DateHandler {

    private Date inputDate;

    /*
        G 	Era designator (before christ, after christ)
        y 	Year (e.g. 12 or 2012). Use either yy or yyyy.
        M 	Month in year. Number of M's determine length of format (e.g. MM, MMM or MMMMM)
        d 	Day in month. Number of d's determine length of format (e.g. d or dd)
        h 	Hour of day, 1-12 (AM / PM) (normally hh)
        H 	Hour of day, 0-23 (normally HH)
        m 	Minute in hour, 0-59 (normally mm)
        s 	Second in minute, 0-59 (normally ss)
        S 	Millisecond in second, 0-999 (normally SSS)
        E 	Day in week (e.g Monday, Tuesday etc.)
        D 	Day in year (1-366)
        F 	Day of week in month (e.g. 1st Thursday of December)
        w 	Week in year (1-53)
        W 	Week in month (0-5)
        a 	AM / PM marker
        k 	Hour in day (1-24, unlike HH's 0-23)
        K 	Hour in day, AM / PM (0-11)
        z 	Time Zone
        ' 	Escape for text delimiter
        ' 	Single quote
    */

    private final String itemDateStr = "MM/dd/yyyy";
    private final String notificationDateStr = "MM/dd/yyyy";
    private final String monthlyNotificationDateStr = "W, E, HH:mm";
    private final String weeklyNotificationDateStr = "E, HH:mm";
    private final String dailyNotificationDateStr = "HH:mm";

    private SimpleDateFormat itemDate = new SimpleDateFormat(itemDateStr);
    private SimpleDateFormat notificationDate = new SimpleDateFormat(notificationDateStr);
    private SimpleDateFormat monthNotificationDate = new SimpleDateFormat(monthlyNotificationDateStr);
    private SimpleDateFormat weekNotificationDate = new SimpleDateFormat(weeklyNotificationDateStr);
    private SimpleDateFormat dayNotificationDate = new SimpleDateFormat(dailyNotificationDateStr);

    public DateHandler() {

    }

    public Date intToDate(int month, int day, int year) throws ParseException {
        String m, d, y;

        m = Integer.toString(month);
        d = Integer.toString(day);
        y = Integer.toString(year);

        if((m.length() > 2) || (m.length() < 1)){
            throw new ParseException("Bad integer month format passed to intToDate method", 0);
        }

        if((d.length() > 2) || (d.length() < 1)){
            throw new ParseException("Bad integer day format passed to intToDate method", 0);
        }

        if(y.length() != 4){
            if(y.length() != 2) {
                throw new ParseException("Bad integer year format passed to intToDate method", 4);
            }
            y = "20" + y;
        }

        String str = m + "/" + d + "/" + y;

        inputDate = itemDate.parse(str);
        return inputDate;
    }

    public String itemDateToString(Date date){
        if (date != null) {
            return itemDate.format(date);
        } else {
            return "No date available";
        }
    }

    public String itemDateToString(){
        return itemDate.format(inputDate);
    }

    public Date itemStringToDate(String str){

        try {
            inputDate = itemDate.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return inputDate;
    }

}
