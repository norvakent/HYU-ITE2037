package assignment_3;

public class Customer {
  private String name;     // customer name
  private boolean isVIP;   // membership status (true if VIP)

  // constructor initializes the customer's name and VIP status
  public Customer(String name, boolean isVIP) {
    this.name = name;
    this.isVIP = isVIP;
  }

  // getter for customer's name
  public String getName() {
    return this.name;
  }

  // getter for VIP status
  public boolean isVIP() {
    return this.isVIP;
  }

  // return string representation of the customer
  public String toString() {
    return this.name + (this.isVIP ? " (VIP)" : "");
  }
}
