package assignment_4;

public class Customer {
  private String name;     // customer name
  private boolean isVIP;   // membership status (true if VIP)
  private int points;

  // constructor initializes the customer's name and VIP status
  public Customer(String name, boolean isVIP) {
    this.name = name;
    this.isVIP = isVIP;
    this.points = 0;
  }

  // getter for customer's name
  public String getName() {
    return this.name;
  }

  // getter for VIP status
  public boolean isVIP() {
    return this.isVIP;
  }

  public void addPoints(int amount) {
  	  points += amount;
  }

  public int getPoints() {
	  return points;
  }

  // return string representation of the customer
  public String toString() {
    return this.name + (this.isVIP ? " (VIP)" : "");
  }
}
