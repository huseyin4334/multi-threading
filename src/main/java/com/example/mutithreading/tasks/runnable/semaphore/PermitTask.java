package com.example.mutithreading.tasks.runnable.semaphore;

import com.example.mutithreading.beans.normal.Counter;
import com.example.mutithreading.beans.normal.SemaphoreSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
@AllArgsConstructor
public class PermitTask implements Runnable {

    Semaphore worker;
    SemaphoreSource resource;
    int numPermits = 1;

    @Override
    public void run() {
        try {
            String threadName = Thread.currentThread().getName();

            log.info("{} is waiting for acquire resource for {} permits", threadName, numPermits);

            worker.acquire(numPermits);

            log.info("{} has acquired resource for {} permits!", threadName, numPermits);

            Thread.sleep((long) (Math.random() * 1000) * 5);
            int size = resource.perms.size();

            worker.release(numPermits); //kaynakları serbest bırakır ve çalışacak diğer tasklar için alan açılır.

            log.info("{} released resource for {} permits!", threadName, numPermits);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
