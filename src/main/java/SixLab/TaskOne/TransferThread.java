package SixLab.TaskOne;

class TransferThread extends Thread {
    Account from;
    Account to;
    double amount;

    TransferThread(Account from, Account to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public void run() {
        synchronized(from) {
            synchronized(to) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }
}