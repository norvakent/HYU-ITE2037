package lab_7;

import java.time.LocalDate;

public class Ice extends Car {
  private static int carNum;
  private static int co2emission;
  private static final int GHGPERCAR = 35;

  Ice() {
    super();
  }

  Ice(String name, LocalDate date, int carNum) {
    this.name = name;
    this.date = date;
    Ice.carNum += carNum;
    Ice.co2emission += carNum * GHGPERCAR;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Ice) {
      return this.name.equals(((Ice)obj).name) && this.date.isEqual(((Ice)obj).date);
    }
    return false;
  }

  @Override
  public String toString() {
    return "name: " + this.name + ", date: " + (this.date==null ? "null" : this.date.toString()) + ", carNum: " + Ice.carNum;
  }

  @Override
  public int totalCO2() {
    System.out.println("ICE emit CO2 most when driving");
    return Ice.co2emission;
  }
}

