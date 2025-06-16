package assignment_4;

import java.util.ArrayList;

public class OrderManager implements RentalObserver {
	private ArrayList<String> logs;
	private int completedOrders;

	public OrderManager() {
		logs = new ArrayList<String>();
		completedOrders = 0;
	}

	@Override
	public void update(RentalOrder order) {
		Car returnedCar = order.getCar();
		logs.add("Order completed: Customer " + order.getCustomer().getName() +
											 " has returned car " + returnedCar.getBrand() +
											 " " + returnedCar.getModel() +
											 "\nCar " + returnedCar.getBrand() +
											 " " + returnedCar.getModel() +
											 " is now available");
		returnedCar.setAvailability(true);
		System.out.println(logs.get(completedOrders++));
	}

	public void printAllLogs() {
		for (String s : logs) {
			System.out.println(s);
			System.out.flush();
		}
	}

	public int getCompletedOrders() {
		return completedOrders;
	}
}
