package SixLab.TaskOne;

/*
В этом коде два потока пытаются перевести деньги между двумя счетами, но делают это в обратном порядке.
Поскольку каждый поток сначала блокирует один счет, а затем пытается заблокировать другой,
они могут войти в состояние взаимной блокировки, если они оба успеют заблокировать свой первый счет перед тем,
как другой поток успеет заблокировать второй счет. Это приводит к тому, что оба потока ожидают освобождения счета,
который заблокирован другим потоком, и поэтому они оба блокируются навсегда. Это и есть взаимная блокировка.
 */

public class Main {
    public static void main(String[] args) {
        Account a = new Account();
        Account b = new Account();

        TransferThread thread1 = new TransferThread(a, b, 100);
        TransferThread thread2 = new TransferThread(b, a, 100);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}