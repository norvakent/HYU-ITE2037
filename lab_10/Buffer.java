package lab_10;

public class Buffer {
  private int loc;
  private double[] data;

  public Buffer(int size) {
    this.loc = 0;
    this.data = new double[size];
    System.out.println("New buffer");
    System.out.println(this);
  }

  public int getSize() {
    return data.length;
  }

  public synchronized void add(double toAdd) throws InterruptedException {
    String threadName = "Producer#" + Thread.currentThread().getName().split("-")[1];
    while (loc >= data.length) {
      System.out.println(threadName + " try " + loc + " @ Buffer is full.\n");
      wait();
    }
    System.out.println(threadName + " Adding item on " + loc + ": " + toAdd);
    data[loc++] = toAdd;
    System.out.println(this);
    System.out.flush();
    notifyAll();
  }

  public synchronized double remove() throws InterruptedException {
    String threadName = "Consumer#" + (Integer.parseInt(Thread.currentThread().getName().split("-")[1])-2);
    while (loc <= 0) {
      System.out.println(threadName + " try " + loc + " O Buffer is empty.\n");
      wait();
    }
    double temp = data[--loc];
    System.out.println(threadName + " Removing item on " + loc + ": " + temp);
    data[loc] = 0.0;
    System.out.println(this);
    System.out.flush();
    notifyAll();
    return temp;
  }

  @Override
  public String toString() {
    String s = "";
    for (double d : data) s += String.format("%.2f ", d);
    return s;
  }
}
