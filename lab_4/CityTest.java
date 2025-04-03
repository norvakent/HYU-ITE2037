public class CityTest {
  public static void main(String[] args) {
    City city1 = new City("Seoul", 23, 45);
    City city2 = new City("Paris", 123, 41);
    City city3 = new City("Racoon City");
    City city4 = new City("Mega City");
    System.out.println(city1);
    System.out.println(city2);
    System.out.println(city3);
    System.out.println(city4);
    System.out.println(city1.getName() + "-" + city2.getName() + " : " + City.distance(city1, city2));
    System.out.println(city1.getName() + "-" + city3.getName() + " : " + City.distance(city1, city3));
    System.out.println(city2.getName() + "-" + city4.getName() + " : " + City.distance(city2, city4));
  }
}
