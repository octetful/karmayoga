package org.kritiniyoga.karmayoga;

import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.List;

@Value
public class TimeSlot {
    Instant start, end;

    public TimeSlot(@NonNull Instant start, @NonNull Instant end) {
        if ( start.isAfter(end) ) {
            throw new IllegalArgumentException("End must be after start");
        } else {
            this.start = start;
            this.end = end;
        }
    }

    public List<TimeSlot> split(Instant splitPoint) {
        return List.of(
            new TimeSlot(this.start, splitPoint),
            new TimeSlot(splitPoint, this.end)
        );
    }
}
