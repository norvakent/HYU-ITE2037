package lab_11;

// GraphObserver: random number만큼 *개수로 출력
public class GraphObserver implements Observer {
  public GraphObserver(NumberGenerator generator) {
    generator.addObserver(this);
  }

  @Override
  public void update(NumberGenerator generator) {
    String graph = "";
    for (int i = 0; i < generator.getNumber(); i++) {
      graph += "*";
    }
    System.out.println("GraphObserver: " + graph);
  }
}
