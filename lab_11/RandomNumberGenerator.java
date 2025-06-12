package lab_11;

import java.util.Random;

// RandomNumberGenerator: NumberGenerator 구현
public class RandomNumberGenerator extends NumberGenerator {
  private Random random;
  private int number;

  public RandomNumberGenerator() {
    random = new Random();
    number = 0;
  }

  public int getNumber() {
    return number;
  }

  // 상태를 변경하고 변경된 상태를 Observer들에게 알림
  public void execute() {
    number = random.nextInt(50) + 1;
    notifyObservers();
  }

  public static void main(String[] args) {
    RandomNumberGenerator generator = new RandomNumberGenerator();
    new DigitObserver(generator);
    new GraphObserver(generator);

    for (int i = 0; i < 10; i++) {
      generator.execute();
    }
  }
}
