package assignment_3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CarRentalSystem {
  private ArrayList<Car> cars;             // list of cars in the system
  private int carCount;           // number of cars
  private Customer[] customers;   // list of registered customers
  private int customerCount;      // number of customers
  private ArrayList<RentalOrder> orders;   // list of rental orders
  private int orderCount;         // number of rental orders
  private FileHandler<CarRentalSystem> fileHandler;

  // constructor initializes empty arrays
  public CarRentalSystem() {
    this.cars = new ArrayList<Car>();
    this.carCount = 0;
    this.customers = new Customer[0];
    this.customerCount = 0;
    this.orders = new ArrayList<RentalOrder>();
    this.orderCount = 0;
    this.fileHandler = new CSVHandler(this);
  }

  // method to add a car to the system
  public void addCar(Car car) {
    // resize array if full
    // if (this.carCount == this.cars.length) {
    //   Car[] newCars = new Car[this.carCount + 10];
    //   for (int i=0; i<this.cars.length; i++) {
    //     newCars[i] = this.cars[i];
    //   }
    //   this.cars = newCars;
    // }
    this.carCount++;
    car.setAvailability(true); // mark car as available
    this.cars.add(car);
    return;
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
    return;
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
    return;
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
    return;
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
    if (!this.cars.get(carIndex).isAvailable()) {
      System.out.println("Selected car is not available.");
      return;
    }
    // exception handling for unregistered customer
    if (!this.customerExists(customerName)) {
      System.out.println("Customer not found. Please register first.");
      return;
    }

    // resize array if full
    // if (this.orderCount == this.orders.length) {
    //   RentalOrder[] newOrders = new RentalOrder[this.orderCount + 10];
    //   for (int i=0; i<this.orderCount; i++) {
    //     newOrders[i] = this.orders[i];
    //   }
    //   this.orders = newOrders;
    // }
    
    // select car
    Car car = this.cars.get(carIndex);
    
    // select customer
    Customer customer = null;
    // find the customer object by name
    // this is guaranteed to exist because we checked before with customerExists method
    for (int i=0; i<this.customerCount; i++) {
      if (this.customers[i].getName().equals(customerName)) {
        customer = this.customers[i];
        break;
      }
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
    this.orders.add(order);
    System.out.println("Rental created successfully!");
    System.out.println(order.toString());
    return;
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
    for(int i=0; i<this.orderCount; i++) {
      System.out.println(this.orders.get(i).toString());
    }
    return;
  }

  public void saveAllData(String filename) {
    try {
      if (fileHandler.saveData(filename)) {
        System.out.println("System data saved to " + filename);
      }
    } catch (IOException e) {
      System.out.println("I/O error occurred while accessing file.");
      return;
    }
    return;
  }

  public void loadAllData(String filename) {
    try {
      fileHandler.loadData(filename);
    } catch (DataFormatException e) {
      System.out.println("Skipped line due to invalid format.");
    } catch (IOException e) {
      System.out.println("I/O error occurred while accessing file.");
    }
    return;
  }

  public ArrayList<Car> getCars() {
    return this.cars;
  }

  public ArrayList<RentalOrder> getOrders() {
    return this.orders;
  }

  public Customer[] getCustomers() {
    return this.customers;
  }

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

  public static void main(String[] args) {
    // initialize scanner for user input
    Scanner scanner = new Scanner(System.in);

    // create a instance of CarRentalSystem class
    CarRentalSystem rentalSystem = new CarRentalSystem();
    int option = 0;
    String filename = "";

    // service loop works until the user selects option 7
    while (option != 9) {
      // display welcome message and menu options
      System.out.println("Welcome to the Car Rental System!");
      System.out.println("1. Add Car");
      System.out.println("2. List Available Cars");
      System.out.println("3. Register Customer");
      System.out.println("4. List Customers");
      System.out.println("5. Rent a Car");
      System.out.println("6. View Rental Orders");
      System.out.println("7. Save Data");
      System.out.println("8. Load Data");
      System.out.println("9. Exit");
      System.out.print("Select option: ");

      // read user input
      try {
        option = scanner.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("Invalid input type. Pleasse enter a number.");
        option = 0;
      }
      scanner.nextLine(); // ignore newline character

      // branch based on user input
      switch (option) {

        case 1: // add a new car to the system
          // prompt user for car details
          System.out.print("Enter brand: ");
          String brand = scanner.nextLine();
          System.out.print("Enter model: ");
          String model = scanner.nextLine();

          // read year with exception handling
          System.out.print("Enter year: ");
          int year;
          try {
            year = scanner.nextInt();
          } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Pleasse enter a number.");
            scanner.nextLine(); // clear buffer
            break;
          }
          scanner.nextLine(); // ignore newline character

          // read daily rate with exception handling
          System.out.print("Enter daily rate: ");
          double dailyRate;
          try {
            dailyRate = scanner.nextDouble();
          } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Pleasse enter a number.");
            scanner.nextLine(); // clear buffer
            break;
          }
          scanner.nextLine(); // ignore newline character

          // create a new Car object and add it to the system
          Car car = new Car(brand, model, year, dailyRate);
          rentalSystem.addCar(car);

          // display success message
          System.out.println("Car added successfully!\n");
          break;

        case 2: // display available cars
          rentalSystem.listAvailableCars();
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

        case 4: // display registere customers
          rentalSystem.listCustomers();
          System.out.println();
          break;

        case 5: // rent a car
          // exception handling for no available cars
          if (!rentalSystem.hasAvailableCars()) {
            System.out.println("No cars available for rent.\n");
            break;
          }

          // prompt user for rental details
          System.out.print("Enter customer name: ");
          String customerName = scanner.nextLine();
          rentalSystem.listAvailableCars();

          // read car index with exception handling
          System.out.print("Enter car index to rent: ");
          int carIndex;
          try {
            carIndex = scanner.nextInt();
          } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Pleasse enter a number.");
            scanner.nextLine(); // clear buffer
            break;
          }
          scanner.nextLine(); // ignore newline character

          // read rental days with exception handling
          System.out.print("Enter number of rental days: ");
          int rentalDays;
          try {
            rentalDays = scanner.nextInt();
          } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Pleasse enter a number.");
            scanner.nextLine(); // clear buffer
            break;
          }
          scanner.nextLine(); // ignore newline character

          // process rental
          rentalSystem.rentCar(customerName, carIndex, rentalDays);
          System.out.println();
          break;

        case 6: // dlsplay all rental orders
          rentalSystem.listOrders();
          System.out.println();
          break;

        case 7:
          System.out.print("Enter filename to save (e.g., data.csv): ");
          filename = scanner.nextLine();
          rentalSystem.saveAllData(filename);
          break;

        case 8:
          filename = scanner.nextLine();
          rentalSystem.loadAllData(filename);
          break;

        case 9: // exit the program
          // entering 9 will exit the loop and the program
          // at the condition of this while loop
          // display exit message
          System.out.println("Thank you for using the Car Rental System!");
          break;

        default: // invalid menu input
          // loop back to the menu if the user enters an invalid option
          System.out.println("Invalid option.\n");
          break;
      }
    }

    scanner.close(); // cleanup scanner
    return;
  }
}