package lab_7;

import java.time.LocalDate;

public class Bev extends Car {
  private static int carNum;
  private static int co2emission;
  private static final int GHGPERCAR = 25;

  Bev() {
    super();
  }

  Bev(String name, LocalDate date, int carNum) {
    this.name = name;
    this.date = date;
    Bev.carNum += carNum;
    Bev.co2emission += carNum * GHGPERCAR;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Bev) {
      return this.name.equals(((Bev)obj).name) && this.date.isEqual(((Bev)obj).date);
    }
    return false;
  }

  @Override
  public String toString() {
    return "name: " + this.name + ", date: " + (this.date==null ? "null" : this.date.toString()) + ", carNum: " + Bev.carNum;
  }

  @Override
  public int totalCO2() {
    System.out.println("BEV emit CO2 most when generating electric energy");
    return Bev.co2emission;
  }
}
