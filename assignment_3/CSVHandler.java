package assignment_3;

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

  public CSVHandler(CarRentalSystem system) {
    this.system = system;
  }

  @Override
  public boolean saveData(String filename) throws IOException {
    BufferedWriter writer = null;
    File file = null;
    try {
      file = new File(filename);
      if (file.exists()) {
        this.loadData(filename);
        file.delete();
        file.createNewFile();
      }
      writer = new BufferedWriter(new FileWriter(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File not found. Creating new file...");
      file.createNewFile();
      writer = new BufferedWriter(new FileWriter(filename));
    }

    try {
      for (Car car : system.getCars()) {
        writer.write(String.format("CAR,%s,%s,%d,%f,%b%n",
            car.getBrand(), car.getModel(), car.getYear(), car.getDailyRate(), car.isAvailable()));
      }

      for (RentalOrder order : system.getOrders()) {
        Customer c = order.getCustomer();
        Car car = order.getCar();
        writer.write(String.format("ORDER,%s,%b,%s,%s,%d,%f,%b,%d,%f%n",
            c.getName(), c.isVIP(),
            car.getBrand(), car.getModel(), car.getYear(),
            car.getDailyRate(), car.isAvailable(),
            order.getRentalDays(), order.calculateTotalPrice()));
      }
    } finally {
      if (writer != null) writer.close();
    }
    return true;
  }
  
  @Override
  public CarRentalSystem loadData(String filename) throws IOException {
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File not found. Data loading failed.");
      return this.system;
    }

    String line;
    String[] data;
    ArrayList<Car> duplicatedCars = new ArrayList<Car>();
    while ((line = reader.readLine()) != null) {
      data = line.split(",");
      try {
        if (data[0].equals("CAR") && data.length == 6) {
          // Create a new Car object and add it to the system
          String brand = data[1];
          String model = data[2];
          int year = Integer.parseInt(data[3]);
          double dailyRate = Double.parseDouble(data[4]);
          
          Car car = new Car(brand, model, year, dailyRate);
          car.setAvailability(true);
          int carIndex = system.getCarIndexByInfo(car);
          if (carIndex == -1) system.addCar(car);
          else {
            System.out.println("This car is duplicated. Associated order will not be loaded.\n" + 
                                car.toString() + "\n");
            duplicatedCars.add(car);
          }

        } else if (data[0].equals("ORDER") && data.length == 10) {
          // Create a new RentalOrder object and add it to the system
          String customerName = data[1];
          boolean isVIP = Boolean.parseBoolean(data[2]);
          String carBrand = data[3];
          String carModel = data[4];
          int carYear = Integer.parseInt(data[5]);
          double carDailyRate = Double.parseDouble(data[6]);
          // boolean carIsAvailable = Boolean.parseBoolean(data[7]);
          int rentalDays = Integer.parseInt(data[8]);
          // double totalPrice = Double.parseDouble(data[9]);
        
          for (Car c : duplicatedCars) {
            if (c.getBrand().equals(carBrand) && c.getModel().equals(carModel) && c.getYear() == carYear && c.getDailyRate() == carDailyRate) {
              continue;
            }
          }
          Car car = new Car(carBrand, carModel, carYear, carDailyRate);
          car.setAvailability(true);
          int carIndex = system.getCarIndexByInfo(car);

          if (system.customerExists(customerName)) {
            System.out.println(customerName + " already exists. updated VIP status.");
            for (Customer c : system.getCustomers()) {
              if (c.getName().equals(customerName)) {
                c.setVIP(isVIP);
                break;
              }
            }
          }
          else {
            Customer customer = new Customer(customerName, isVIP);
            system.addCustomer(customer);
          }

          system.rentCar(customerName, carIndex, rentalDays);
        } else {
          throw new DataFormatException();
        }
      } catch (DataFormatException e) {
        System.out.println(e.getMessage());
      } catch (NumberFormatException e) {
        System.out.println("Invalid number format.");
      }
    }

    reader.close();
    return this.system;
  }
}
