package assignment_4;

public class LongTermRental extends RentalOrder {
  // constructor passes info to superclass constructor
  public LongTermRental(Car car, Customer customer, int rentalDays) {
    super(car, customer, rentalDays);
  }

  public LongTermRental(Car car, Customer customer, int rentalDays, boolean isAvailable) {
    super(car, customer, rentalDays, isAvailable);
  }

  // calculat price with 15% long-term discount and optional 10% VIP discount
  @Override
  public double calculateTotalPrice() {
    return this.car.getRentalPrice(this.rentalDays) * 0.85 * (this.customer.isVIP() ? 0.9 : 1);
  }
}
