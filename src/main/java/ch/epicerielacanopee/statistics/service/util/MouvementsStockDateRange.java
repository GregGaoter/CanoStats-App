package ch.epicerielacanopee.statistics.service.util;

import java.time.Instant;

public class MouvementsStockDateRange {

    private Instant startDate;
    private Instant endDate;

    public MouvementsStockDateRange(Instant startDate, Instant endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }
}
