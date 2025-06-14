package assignment_4;

public class DataFormatException extends Exception {
  // Default constructor
  public DataFormatException() {
    super("Skipped line due to invalid format.");
  }

  // Constructor that accepts a message
  public DataFormatException(String message) {
    super(message);
  }
}
