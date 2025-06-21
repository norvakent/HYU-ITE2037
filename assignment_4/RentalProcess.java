package assignment_4;

import java.util.ArrayList;
import java.util.List;

// Thread simulating a rental in progress
public class RentalProcess extends Thread {
        private RentalOrder order;
        private List<RentalObserver> observers;
        private CarRentalSystem system;

        // Prepare a new rental thread
        public RentalProcess(RentalOrder order, CarRentalSystem system) {
                this.order = order;
                observers = new ArrayList<>();
                this.system = system;
        }

        // Subscribe an observer
        public void addObserver(RentalObserver obs) {
                observers.add(obs);
        }

        // Notify all observers of completion
        private void notifyObservers() {
                for (RentalObserver obs : observers) {
                        obs.update(order);
                }
        }

        // Simulate the day-by-day rental
        @Override
        public void run() {
                if (!system.tryAcquireRentalSlot()) return;

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

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.releaseRentalSlot();
		}
	}
}
