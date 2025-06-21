package assignment_4;

// Thrown when a CSV line does not match the expected format
public class DataFormatException extends Exception {
  // Default constructor
  public DataFormatException() {
    super("Skipped line due to invalid format.");
  }

  // Constructor that accepts a custom message
  public DataFormatException(String message) {
    super(message);
  }
}
