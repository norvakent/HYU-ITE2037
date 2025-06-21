package assignment_4;

// Simple data class describing a car
public class Car {
  private String brand;        // car brand
  private String model;        // car model
  private int year;            // year of manufacture
  private double dailyRate;    // daily rental price
  private boolean isAvailable; // current availability

  // Initialize the car fields
  public Car(String brand, String model, int year, double dailyRate) {
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.dailyRate = dailyRate;

    // a new car is unavailable until added to the system
    this.isAvailable = false;
  }

  // Calculate total rental cost for the given days
  public double getRentalPrice(int days) {
    return this.dailyRate * days;
  }

  // Set availability status
  public synchronized void setAvailability(boolean status) {
    this.isAvailable = status;
  }

  // Get availability status
  public synchronized boolean isAvailable() {
    return this.isAvailable;
  }

  // Brand accessor
  public String getBrand() {
    return this.brand;
  }

  // Model accessor
  public String getModel() {
    return this.model;
  }

  // Year accessor
  public int getYear() {
    return this.year;
  }

  // Daily rate accessor
  public double getDailyRate() {
    return this.dailyRate;
  }

  // Represent the car as a human-readable string
  @Override
  public String toString() {
    return this.brand + " " +
           this.model + " (" +
           this.year + ") - $" +
           String.format("%.1f", this.dailyRate) + "/day - " +
           (this.isAvailable() ? "" : "Not ") + "Available";
  }
}