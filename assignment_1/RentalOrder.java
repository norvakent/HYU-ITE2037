public class RentalOrder {
  private Car car;             // the car being rented
  private String customerName; // the name of the customer
  private int rentalDays;      // the number of rental days

  // constructor initializes the car, name, days and status of availability
  public RentalOrder(Car car, String customerName, int rentalDays) {
    this.car = car;
    this.customerName = customerName;
    this.rentalDays = rentalDays;
    this.car.setAvailability(false);
  }

  // method to compute total rental cost
  public double calculateTotalPrice() {
    return this.car.getRentalPrice(this.rentalDays);
  }

  // method to display order summary
  public String toString() {
    return "Order Summary:\n" +
           "Rental for: " + this.customerName + "\n" +
           "Car: " + this.car.toString() + "\n" +
           "Days: " + this.rentalDays + "\n" +
           // formatting to 1 decimal place
           "Total: $" + String.format("%.1f", this.calculateTotalPrice());
  }
}
