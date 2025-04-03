public class City {
  private String name;
  private int locationX;
  private int locationY;

  public City(String name, int locationX, int locationY) {
    this.name = name;
    this.locationX = locationX;
    this.locationY = locationY;
  }

  public City(String name) {
    this.name = name;
    this.locationX = (int) (Math.random() * 361);
    this.locationY = (int) (Math.random() * 361);
  }

  public String getName() {
    return this.name;
  }
  
  public int getLocationX() {
    return this.locationX;
  }
  
  public int getLocationY() {
    return this.locationY;
  }

  public Boolean equals(City other) {
    if(this.name.equals(other.name) && this.locationX == other.locationX && this.locationY == other.locationY) {
      return true;
    }
    else {
      return false;
    }
  }

  public String toString() {
    return this.name + ", " + this.locationX + ", " + this.locationY;
  }

  public static double distance(City city1, City city2) {
    return Math.sqrt(Math.pow(city1.locationX - city2.locationX, 2) + Math.pow(city1.locationY - city2.locationY, 2));
  }
}
