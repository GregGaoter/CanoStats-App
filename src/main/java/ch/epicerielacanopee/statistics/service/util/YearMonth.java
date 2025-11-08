package ch.epicerielacanopee.statistics.service.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class YearMonth {
  
    private int year;
    private int month;

    public YearMonth(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public static YearMonth from(Instant date, ZoneId zone) {
        ZonedDateTime zonedDateTime = date.atZone(zone);
        return new YearMonth(zonedDateTime.getYear(), zonedDateTime.getMonthValue());
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }
}
