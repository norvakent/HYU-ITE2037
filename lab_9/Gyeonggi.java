package lab_9;

public class Gyeonggi extends Hub {
  public static int init_num = 10000;
  public static String init_area = "Gyeonggi";
  public static double init_price_per_box = 3000;

  public Gyeonggi(String description) {
    super(++init_num, description, init_area, init_price_per_box);
  }
}
