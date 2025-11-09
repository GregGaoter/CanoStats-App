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
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return String.format("%04d.%02d", year, month);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + year;
        result = prime * result + month;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        YearMonth other = (YearMonth) obj;
        if (year != other.year)
            return false;
        if (month != other.month)
            return false;
        return true;
    }
}
