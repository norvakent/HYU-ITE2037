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

  // Create a handler bound to a rental system instance
  public CSVHandler(CarRentalSystem system) {
    this.system = system;
  }

  @Override
  // Save the current system state to a CSV file
  public boolean saveData(String filename) throws IOException {
    File file = null;
    try {
      file = new File(filename);
      if (file.exists()) {
        file.delete();
        file.createNewFile();
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found. Creating new file...");
      file.createNewFile();
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
    }

    return true;
  }

  @Override
  // Read system data from a CSV file and populate the CarRentalSystem
  public CarRentalSystem loadData(String filename) throws IOException, DataFormatException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      String[] data;

      CarRentalSystem loadedSystem = new CarRentalSystem();
      ArrayList<RentalOrder> loadedOrders = new ArrayList<>();
      int customerCount = 0;
      // Read each line from the file
      while ((line = reader.readLine()) != null) {
        // Split the line by commas to get individual data fields
        data = line.split(",");
        try {
          // Parse car data lines
          if (data[0].equals("CAR") && data.length == 6) {
            // isAvailable flag is ignored when loading
            String brand = data[1];
            String model = data[2];
            int year = Integer.parseInt(data[3]);
            double dailyRate = Double.parseDouble(data[4]);
            // create a new Car object
            Car car = new Car(brand, model, year, dailyRate);
            loadedSystem.addCar(car);
          // Parse order data lines
          } else if (data[0].equals("ORDER") && data.length == 10) {
            // carIsAvailable and totalPrice fields are ignored
            String customerName = data[1];
            boolean isVIP = Boolean.parseBoolean(data[2]);
            String carBrand = data[3];
            String carModel = data[4];
            int carYear = Integer.parseInt(data[5]);
            double carDailyRate = Double.parseDouble(data[6]);
            int rentalDays = Integer.parseInt(data[8]);
            
            // locate the referenced car
            Car car = loadedSystem.getCars().get(loadedSystem.getCarIndexByInfo(new Car(carBrand, carModel, carYear, carDailyRate)));
            Customer customer = new Customer(customerName, isVIP);
            loadedSystem.addCustomer(customer);
            customerCount++;
            // load the order
            if (rentalDays > 7) {
              loadedOrders.add(new LongTermRental(car, customer, rentalDays, true));
            }
            else {
              loadedOrders.add(new ShortTermRental(car, customer, rentalDays, true));
            }
          } else {
            // unknown line format
            throw new DataFormatException();
          }
        } catch (DataFormatException e) {
          System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
          System.out.println("Invalid number format.");
        }
      }
      this.system = new CarRentalSystem(loadedSystem.getCars(), loadedSystem.getCustomers(), customerCount, loadedOrders);
    } catch (FileNotFoundException e) {
      System.out.println("File not found. Data loading failed.");
      throw e;
    }

    return this.system;
  }
}
