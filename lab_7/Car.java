package lab_7;

import java.time.LocalDate;

public abstract class Car {
  protected String name;
  protected LocalDate date;

  protected Car() {
    this.name = "Car Frame";
    this.date = null;
  }

  protected abstract int totalCO2();
}