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
  private ArrayList<Car> cars;             // list of cars in the system
  private int carCount;                    // number of cars
  private Customer[] customers;            // list of registered customers
  private int customerCount;               // number of customers
  private ArrayList<RentalOrder> orders;   // list of rental orders
  private int orderCount;                  // number of rental orders
  private FileHandler<CarRentalSystem> fileHandler; // file handler for loading and saving data
	private HashMap<Car, Queue<WaitingCustomer>> waitingList;
  private OrderManager observer;
  private int activeRentals;

  // constructor initializes empty arrays
  public CarRentalSystem() {
    this.cars = new ArrayList<Car>();
    this.carCount = 0;
    this.customers = new Customer[0];
    this.customerCount = 0;
    this.orders = new ArrayList<RentalOrder>();
    this.orderCount = 0;
    // initialize file handler for CSV operations
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

  // method to add a car to the system
  public void addCar(Car car) {
    this.carCount++;
    car.setAvailability(true); // mark car as available
    this.cars.add(car);
  }

  // method to display all available cars
  public void listAvailableCars() {
    // exception handling for no available cars
    if (!hasAvailableCars()) {
      System.out.println("No cars available for rent.");
      return;
    }
    // iterate and display only available cars.
    for (int i=0; i<this.carCount; i++) {
      if (this.cars.get(i).isAvailable()) {
        System.out.println(i + ": " + this.cars.get(i).toString());
      }
    }
  }

  public void listAllCars() {
    if (carCount == 0) {
      System.out.println("No cars registered.");
      return;
    }
    for (int i = 0; i < this.carCount; i++) {
      System.out.println(i + ": " + this.cars.get(i).toString());
    }
  }

  // method to add a customer to the system
  public void addCustomer(Customer customer) {
    // resize array if full
    if (this.customerCount == this.customers.length) {
      Customer[] newCustomers = new Customer[this.customerCount + 10];
      for (int i=0; i<this.customers.length; i++) {
        newCustomers[i] = this.customers[i];
      }
      this.customers = newCustomers;
    }
    // add customer
    this.customers[customerCount++] = customer;
  }

  // method to display all registered customers
  public void listCustomers() {
    // exception handling for no registered customers
    if (this.customerCount == 0) {
      System.out.println("No customers registered.");
      return;
    }
    // iterate and display customer info
    System.out.println("Registered Customers:");
    for (int i=0; i<this.customerCount; i++) {
      System.out.println((i+1) + ". " + this.customers[i].toString());
    }
  }

  // method to check if a customer with given name exists
  public boolean customerExists(String name) {
    for (int i=0; i<this.customerCount; i++) {
      if (this.customers[i].getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  // method to rent a car to a customer
  public void rentCar(String customerName, int carIndex, int rentalDays) {
    // exception handling for invalid car index
    if (carIndex < 0 || carIndex >= this.carCount) {
      System.out.println("Invalid car selection.");
      return;
    }
    // exception handling for unavailable car
    // if (!this.cars.get(carIndex).isAvailable()) {
    //   System.out.println("Selected car is not available.");
    //   return;
    // }
    // exception handling for unregistered customer
    if (!this.customerExists(customerName)) {
      System.out.println("Customer not found. Please register first.");
      return;
    }
    
    // select car
    Car car = this.cars.get(carIndex);
    
    // select customer
    Customer customer = null;
    // find the customer object by name
    // this is guaranteed to exist because we checked before with customerExists method
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

    // create order
    RentalOrder order;
    // determine rental type based on rental days
    if (rentalDays > 7) {
      order = new LongTermRental(car, customer, rentalDays);
    }
    else {
      order = new ShortTermRental(car, customer, rentalDays);
    }
    
    // store order and confirm
    this.orderCount++;
    this.orders.add(order);
    System.out.println("Rental created successfully!");
    System.out.println(order.toString());

    RentalProcess process = new RentalProcess(order, this);
    process.addObserver(this.observer);
    process.start();
  }

  // method to check the system has available cars
  public boolean hasAvailableCars() {
    // iterate and if found available car then return true
    for (int i=0; i<this.carCount; i++) {
      if (this.cars.get(i).isAvailable()) {
        return true;
      }
    }
    return false;
  }

  // method to display all rental orders
  public void listOrders() {
    // exception handling for no orders.
    if (orderCount == 0) {
      System.out.println("No rental orders found.");
      return;
    }
    // iterate and display order info
    for(int i = 0; i < this.orderCount; i++) {
      System.out.println(this.orders.get(i).toString());
    }
  }

  // method to save data to a file
  // exception will be handled by the caller
  public void saveAllData(String filename) throws IOException {
    fileHandler.saveData(filename);
  }

  // method to load data from a file
  // exception will be handled by the caller
  public void loadAllData(String filename) throws IOException, DataFormatException{
    CarRentalSystem loadedSystem = fileHandler.loadData(filename);
    this.cars = loadedSystem.cars;
    this.carCount = loadedSystem.carCount;
    this.customers = loadedSystem.customers;
    this.customerCount = loadedSystem.customerCount;
    this.orders = loadedSystem.orders;
    this.orderCount = loadedSystem.orderCount;
  }
  
  // below 3 getters are not safety
  // getter for cars list
  public ArrayList<Car> getCars() {
    return this.cars;
  }

  // getter for orders list
  public ArrayList<RentalOrder> getOrders() {
    return this.orders;
  }

  // getter for customers list
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

  public void assignCarToNextCustomer(Car car) {
    WaitingCustomer wc = getNextWaitingCustomer(car);
    if (wc == null) return;
    rentCar(wc.getCustomer().getName(), cars.indexOf(car), wc.getRentalDays());
  }

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

  public void printRentalLogs() {
    observer.printAllLogs();
  }

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

  public synchronized void releaseRentalSlot() {
    if (activeRentals > 0) activeRentals--;
    notifyAll();
  }


  public static void main(String[] args) {
    // initialize scanner for user input
    Scanner scanner = new Scanner(System.in);

    // create a instance of CarRentalSystem class
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

    // service loop works until the user selects option 12
    while (option != 12) {
      // display welcome message and menu options
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
        // handle invalid option input
        System.out.println("Invalid input type. Please enter a number.\n");
        // reset option to 0 to loop back to the menu
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
              // display success message
              System.out.println("Car added successfully!\n");
            }
            else System.out.println("This car already exists in the system.\n");
          } catch (InputMismatchException e) {
            // handle invalid input type for year or daily rate
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

          // hanle VIP input
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

          // register the customer
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
            // exception handling for no available cars
            // if (!rentalSystem.hasAvailableCars()) {
            //   System.out.println("No cars available for rent.\n");
            //   break;
            // }

            // prompt user for rental details
            System.out.print("Enter customer name: ");
            String customerName = scanner.nextLine();
            // rentalSystem.listAvailableCars();
            rentalSystem.listAllCars();

            // read car index throws InputMismatchException
            System.out.print("Enter car index to rent: ");
            int carIndex;
            carIndex = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            // read rental days throws InputMismatchException
            System.out.print("Enter number of rental days: ");
            int rentalDays;
            rentalDays = scanner.nextInt();
            scanner.nextLine(); // ignore newline character

            // process rental
            rentalSystem.rentCar(customerName, carIndex, rentalDays);
            System.out.println();
          } catch (InputMismatchException e) {
            // handle invalid input type for car index or rental days
            scanner.nextLine(); // clear buffer
            System.out.println("Invalid input type. Please enter a number.\n");
          }
          break;

        case 6: // dlsplay all rental orders
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
            // handle IOException
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
            // handle DataFormatException
            System.out.println("Skipped line due to invalid format.");
          } catch (IOException e) {
            // handle IOException
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
          // entering 9 will exit the loop and the program
          // at the condition of this while loop
          // display exit message
          
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
          // loop back to the menu if the user enters an invalid option
          break;
      }
    }

    scanner.close(); // cleanup scanner
  }
}
