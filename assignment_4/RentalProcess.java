package assignment_4;

public class RentalProcess extends Thread {
	private RentalOrder order;
	private ArrayList<RentalObserver> observers;

	public RentalProcess(RentalOrder order) {
		this.order = order;
		observers = new ArrayList<RentalOrder>();
	}

	public void run() {
		for (RentalObserver obs : observers) {
			// obs.run();
		}
	}

	public void addObserver(RentalObserver obs) {
		observers.add(obs);
	}

	private void notifyObservers() {
	}
}
