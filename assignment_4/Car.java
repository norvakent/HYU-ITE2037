package assignment_4;

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
  public synchronized void setAvailability(boolean status) {
    this.isAvailable = status;
  }

  // getter for availability status
  public synchronized boolean isAvailable() {
    return this.isAvailable;
  }

  // getter for brand
  public String getBrand() {
    return this.brand;
  }

  // getter for model
  public String getModel() {
    return this.model;
  }

  // getter for year
  public int getYear() {
    return this.year;
  }

  // getter for daily rental rate
  public double getDailyRate() {
    return this.dailyRate;
  }

  // return string representation of the car
  @Override
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