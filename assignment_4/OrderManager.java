package assignment_4;

import java.util.ArrayList;
import java.util.List;

// Observer that tracks completed orders
public class OrderManager implements RentalObserver {
        private List<String> logs;
        private int completedOrders;

        public OrderManager() {
                logs = new ArrayList<>();
                completedOrders = 0;
        }

        // Called when a rental completes
        @Override
        public void update(RentalOrder order) {
		Car returnedCar = order.getCar();
		String customerName = order.getCustomer().getName();
		String carInfo = returnedCar.getBrand() + " " + returnedCar.getModel();

		String msg1 = "Order completed: Customer " + customerName + " has returned car " + carInfo;
		String msg2 = "Car " + carInfo + " is now available";
		returnedCar.setAvailability(true);

		System.out.println(msg1);
		System.out.println(msg2);
		
		logs.add(msg1);
		logs.add(msg2);
		completedOrders++;
	}

        // Print every logged message
        public void printAllLogs() {
		for (String log : logs) {
			System.out.println(log);
		}
	}

        public int getCompletedOrders() {
                return completedOrders;
        }
}
