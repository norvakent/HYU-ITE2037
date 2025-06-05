package lab_10;

public class ProdCons {
  private Buffer buffer;
  private Producer[] producer;
  private Consumer[] consumer;

  public ProdCons() {
    this.buffer = new Buffer(5);
    producer = new Producer[2];
    consumer = new Consumer[2];
    producer[0] = new Producer(buffer);
    producer[1] = new Producer(buffer);
    consumer[0] = new Consumer(buffer);
    consumer[1] = new Consumer(buffer);
  }

  private class Producer extends Thread {
    private final Buffer buffer;

    public Producer(Buffer buffer) {
      this.buffer = buffer;
    }

    public void produce() throws InterruptedException {
      for (int i = 0; i < buffer.getSize(); i++) {
        System.out.println("Producer#" + 
                            this.getName().split("-")[1] +
                            ":" + this);
        buffer.add(Math.random() * 100);
      }
    }

    public void run() {
      try {
        produce();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private class Consumer extends Thread {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
      this.buffer = buffer;
    }

    public void consume() throws InterruptedException {
      for (int i = 0; i < buffer.getSize(); i++) {
        System.out.println("Consumer#" + 
                            (Integer.parseInt(this.getName().split("-")[1]) - 2) +
                            ":" + this);
        buffer.remove();
      }
    }

    public void run() {
      try {
        consume();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return;
    }
  }

  public void startThread() {
    for (Producer p : producer) p.start();
    for (Consumer c : consumer) c.start();
  }
}
