package assignment_3;

import java.io.IOException;
import java.util.zip.DataFormatException;

public class CSVHandler implements FileHandler<CarRentalSystem> {
  private CarRentalSystem system;

  public CSVHandler(CarRentalSystem system) {

  }

  @Override
  public boolean saveData(String filename) throws IOException {

  }

  @Override
  public CarRentalSystem loadData(String filename) throws IOException, DataFormatException {

  }
}
