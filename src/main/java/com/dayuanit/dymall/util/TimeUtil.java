package com.dayuanit.dymall.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String dateToString(Date date) {
		String time = sdf.format(date);
		return time;
	}
}
