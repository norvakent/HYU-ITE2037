package lab_8;

import java.util.Scanner;

public class Market {
  public static void main(String[] args) {
    Wallet wallet;
    int expense;

    Scanner scanner = new Scanner(System.in);
    wallet = new Wallet(scanner.nextLine());

    while(true) {
      try {
        wallet.empty();
        System.out.print("Enter price: ");
        expense = scanner.nextInt();
        scanner.nextLine();
        if (expense < 0) {
          throw new NegativeException();
        }
        else if (expense > 100) {
          throw new TooMuchExpenseException(expense);
        }
        else if (expense > wallet.getBalance()) {
          throw new TooMuchExpenseException();
        }

        wallet.increaseIndex();
        wallet.decreaseBalance(expense);
        System.out.println("peace~~");
      }
      catch (NegativeException e) {
        System.out.println(e);
        System.out.println("\tat " + e.getStackTrace()[0]);
        System.out.println("oh, sorry!");
      }
      catch (TooMuchExpenseException e) {
        System.out.println(e);
        System.out.println("\tat " + e.getStackTrace()[0]);
        if (e.getMessage().equals("Over the limit!")) {
          System.out.println("you pay " + e.getInputNum());
        }
        System.out.println("oh, my!");
      }
      catch (Exception e) {
        if (e.getMessage().equals("Go Home")) {
          System.out.println(e);
          System.out.println("\tat " + e.getStackTrace()[0]);
          System.out.println("the end...");
          scanner.close();
          return;
        }
      }
      finally {
        System.out.println(wallet.toString());
        System.out.println("---transaction complete---\n");
      }
    }
  }
}
