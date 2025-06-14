package assignment_4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVHandler implements FileHandler<CarRentalSystem> {
  private CarRentalSystem system;

  // constructor initializes the CSVHandler with a CarRentalSystem instance
  public CSVHandler(CarRentalSystem system) {
    this.system = system;
  }

  @Override
  // saveData writes the CarRentalSystem data to a CSV file
  public boolean saveData(String filename) throws IOException {
    File file = null;
    try {
      file = new File(filename);
      // if file exists, load data from it and then create a new file
      // this logic is not exactly overwriting, but updating the file
      if (file.exists()) {
        this.loadData(filename);
        file.delete();
        file.createNewFile();
      }
    } catch (FileNotFoundException e) {
      // handling FileNotFoundException
      System.out.println("File not found. Creating new file...");
      // create a new file if it does not exist
      file.createNewFile();
    } catch (DataFormatException e) {
      // handling DataFormatException
      System.out.println("Skipped line due to invalid format.");
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
      // write car data
      for (Car car : system.getCars()) {
        writer.write(String.format("CAR,%s,%s,%d,%f,%b%n",
            car.getBrand(), car.getModel(), car.getYear(), car.getDailyRate(), car.isAvailable()));
      }

      // write order data
      for (RentalOrder order : system.getOrders()) {
        Customer c = order.getCustomer();
        Car car = order.getCar();
        writer.write(String.format("ORDER,%s,%b,%s,%s,%d,%f,%b,%d,%f%n",
            c.getName(), c.isVIP(),
            car.getBrand(), car.getModel(), car.getYear(),
            car.getDailyRate(), car.isAvailable(),
            order.getRentalDays(), order.calculateTotalPrice()));
      }
      // IOException is handled by the caller
    }
    // BufferedWriter will automatically close after try-with-resources block

    return true;
  }
  
  @Override
  // loadData reads data from a CSV file and populates the CarRentalSystem
  public CarRentalSystem loadData(String filename) throws IOException, DataFormatException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;   // each line from the file
      String[] data; // split data from each line
      
      // The existingCars list is used to track cars that have already been loaded into the system.
      // This ensures that no duplicate car entries are added when processing the CSV file.
      // Without this check, duplicate car entries could lead to ambiguity when associating rental orders with specific cars.
      // Cars in this list are not added to the system, also associated orders will not be loaded.
      ArrayList<Car> existingCars = new ArrayList<Car>();
      // Read each line from the file
      while ((line = reader.readLine()) != null) {
        // Split the line by commas to get individual data fields
        data = line.split(",");
        try {
          // Checking the first element and the length of data
          if (data[0].equals("CAR") && data.length == 6) {
            // parse the car data
            // isAvaioable is not necessary
            String brand = data[1];
            String model = data[2];
            int year = Integer.parseInt(data[3]);
            double dailyRate = Double.parseDouble(data[4]);
            // boolean isAvailable = Boolean.parseBoolean(data[5]);
            
            // create a new Car object
            Car car = new Car(brand, model, year, dailyRate);
            // determine if the car already exists in the system
            int carIndex = system.getCarIndexByInfo(car);
            // if car does not exist, add it to the system
            // its availability is set to true when calling addCar method, default is false
            if (carIndex == -1) system.addCar(car);
            else {
              System.out.println("This car is duplicated. Associated order will not be loaded.\n" + 
                                  car.toString());
              // If the car already exists, add it to the existingCars list
              existingCars.add(car);
            }

          // Checking the first element and the length of data
          } else if (data[0].equals("ORDER") && data.length == 10) {
            // parse the order data
            // carIsAvailable, totalPrice are not necessary
            String customerName = data[1];
            boolean isVIP = Boolean.parseBoolean(data[2]);
            String carBrand = data[3];
            String carModel = data[4];
            int carYear = Integer.parseInt(data[5]);
            double carDailyRate = Double.parseDouble(data[6]);
            // boolean carIsAvailable = Boolean.parseBoolean(data[7]);
            int rentalDays = Integer.parseInt(data[8]);
            // double totalPrice = Double.parseDouble(data[9]);
            
            // Check if the car exists in the existingCars list
            boolean duplicated = false; // flag to check if the car is duplicated
            for (Car c :existingCars) {
              if (c.getBrand().equals(carBrand) && c.getModel().equals(carModel) && c.getYear() == carYear && c.getDailyRate() == carDailyRate) {
                duplicated = true;
                break;
              }
            }
            if (duplicated) continue; // skip the line if the car is duplicated

            // create a new Car object
            Car car = new Car(carBrand, carModel, carYear, carDailyRate);
            // find the index of the car in the system
            // carIndex cannot be -1
            int carIndex = system.getCarIndexByInfo(car);

            // if customer exists, just update the customer information
            if (system.customerExists(customerName)) {
              System.out.println(customerName + " already exists. updated VIP status.");
              Customer[] customers = system.getCustomers();
              for (int i = 0; i < customers.length; i++) {
                if (customers[i].getName().equals(customerName)) {
                  customers[i] = new Customer(customerName, isVIP);
                  break;
                }
              }
            }
            // else create a new customer
            else {
              Customer customer = new Customer(customerName, isVIP);
              system.addCustomer(customer);
            }

            // load order data with rentCar method
            system.rentCar(customerName, carIndex, rentalDays);
          } else {
            // if the line does not match the expected format, throw DataFormatException
            throw new DataFormatException();
          }
        } catch (DataFormatException e) {
          // default message is "Skipped line due to invalid format."
          System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
          System.out.println("Invalid number format.");
        }
        // above exceptions are caught line by line
      }
    } catch (FileNotFoundException e) {
      // this exception is caught when the file does not exist
      System.out.println("File not found. Data loading failed.");
      throw e;
    }
    // reader will automatically close after try-with-resources block

    return this.system;
  }
}
