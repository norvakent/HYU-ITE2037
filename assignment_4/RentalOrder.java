package assignment_4;

public abstract class RentalOrder {
  protected Car car;             // the car being rented
  protected Customer customer;   // the customer renting the car
  protected int rentalDays;      // the number of rental days

  // constructor initializes the order details and marks the car as not available
  public RentalOrder(Car car, Customer customer, int rentalDays) {
    this.car = car;
    this.customer= customer;
    this.rentalDays = rentalDays;
    this.car.setAvailability(false); // car becomes unavailable once rented
  }

  // abstract method to calculate total rental price (implemented in subclasses) 
  public abstract double calculateTotalPrice();

  // getter for car
  public Car getCar() {
    return this.car;
  }

  // getter for customer
  public Customer getCustomer() {
    return this.customer;
  }

  // getter for rental days
  public int getRentalDays() {
    return this.rentalDays;
  }

  // return summary of the rental order
  public String toString() {
    return "Rental for: " + this.customer.toString() + "\n" +
           "Car: " + this.car.toString() + "\n" +
           "Days: " + this.rentalDays + "\n" +
           // formatting to 2 decimal place
           "Total: $" + String.format("%.2f", this.calculateTotalPrice());
  }
}
