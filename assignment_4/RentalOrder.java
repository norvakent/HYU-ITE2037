package assignment_4;

// Base class for rental orders
public abstract class RentalOrder {
  protected Car car;             // rented car
  protected Customer customer;   // renting customer
  protected int rentalDays;      // rental length in days

  // Initialize order details and mark the car unavailable
  public RentalOrder(Car car, Customer customer, int rentalDays) {
    this.car = car;
    this.customer= customer;
    this.rentalDays = rentalDays;
    this.car.setAvailability(false); // car becomes unavailable once rented
  }

  public RentalOrder(Car car, Customer customer, int rentalDays, boolean isAvailable) {
    this.car = car;
    this.customer = customer;
    this.rentalDays = rentalDays;
    this.car.setAvailability(true);
  }

  // Calculate the total price (implemented in subclasses)
  public abstract double calculateTotalPrice();

  // Car accessor
  public Car getCar() {
    return this.car;
  }

  // Customer accessor
  public Customer getCustomer() {
    return this.customer;
  }

  // Rental days accessor
  public int getRentalDays() {
    return this.rentalDays;
  }

  // Return a summary of the rental order
  public String toString() {
    return "Rental for: " + this.customer.toString() + "\n" +
           "Car: " + this.car.toString() + "\n" +
           "Days: " + this.rentalDays + "\n" +
           // formatting to 2 decimal place
           "Total: $" + String.format("%.2f", this.calculateTotalPrice());
  }
}
