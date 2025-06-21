package assignment_4;

import java.io.IOException;

// Generic interface for simple persistence helpers
public interface FileHandler<T> {
  boolean saveData(String filename) throws IOException;
  T loadData(String filename) throws IOException, DataFormatException;
}
