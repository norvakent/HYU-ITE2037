package manager;

import account.Account;
import java.time.LocalDate;
import java.util.Random;

public class AccountManager {
  public static void main(String args[]) {
    LocalDate created = LocalDate.of(2021, 12, 1);
    Account account = new Account("Lee", 5.0, created);
    System.out.println(account.toString());
    int months = 0;
    while (account.getBalance() < 10000) {
      if (months / 12 >= 1 && (months % 12 == 0)) {
        Random random = new Random();
        if (random.nextInt(10) == 0) {
          account.receiveIncome(100);
          System.out.println("이벤트 당첨!");
        }
      }
      if (months == 36) {
        account.increaseYearlyInterest(2);
        System.out.println("가입 후 3년이 지나서 이자율이 2% 인상되었습니다.");
      }
      account.receiveIncome(100);
      account.receiveInterest();
      months++;
    }
    System.out.println(account.toString() + ", 1억 모으기 끝" + 
                       account.getCreated().plusMonths(months).toString());
  }
}
