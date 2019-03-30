package org.kritiniyoga.karmayoga.validators;

import io.vavr.Tuple2;
import io.vavr.control.Validation;

import java.time.Instant;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

public class TimeSlotValidator {

    private static final String ERROR_MSG_END_BEFORE_START = "End should be after start";

    public static Validation<String, Tuple2<Instant, Instant>> checkEndIsAfterStart(Tuple2<Instant, Instant> instances) {
        return Match(instances).of(
            Case($(i -> i._1.isBefore(i._2)), Validation.valid(instances)),
            Case($(), Validation.invalid(ERROR_MSG_END_BEFORE_START))
        );
    }
}
