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
            try {
                // Добавляем задержку
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized(to) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }
}
