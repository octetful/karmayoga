package org.kritiniyoga.karmayoga;

import lombok.Value;

import java.time.Instant;

@Value
public class TimeSlot {
    Instant start, end;

    public TimeSlot(Instant start, Instant end) {
        super();
        if ( start.isAfter(end) ) {
            throw new IllegalArgumentException("End must be after start");
        } else {
            this.end = end;
            this.start = start;
        }
    }
}
