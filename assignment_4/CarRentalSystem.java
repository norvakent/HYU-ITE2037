package assignment_4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class CarRentalSystem {
  private ArrayList<Car> cars;             // cars registered in the system
  private int carCount;                    // number of cars
  private Customer[] customers;            // registered customers
  private int customerCount;               // number of customers
  private ArrayList<RentalOrder> orders;   // existing rental orders
  private int orderCount;                  // number of orders
  private FileHandler<CarRentalSystem> fileHandler; // persistence helper
  private HashMap<Car, Queue<WaitingCustomer>> waitingList;
  private OrderManager observer;
  private int activeRentals;

  // Create a new empty rental system
  public CarRentalSystem() {
    this.cars = new ArrayList<Car>();
    this.carCount = 0;
    this.customers = new Customer[0];
    this.customerCount = 0;
    this.orders = new ArrayList<RentalOrder>();
    this.orderCount = 0;
    // CSV persistence handler
    this.fileHandler = new CSVHandler(this);
    this.waitingList = new HashMap<Car, Queue<WaitingCustomer>>();
    this.observer = new OrderManager();
    this.activeRentals = 0;
  }

  public CarRentalSystem(ArrayList<Car> cars, Customer[] customers, int customerCount, ArrayList<RentalOrder> rentalOrders) {
    this.cars = cars;
    this.carCount = cars.size();
    this.customers = customers;
    this.customerCount = customerCount;
    this.orders = rentalOrders;
    this.orderCount = rentalOrders.size();
    this.fileHandler = null;
    this.waitingList = null;
    this.observer = null;
    this.activeRentals = 0;
  }

  // Add a new car to the system
  public void addCar(Car car) {
    this.carCount++;
    car.setAvailability(true); // mark car as available
    this.cars.add(car);
  }

  // Display only available cars
  public void listAvailableCars() {
    // exception handling for no available cars
    if (!hasAvailableCars()) {
      System.out.println("No cars available for rent.");
      return;
    }
    // iterate and display available cars
    for (int i=0; i<this.carCount; i++) {
      if (this.cars.get(i).isAvailable()) {
        System.out.println(i + ": " + this.cars.get(i).toString());
      }
    }
  }

  // Display every car regardless of availability
  public void listAllCars() {
    if (carCount == 0) {
      System.out.println("No cars registered.");
      return;
    }
    for (int i = 0; i < this.carCount; i++) {
      System.out.println(i + ": " + this.cars.get(i).toString());
    }
  }

  // Register a new customer
  public void addCustomer(Customer customer) {
    // resize array if full
    if (this.customerCount == this.customers.length) {
      Customer[] newCustomers = new Customer[this.customerCount + 10];
      for (int i=0; i<this.customers.length; i++) {
        newCustomers[i] = this.customers[i];
      }
      this.customers = newCustomers;
    }
    this.customers[customerCount++] = customer;
  }

  // Display all registered customers
  public void listCustomers() {
    if (this.customerCount == 0) {
      System.out.println("No customers registered.");
      return;
    }
    System.out.println("Registered Customers:");
    for (int i=0; i<this.customerCount; i++) {
      System.out.println((i+1) + ". " + this.customers[i].toString());
    }
  }

  // Check if a customer with given name exists
  public boolean customerExists(String name) {
    for (int i=0; i<this.customerCount; i++) {
      if (this.customers[i].getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  // Rent a car to a customer
  public void rentCar(String customerName, int carIndex, int rentalDays) {
    // invalid car index
    if (carIndex < 0 || carIndex >= this.carCount) {
      System.out.println("Invalid car selection.");
      return;
    }
    // unregistered customer
    if (!this.customerExists(customerName)) {
      System.out.println("Customer not found. Please register first.");
      return;
    }
    
    Car car = this.cars.get(carIndex);

    Customer customer = null;
    for (int i = 0; i < this.customerCount; i++) {
      if (this.customers[i].getName().equals(customerName)) {
        customer = this.customers[i];
        break;
      }
    }

    if (!car.isAvailable()) {
      System.out.println("Selected car is not available.");
      System.out.println("Customer " + customerName +
                         " (days: " + rentalDays +
                         ") added to the waiting list for car " +
                         car.getBrand() + " " + car.getModel());
      addCustomerToQueue(car, customer, rentalDays);
      return;
    }

    RentalOrder order;
    // choose rental type based on days
    if (rentalDays > 7) {
      order = new LongTermRental(car, customer, rentalDays);
    }
    else {
      order = new ShortTermRental(car, customer, rentalDays);
    }
    
    this.orderCount++;
    this.orders.add(order);
    System.out.println("Rental created successfully!");
    System.out.println(order.toString());

    RentalProcess process = new RentalProcess(order, this);
    process.addObserver(this.observer);
    process.start();
  }

  // Check if there is at least one available car
  public boolean hasAvailableCars() {
    for (int i=0; i<this.carCount; i++) {
      if (this.cars.get(i).isAvailable()) {
        return true;
      }
    }
    return false;
  }

  // Display all rental orders
  public void listOrders() {
    if (orderCount == 0) {
      System.out.println("No rental orders found.");
      return;
    }
    for(int i = 0; i < this.orderCount; i++) {
      System.out.println(this.orders.get(i).toString());
    }
  }

  // Save data to a file (exceptions propagate)
  public void saveAllData(String filename) throws IOException {
    fileHandler.saveData(filename);
  }

  // Load data from a file (exceptions propagate)
  public void loadAllData(String filename) throws IOException, DataFormatException{
    CarRentalSystem loadedSystem = fileHandler.loadData(filename);
    this.cars = loadedSystem.cars;
    this.carCount = loadedSystem.carCount;
    this.customers = loadedSystem.customers;
    this.customerCount = loadedSystem.customerCount;
    this.orders = loadedSystem.orders;
    this.orderCount = loadedSystem.orderCount;
  }
  
  // Expose internal collections (used by persistence)
  public ArrayList<Car> getCars() {
    return this.cars;
  }

  public ArrayList<RentalOrder> getOrders() {
    return this.orders;
  }

  public Customer[] getCustomers() {
    return this.customers;
  }

  // getter for car index by car info
  public int getCarIndexByInfo(Car car) {
    for (Car c : this.cars) {
      if (c.getBrand().equals(car.getBrand()) &&
          c.getModel().equals(car.getModel()) &&
          c.getYear() == car.getYear() &&
          c.getDailyRate() == car.getDailyRate()) {
        return this.cars.indexOf(c);
      }
    }
    return -1;
  }

  // Assign the next waiting customer to the given car
  public void assignCarToNextCustomer(Car car) {
    WaitingCustomer wc = getNextWaitingCustomer(car);
    if (wc == null) return;
    rentCar(wc.getCustomer().getName(), cars.indexOf(car), wc.getRentalDays());
  }

  // Add a customer to the waiting queue for a car
  public void addCustomerToQueue(Car car, Customer customer, int rentalDays) {
    waitingList.putIfAbsent(car, new LinkedList<>());
    waitingList.get(car).offer(new WaitingCustomer(customer, rentalDays));
  }

  // public void registerObserver(RentalObserver observer) {
  //   observers.add(observer);
  // }

  // public void removeObserver(RentalObserver observer) {
  //   observers.remove(observer);
  // }

  // public void rentalFinished() {
  //   synchronized (this) {
  //     if (activeRentals > 0) activeRentals--;
  //     this.notifyAll();
  //   }
  // }

  // Print all log messages collected by the observer
  public void printRentalLogs() {
    observer.printAllLogs();
  }

  // Retrieve and remove the next customer waiting for a car
  public WaitingCustomer getNextWaitingCustomer(Car car) {
    Queue<WaitingCustomer> queue = waitingList.get(car);
    if (queue != null && !queue.isEmpty()) {
      WaitingCustomer nextCustomer = queue.poll();
      if (queue.isEmpty()) waitingList.remove(car);
      return nextCustomer;
    }
    return null;
  }

  // public void startRentalProcess(RentalOrder order) {
  // }

  // Display the entire waiting list
  public void printWaitingList() {
    if (waitingList.isEmpty()) {
      System.out.println("Waiting list is empty.");
      return;
    }
    for (Map.Entry<Car, Queue<WaitingCustomer>> entry : waitingList.entrySet()) {
      System.out.println(entry.getKey().toString());
      for (WaitingCustomer wc : entry.getValue()) {
        System.out.println(" - " + wc.getCustomer().getName() + " (" + wc.getRentalDays() + " days)");
      }
    }
  }

  // Limit concurrent rentals to three
  public synchronized boolean tryAcquireRentalSlot() {
    while (activeRentals >= 3) {
      try {
        wait();
      } catch (InterruptedException e) {
        return false;
      }
    }
    activeRentals++;
    return true;
  }

  // Called when a rental thread finishes
  public synchronized void releaseRentalSlot() {
    if (activeRentals > 0) activeRentals--;
    notifyAll();
  }


  public static void main(String[] args) {
    // Basic command line interface
    Scanner scanner = new Scanner(System.in);

    CarRentalSystem rentalSystem = new CarRentalSystem();

    int option = 0; // default value is 0
    String filename = "rental_data.csv"; // default filename for auto-saving/loading
    // try to load data from the default file when the program starts
    try {
      rentalSystem.loadAllData(filename);
      System.out.println("System data automatically loaded from " + filename + "\n");
    } catch (FileNotFoundException e) {
      System.out.println("Automatic loading failed: rental_data.csv not found.");
    } catch (DataFormatException e) {
      System.out.println("Skipped line due to invalid format.");
    } catch (IOException e) {
      System.out.println("I/O error occurred while accessing file.");
    }

    // menu loop
    while (option != 12) {
      // display menu
      System.out.println("Welcome to the Car Rental System!");
      System.out.println("1. Add Car");
      System.out.println("2. List All Cars");
      System.out.println("3. Register Customer");
      System.out.println("4. List Customers");
      System.out.println("5. Rent a Car");
      System.out.println("6. View Rental Orders");
      System.out.println("7. Save Data");
      System.out.println("8. Load Data");
      System.out.println("9. View Customer Points");
      System.out.println("10. View Rental Logs");
      System.out.println("11. View Waiting List");
      System.out.println("12. Exit");
      System.out.print("Select option: ");

      // read user input
      try {
        option = scanner.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("Invalid input type. Please enter a number.\n");
        option = 0;
      } finally {
        scanner.nextLine(); // ignore newline character
      }

      // branch based on user input
      switch (option) {

        case 1: // add a new car to the system
          try {
            // prompt user for car details
            System.out.print("Enter brand: ");
            String brand = scanner.nextLine();
            System.out.print("Enter model: ");
            String model = scanner.nextLine();

            // read year throws InputMismatchException
            System.out.print("Enter year: ");
            int year;
            year = scanner.nextInt();
            scanner.nextLine(); // ignore newline character

            // read daily rate throws InputMismatchException
            System.out.print("Enter daily rate: ");
            double dailyRate = scanner.nextDouble();
            scanner.nextLine(); // ignore newline character

            // create a new Car object and add it to the system
            Car car = new Car(brand, model, year, dailyRate);
            if (rentalSystem.getCarIndexByInfo(car) == -1) {
            rentalSystem.addCar(car);
            System.out.println("Car added successfully!\n");
            }
            else System.out.println("This car already exists in the system.\n");
          } catch (InputMismatchException e) {
            scanner.nextLine(); // clear buffer
            System.out.println("Invalid input type. Please enter a number.\n");
          }
          break;

        case 2: // display all cars
          rentalSystem.listAllCars();
          System.out.println();
          break;

        case 3: // Register Customer
          System.out.print("Enter customer name: ");
          String name = scanner.nextLine();

          // prevent duplicate name
          if (rentalSystem.customerExists(name)) {
            System.out.println("Customer already exists.\n");
            break;
          }

          // ask for VIP status
          System.out.print("Is the customer a VIP? (yes/no): ");
          String input = scanner.nextLine().trim().toLowerCase();
          boolean isVIP;

          // handle VIP input
          if (input.equals("y") || input.equals("yes")) {
            isVIP = true;
          }
          else if (input.equals("n") || input.equals("no")) {
            isVIP = false;
          }
          else {
            System.out.println("Invalid input.\n");
            break;
          }

          Customer customer = new Customer(name, isVIP);
          rentalSystem.addCustomer(customer);
          
          System.out.println("Customer registered succesfully!\n");
          break;

        case 4: // display registered customers
          rentalSystem.listCustomers();
          System.out.println();
          break;

        case 5: // rent a car
          try {
            // prompt user for rental details
            System.out.print("Enter customer name: ");
            String customerName = scanner.nextLine();
            rentalSystem.listAllCars();

            System.out.print("Enter car index to rent: ");
            int carIndex;
            carIndex = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            System.out.print("Enter number of rental days: ");
            int rentalDays;
            rentalDays = scanner.nextInt();
            scanner.nextLine(); // ignore newline character

            rentalSystem.rentCar(customerName, carIndex, rentalDays);
            System.out.println();
          } catch (InputMismatchException e) {
            scanner.nextLine(); // clear buffer
            System.out.println("Invalid input type. Please enter a number.\n");
          }
          break;

        case 6: // display all rental orders
          rentalSystem.listOrders();
          System.out.println();
          break;

        case 7:
          try {
            System.out.print("Enter filename to save (e.g., data.csv): ");
            filename = scanner.nextLine();
            rentalSystem.saveAllData(filename);
            System.out.println("System data saved to " + filename + "\n");
          } catch (IOException e) {
            System.out.println("I/O error occurred while accessing file.");
          }
          break;

        case 8:
          try {
            System.out.print("Enter filename to load (e.g., data.csv): ");
            filename = scanner.nextLine();
            rentalSystem.loadAllData(filename);
            System.out.println("System data loaded from " + filename + "\n");
          } catch (DataFormatException e) {
            System.out.println("Skipped line due to invalid format.");
          } catch (IOException e) {
            System.out.println("I/O error occurred while accessing file.");
          }
          break;

        case 9:
          ArrayList<Customer> rankedCustomers = new ArrayList<Customer>();
          for (int i = 0; i < rentalSystem.customerCount; i++) {
            rankedCustomers.add(rentalSystem.customers[i]);
          }
          rankedCustomers.sort((a, b) -> Integer.compare(b.getPoints(), a.getPoints()));
          System.out.println("Customer Points:");
          for (Customer c : rankedCustomers) {
            System.out.println(c.getName() + ": " + c.getPoints() + " points");
          }
          System.out.println();
          break;
        
        case 10:
          rentalSystem.observer.printAllLogs();
          System.out.println();
          break;

        case 11:
          rentalSystem.printWaitingList();
          System.out.println();
          break;

        case 12: // exit the program
          // try to save data to the default file before exiting
          try {
            rentalSystem.saveAllData("rental_data.csv");
            System.out.println("System data automatically saved to rental_data.csv");
          } catch (IOException e) {
            System.out.println("I/O error occurred while accessing file.");
          }
          System.out.println("Thank you for using the Car Rental System!");
          break;

        default: // invalid menu input
          break;
      }
    }

    scanner.close(); // cleanup scanner
  }
}
