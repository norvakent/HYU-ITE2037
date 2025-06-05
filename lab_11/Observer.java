package lab_11;

// Observer Interface: 상태 변화에 반응하는 객체들의 동작을 일관되게 지정함
// Interface가 없으면 Observer들을 개별적으로 구현, 개별적으로 동작하도록 일일이 구현해야함
public interface Observer {
  public abstract void update(NumberGenerator generator);
}
