package assignment_4;

// Rental type with a long-term discount
public class LongTermRental extends RentalOrder {
  // Pass information to the base class
  public LongTermRental(Car car, Customer customer, int rentalDays) {
    super(car, customer, rentalDays);
  }

  public LongTermRental(Car car, Customer customer, int rentalDays, boolean isAvailable) {
    super(car, customer, rentalDays, isAvailable);
  }

  // Calculate price with 15% long‑term discount and optional 10% VIP discount
  @Override
  public double calculateTotalPrice() {
    return this.car.getRentalPrice(this.rentalDays) * 0.85 * (this.customer.isVIP() ? 0.9 : 1);
  }
}
