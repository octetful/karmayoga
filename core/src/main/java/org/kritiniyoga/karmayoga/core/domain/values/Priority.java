package org.kritiniyoga.karmayoga.core.domain.values;


import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.isIn;

public enum Priority {
  LOW, MEDIUM, HIGH, UNKNOWN;

  /**
   * Given a string representation, returns an enumerated Priority value.
   *
   * @param priority a string representation of priority, allowed values are (L/l/M/m/H/h).
   * @return An enumerated priority.
   */
  public Priority from(String priority) {
    return Match(priority).of(
        Case($(isIn("L", "l")), LOW),
        Case($(isIn("M", "m")), MEDIUM),
        Case($(isIn("H", "h")), HIGH),
        Case($(), UNKNOWN)
    );
  }
}
