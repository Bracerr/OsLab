package SixLab.TaskTwo;

public class Main {

    public static void main(String[] args) {
        Account a = new Account();
        Account b = new Account();

        a.deposit(1000); // Пополняем счет аккаунта 'a' на 1000
        b.deposit(1000); // Пополняем счет аккаунта 'b' на 1000

        Thread t1 = new Thread(() -> {
            try {
                a.transfer(a, b, 500); // Переводим 500 с 'a' на 'b'
                System.out.println("Transfer from a to b completed by Thread 1");
            } catch (InterruptedException e) {
                System.out.println("Thread 1 was interrupted");
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                b.transfer(b, a, 300); // Переводим 300 с 'b' на 'a'
                System.out.println("Transfer from b to a completed by Thread 2");
            } catch (InterruptedException e) {
                System.out.println("Thread 2 was interrupted");
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Balance of a: " + a.getBalance()); // Ожидаем 800 (1000 - 500 + 300)
        System.out.println("Balance of b: " + b.getBalance()); // Ожидаем 1200 (1000 + 500 - 300)
    }
}
