package ru.zaoemtika.EmtikaWork;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public abstract class CurrentDate {

	public static String currentDate(boolean TodayOrYesterday) {

		StringBuilder currDate = new StringBuilder();

		DateTime currdate = new DateTime();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyMMdd");

		if (TodayOrYesterday) {
			currDate.append(formatter.print(currdate));
			
		} else 
			if(currdate.getDayOfWeek() == 1){
				currDate.append(formatter.print(currdate.minusDays(3)));
			} else {
				currDate.append(formatter.print(currdate.minusDays(1)));
			}
		return currDate.toString();
	}

}
