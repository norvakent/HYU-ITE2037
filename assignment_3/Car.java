package assignment_3;

public class Car {
  private String brand;        // the brand of the car
  private String model;        // the model of the car
  private int year;            // the year of manufacture
  private double dailyRate;    // the daily rental price
  private boolean isAvailable; // availability status

  // constructor initializes the car's attributes
  public Car(String brand, String model, int year, double dailyRate) {
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.dailyRate = dailyRate;

    // set availability to false by default
    this.isAvailable = false;
  }

  // calculate total rental cost
  public double getRentalPrice(int days) {
    return this.dailyRate * days;
  }

  // setter for availability status
  public void setAvailability(boolean status) {
    this.isAvailable = status;
    return;
  }

  // getter for availability status
  public boolean isAvailable() {
    return this.isAvailable;
  }

  // return string representation of the car
  public String toString() {
    return this.brand + " " +
           this.model + " (" +
           this.year + ") - $" +
           // formatting to 1 decimal place
           String.format("%.1f", this.dailyRate) + "/day - " +
           // simply indicating word "not" using ternary operator
           // if isAvailable() is true, it will be empty string
           // if isAvailable() is false, it will be "Not "
           (this.isAvailable() ? "" : "Not ") + "Available";
  }
}