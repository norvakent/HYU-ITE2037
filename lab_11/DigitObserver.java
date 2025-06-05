package lab_11;

// DigitObserver: random number를 숫자로 출력
public class DigitObserver implements Observer {
  public DigitObserver(NumberGenerator generator) {
    generator.addObserver(this);
  }

  @Override
  public void update(NumberGenerator generator) {
    System.out.println("DigitObserver: " + generator.getNumber());
  }
}
