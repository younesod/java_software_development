package BankAccount;

public class BankAccount {
    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }
    public int getBalance() {
        return balance;
    }
    public  synchronized void deposit(int amount){
        balance+=amount;
    }
    public synchronized void withdraw(int amount){
        if(balance-amount>=0){
            balance-=amount;
        }else System.out.println("Insufficient funds");
    }
}
