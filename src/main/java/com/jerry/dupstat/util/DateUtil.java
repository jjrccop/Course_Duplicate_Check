package com.jerry.dupstat.util;

import javafx.util.Pair;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class DateUtil {
    private static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    static Pair<Date, Date> getTime(String startDate, int cnt) {
        String left = startDate, right = startDate;
        int day = cnt / 12;
        int time = cnt % 12;
        left += SysConstant.perCourseStart[time];
        right += SysConstant.perCourseEnd[time];
        Date dateLeft = new Date(), dateRight = new Date();

        try {
            dateLeft = addDays(new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(left), day);
            dateRight = addDays((new SimpleDateFormat("yyyy/MM/dd HH:mm")).parse(right), day);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pair<Date, Date> ans = new Pair<>(dateLeft, dateRight);
        return ans;
    }
}
