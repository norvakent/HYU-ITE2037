package lab_11;

import java.util.ArrayList;

// NumberGenerator: Subject 역할
public abstract class NumberGenerator {
  private ArrayList<Observer> observers = new ArrayList<Observer>();

  // Observer 등록
  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  // Observer 제거
  public void deleteObserver(Observer observer) {
    observers.remove(observer);
  }

  // Observer들에게 상태 변화 알림
  public void notifyObservers() {
    for (Observer o : observers) {
      o.update(this);
    }
  }

  public abstract int getNumber();

  public abstract void execute();
}
