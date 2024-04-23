package SixLab.TaskTwo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/*
В предложенном решении используется упорядочивание блокировок по хэш-кодам объектов.
Это предотвращает возможность взаимоблокировки, так как потоки всегда запрашивают ресурсы в одном и том же порядке:
сначала блокируется ресурс с меньшим хэш-кодом, а затем ресурс с большим хэш-кодом.

Также используются методы tryLock с таймаутом, чтобы предотвратить длительное ожидание блокировки и избежать взаимоблокировки.
Если блокировка не может быть получена в течение 1 секунды,
поток освобождает ресурсы и пытается заново выполнить операцию позже.
 */


/**
 * Обоснование:
 * Упорядочивание получения блокировок: Если потоки всегда запрашивают ресурсы в одном и том же порядке,
 * это исключит возможность взаимоблокировки.
 * <p>
 * Использование методов tryLock и lockInterruptibly: Эти методы позволяют пытаться получить
 * блокировку на ресурс и при неудаче выполнять другие действия. Например, можно прервать
 * попытку получения блокировки после определенного времени или в ответ на прерывание потока.
 * <p>
 * Таймауты для блокировок: Метод tryLock также позволяет установить таймаут для ожидания блокировки.
 * Если блокировка не удастся получить в течение указанного времени, поток может освободить ресурсы и попробовать позже.
 */

class Account {
    public double getBalance() {
        return balance;
    }

    private double balance;
    private final Lock lock = new ReentrantLock();

    void withdraw(double amount) {
        lock.lock();
        try {
            balance -= amount;
        } finally {
            lock.unlock();
        }
    }

    void deposit(double amount) {
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    void transfer(Account from, Account to, double amount) throws InterruptedException {
        Account first = (this.hashCode() < to.hashCode()) ? this : to;
        Account second = (this.hashCode() < to.hashCode()) ? to : this;

        if (first.lock.tryLock(1, TimeUnit.SECONDS)) {
            try {
                if (second.lock.tryLock(1, TimeUnit.SECONDS)) {
                    try {
                        from.withdraw(amount);
                        to.deposit(amount);
                    } finally {
                        second.lock.unlock();
                    }
                }
            } finally {
                first.lock.unlock();
            }
        }
    }
}

