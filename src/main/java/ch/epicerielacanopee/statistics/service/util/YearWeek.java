package ch.epicerielacanopee.statistics.service.util;

import java.time.ZonedDateTime;
import java.time.temporal.WeekFields;
import java.util.Objects;

public class YearWeek implements Comparable<YearWeek> {

    private int year;
    private int week;

    public YearWeek(int year, int week) {
        this.year = year;
        this.week = week;
    }

    public int getYear() {
        return this.year;
    }

    public int getWeek() {
        return this.week;
    }

    public static YearWeek from(ZonedDateTime zdt, WeekFields wf) {
        int y = zdt.get(wf.weekBasedYear());
        int w = zdt.get(wf.weekOfWeekBasedYear());
        return new YearWeek(y, w);
    }

    @Override
    public int compareTo(YearWeek other) {
        int c = Integer.compare(this.year, other.year);
        if (c != 0) return c;
        return Integer.compare(this.week, other.week);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YearWeek)) return false;
        YearWeek that = (YearWeek) o;
        return year == that.year && week == that.week;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.year, this.week);
    }

    @Override
    public String toString() {
        return this.year + "-S" + this.week;
    }
}
