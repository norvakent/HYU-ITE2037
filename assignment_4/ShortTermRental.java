package assignment_4;

// Standard rental without long-term discount
public class ShortTermRental extends RentalOrder {
  // Pass information to the base class
  public ShortTermRental(Car car, Customer customer, int rentalDays) {
    super(car, customer, rentalDays);
  }

  public ShortTermRental(Car car, Customer customer, int rentalDays, boolean isAvailable) {
    super(car, customer, rentalDays, isAvailable);
  }

  // Calculate price with optional 10% VIP discount
  @Override
  public double calculateTotalPrice() {
    return this.car.getRentalPrice(this.rentalDays) * (this.customer.isVIP() ? 0.9 : 1);
  }
}
