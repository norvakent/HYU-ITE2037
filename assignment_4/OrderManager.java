package assignment_4;

public class OrderManager implements RentalObserver {
	private ArrayList<String> logs;
	private int completedOrders;

	public OrderManager() {
		logs = new ArrayList<String>();
		completedOrders = 0;
	}

	public void update(RentalOrder order) {
		completedOrders++;
		//
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
