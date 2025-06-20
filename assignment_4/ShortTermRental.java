package assignment_4;

public class ShortTermRental extends RentalOrder {
  // constructor passes info to superclass constructor
  public ShortTermRental(Car car, Customer customer, int rentalDays) {
    super(car, customer, rentalDays);
  }

  public ShortTermRental(Car car, Customer customer, int rentalDays, boolean isAvailable) {
    super(car, customer, rentalDays, isAvailable);
  }

  // calculate price with optional VIP discound (10%)
  @Override
  public double calculateTotalPrice() {
    return this.car.getRentalPrice(this.rentalDays) * (this.customer.isVIP() ? 0.9 : 1);
  }
}
