package account;

import java.time.LocalDate;

public class Account {
  private String name;
  private double yearlyInterest;
  private double balance;
  private LocalDate created;

  public Account(String name, double yearlyInterest, LocalDate created) {
    this.name = name;
    this.yearlyInterest = yearlyInterest;
    this.created = LocalDate.of(created.getYear(), created.getMonthValue(), created.getDayOfMonth());
    this.balance = 0.0;
  }

  public double getBalance() {
    return balance;
  }

  public LocalDate getCreated() {
    return LocalDate.of(this.created.getYear(), this.created.getMonthValue(), this.created.getDayOfMonth());
  }

  public void increaseYearlyInterest(int byPercent) {
    this.yearlyInterest += byPercent; 
    return;
  }

  public void receiveIncome(double income) {
    this.balance += income;
    return;
  }

  public void receiveInterest() {
    double interest = (this.yearlyInterest / 100 / 12) * this.balance;
    this.balance += interest;
    return;
  }

  public String toString() {
    return "이름: " + this.name + ", " +
           "연이자: " + String.format("%.1f", this.yearlyInterest) + ", " +
           "잔고: " + String.format("%.1f", this.balance) + ", " +
           "가입일: " + this.created.toString();
  }
}
