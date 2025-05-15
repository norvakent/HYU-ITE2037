package assignment_3;

public class LongTermRental extends RentalOrder {
  // constructor passes info to superclass constructor
  public LongTermRental(Car car, Customer customer, int rentalDays) {
    super(car, customer, rentalDays);
  }

  // calculat price with 15% long-term discount and optional 10% VIP discount
  @Override
  public double calculateTotalPrice() {
    return this.car.getRentalPrice(this.rentalDays) * 0.85 * (this.customer.isVIP() ? 0.9 : 1);
  }
}
