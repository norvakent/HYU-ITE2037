package assignment_3;

import java.io.IOException;

public interface FileHandler<T> {
  boolean saveData(String filename) throws IOException;
  T loadData(String filename) throws IOException, DataFormatException;
}
