package SixLab.TaskOne;

class Account {
    double balance;
    synchronized void withdraw(double amount){
        balance -= amount;
    }
    synchronized void deposit(double amount){
        balance += amount;
    }
}




