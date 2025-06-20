package assignment_4;

import java.util.ArrayList;
import java.util.List;

public class RentalProcess extends Thread {
	private RentalOrder order;
	private List<RentalObserver> observers;
	private CarRentalSystem system;

	public RentalProcess(RentalOrder order, CarRentalSystem system) {
		this.order = order;
		observers = new ArrayList<>();
		this.system = system;
	}

	public void addObserver(RentalObserver obs) {
		observers.add(obs);
	}

	private void notifyObservers() {
		for (RentalObserver obs : observers) {
			obs.update(order);
		}
	}

	@Override
	public void run() {
		try {
			int rentalDays = order.getRentalDays();
			for (int i = 1; i <= rentalDays; i++) {
				System.out.println("[" + order.getCustomer().getName() + "] Rental day " + i + "/" + rentalDays);
				Thread.sleep(1000);
			}

			synchronized (order.getCar()) {
				order.getCar().setAvailability(true);
			}
			order.getCustomer().addPoints(rentalDays);

			notifyObservers();
			if (system.getNextWaitingCustomer(order.getCar()) != null) {
				system.assignCarToNextCustomer(order.getCar());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
