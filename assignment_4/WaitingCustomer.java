package "assignment_4"

public class WaitingCustomer {
  private Customer customer;
  private int rentalDays;

  public WaitingCustomer(Customer customer, int rentalDays) {
	  this.customer = customer;
	  this.rentalDays = rentalDays;
  }

  public Customer getCustomer() {
	  return customer;
  }

  public int getRentalDays() {
	  return rentalDays;
  }
}
