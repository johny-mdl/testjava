package com.bet.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Long miliTruncateDay(Long miliDate) {
		Date date = new Date(miliDate);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime().getTime();
	}

	public static Long addDays(long miliDate, int days) {
		Date date = new Date(miliDate);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime().getTime();
	}
}
