package assignment_4;

import java.util.ArrayList;

public class RentalProcess extends Thread {
	private RentalOrder order;
	private ArrayList<RentalObserver> observers;

	public RentalProcess(RentalOrder order) {
		this.order = order;
		observers = new ArrayList<RentalObserver>();
	}

	public void run() {
		for (RentalObserver obs : observers) {
		}
	}

	public void addObserver(RentalObserver obs) {
		observers.add(obs);
	}

	private void notifyObservers() {
	}
}
