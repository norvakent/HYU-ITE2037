package lab_9;

public class Hub {
  private int number;
  private String description;
  private String area;
  private double price_per_box;

  public Hub(int number, String description, String area, double price_per_box) {
    this.number = number;
    this.description = description;
    this.area = area;
    this.price_per_box = price_per_box;
  }

  public int getNumber() {
    return this.number;
  }

  public void setNumber(int number) {
    this.number = number;
    return;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
    return;
  }

  public String getArea() {
    return this.area;
  }

  public void setArea(String area) {
    this.area = area;
    return;
  }

  public double getPricePerBox() {
    return this.price_per_box;
  }

  public void setPricePerBox(double price_per_box) {
    this.price_per_box = price_per_box;
    return;
  }

  @Override
  public String toString() {
    return "Box Number : " + this.number +
           "\nDescription : " + this.description +
           "\nArea : " + this.area +
           "\nPrice per box : " + String.format("%.1f", this.price_per_box);
  }
}
