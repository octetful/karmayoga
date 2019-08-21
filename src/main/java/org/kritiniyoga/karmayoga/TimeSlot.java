package org.kritiniyoga.karmayoga;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.NonNull;
import lombok.Value;
import org.kritiniyoga.karmayoga.validators.TimeSlotValidator;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Value
public class TimeSlot implements Comparable<TimeSlot> {
    Instant start, end;

    private TimeSlot(@NonNull Instant start, @NonNull Instant end) {
        this.start = start;
        this.end = end;
    }

    public List<TimeSlot> split(Instant splitPoint) {
        return List.of(
            createTimeSlot(this.start, splitPoint),
            createTimeSlot(splitPoint, this.end)
        );
    }

    public Duration length() {
        return Duration.between(start, end);
    }

    public static TimeSlot createTimeSlot(@NonNull Instant start, @NonNull Instant end) {
        Tuple2<Instant, Instant> validatedInstances =
            TimeSlotValidator.checkEndIsAfterStart(Tuple.of(start, end)).get();
        return new TimeSlot(validatedInstances._1, validatedInstances._2);
    }

    @Override
    public int compareTo(TimeSlot o) {
        return this.length().compareTo(o.length());
    }
}
