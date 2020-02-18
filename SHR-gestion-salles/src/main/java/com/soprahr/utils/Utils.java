package com.soprahr.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import net.minidev.json.JSONObject;

public class Utils {

	public Utils() {
		super();
	}

	public boolean dateEquals(Date d1 , Date d2) {
		
		JSONObject jo = new JSONObject();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);

		if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
			if (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {
				if (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
					jo.put("Success", "Les dates sont égaux");
					return true;
				} else {
					jo.put("Error", "Les dates ne sont pas égaux");
					return false;
				}
			} else {
				jo.put("Error", "Les dates ne sont pas égaux");
				return false;
			}
		} else {
			jo.put("Error", "Les dates ne sont pas égaux");
			return false;
		}

	}

	public Date convertStringToDate(String sDate) {

		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(sDate + "+00:00");
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;

		}
	}
}
