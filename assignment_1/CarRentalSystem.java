package assignment_1;

import java.util.Scanner;

public class CarRentalSystem {
  private Car[] cars;   // an array of cars in the system
  private int carCount; // number of cars in the system

  // constructor initializes the car array and count
  public CarRentalSystem() {
    this.cars = new Car[100]; // the system(inventory) can hold up to 100 cars
    this.carCount = 0;
  }

  // method to add a car to the system
  public void addCar(Car car) {
    // exception handling for full inventory
    if (this.carCount < this.cars.length) {
      // add the car to the inventory at current count index and increment count
      this.cars[this.carCount++] = car;
    }
    else {
      System.out.println("Inventory is full. Please visit later.");
    }
    return;
  }

  // method to list all available cars
  public void listAvailableCars() {
    System.out.println("Available Cars:");
    // iterate through the car array and print only available cars
    for (int i = 0; i < this.carCount; i++) {
      if (this.cars[i].isAvailable()) {
        System.out.println(i + ": " + this.cars[i].toString());
      }
    }
    return;
  }

  // method to rent a car using the RentalOrder class
  public void rentCar(String customerName, int carIndex, int days) {
    // exception handling for invalid car index
    if (carIndex < 0 || carIndex >= this.carCount) {
      System.out.println("Invalid car index. Please try again.");
    }
    // exception handling for unavailable car
    else if (!this.cars[carIndex].isAvailable()) {
      System.out.println("This car is not available for rent.");
    }
    else {
      // create a instance of RentalOrder class
      // and initialize it with the parameters and class variables
      RentalOrder order = new RentalOrder(this.cars[carIndex], customerName, days);
      System.out.println("Rental successful!");
      System.out.println(order.toString());
    }
    return;
  }

  public static void main(String[] args) {
    // initialize scanner for user input
    Scanner scanner = new Scanner(System.in);

    // create a instance of CarRentalSystem class
    CarRentalSystem rentalSystem = new CarRentalSystem();
    int option = 0;

    // service loop works until the user selects option 4
    while (option != 4) {

      // display welcome message and menu options
      System.out.println("Welcome to the Car Rental System!");
      System.out.println("1. Add Car");
      System.out.println("2. List Available Cars");
      System.out.println("3. Rent a Car");
      System.out.println("4. Exit");
      System.out.print("Select option: ");

      // read user input
      option = scanner.nextInt();
      scanner.nextLine(); // ignore newline character

      // branch based on user input
      switch (option) {

        case 1: // add a new car to the system
          // prompt user for car details
          System.out.print("Enter brand: ");
          String brand = scanner.nextLine();
          System.out.print("Enter model: ");
          String model = scanner.nextLine();
          System.out.print("Enter year: ");
          int year = scanner.nextInt();
          scanner.nextLine(); // ignore newline character
          System.out.print("Enter daily rate: ");
          double dailyRate = scanner.nextDouble();
          scanner.nextLine(); // ignore newline character

          // create a new Car object and add it to the system
          Car car = new Car(brand, model, year, dailyRate);
          car.setAvailability(true); // change status to available
          rentalSystem.addCar(car);

          // display success message
          System.out.println("Car added successfully!\n");
          break;

        case 2: // display available cars
          rentalSystem.listAvailableCars();
          System.out.println();
          break;

        case 3: // rent a car
          // prompt user for rental details
          System.out.print("Enter your name: ");
          String customerName = scanner.nextLine();
          System.out.print("Enter car index to rent: ");
          int carIndex = scanner.nextInt();
          scanner.nextLine(); // ignore newline character
          System.out.print("Enter number of rental days: ");
          int rentalDays = scanner.nextInt();
          scanner.nextLine(); // ignore newline character

          // process the rental
          rentalSystem.rentCar(customerName, carIndex, rentalDays);
          System.out.println();
          break;

        default:
          // loop back to the menu if the user enters an invalid option
          // entering 4 will exit the loop and the program
          // at the condition of this while loop
          break;
      }
    }

    // display exit message
    System.out.println("Thank you for using the Car Rental System!");
    scanner.close();
    return;
  }
}