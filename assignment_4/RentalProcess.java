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
				System.out.println("Order in progress: " + order.getCar().getBrand() +
													 " " + order.getCar().getModel() +
													 " - Day " + i + "/" + rentalDays);
				Thread.sleep(1000);
			}
			
			notifyObservers();
			order.getCustomer().addPoints(rentalDays);
			synchronized (order.getCar()) {
				system.assignCarToNextCustomer(order.getCar());
			}
			system.rentalFinished();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
