package org.kritiniyoga.karmayoga.core.values;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static io.vavr.API.*;

@Value
public class TimeSlot implements Comparable<TimeSlot> {
    private static final String ERROR_MSG_END_BEFORE_START = "End should be after start";
    Instant start, end;

    public TimeSlot(@NonNull Instant start, @NonNull Instant end) {
        this.start = start;
        this.end = end;
    }

    public static TimeSlot createFrom(@NonNull Instant start, @NonNull Instant end) {
        Tuple2<Instant, Instant> validatedInstances =
            checkEndIsAfterStart(Tuple.of(start, end)).get();
        return new TimeSlot(validatedInstances._1, validatedInstances._2);
    }

    private static Validation<String, Tuple2<Instant, Instant>> checkEndIsAfterStart(Tuple2<Instant, Instant> instances) {
        return Match(instances).of(
            Case($(i -> i._1.isBefore(i._2)), Validation.valid(instances)),
            Case($(), Validation.invalid(ERROR_MSG_END_BEFORE_START
                + " [End: " + Date.from(instances._2)
                + " Start: " + Date.from(instances._1)
                + "]"))
        );
    }

    public List<TimeSlot> split(Instant splitPoint) {
        return List.of(
            createFrom(this.start, splitPoint),
            createFrom(splitPoint, this.end)
        );
    }

    public Duration length() {
        return Duration.between(start, end);
    }

    @Override
    public int compareTo(TimeSlot o) {
        return this.length().compareTo(o.length());
    }
}
