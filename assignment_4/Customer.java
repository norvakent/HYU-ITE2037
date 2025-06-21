package assignment_4;

// Customer information and reward points
public class Customer {
  private String name;     // customer name
  private boolean isVIP;   // true if VIP member
  private int points;

  // Initialize customer data
  public Customer(String name, boolean isVIP) {
    this.name = name;
    this.isVIP = isVIP;
    this.points = 0;
  }

  // Name accessor
  public String getName() {
    return this.name;
  }

  // VIP status accessor
  public boolean isVIP() {
    return this.isVIP;
  }

  public void addPoints(int amount) {
    points += amount;
  }

  public int getPoints() {
    return points;
  }

  // Represent customer as a string
  public String toString() {
    return this.name + (this.isVIP ? " (VIP)" : "");
  }
}
