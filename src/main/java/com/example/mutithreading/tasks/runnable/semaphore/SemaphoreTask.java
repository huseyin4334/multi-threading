package com.example.mutithreading.tasks.runnable.semaphore;

import com.example.mutithreading.beans.normal.Counter;
import com.example.mutithreading.beans.normal.SemaphoreSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
@AllArgsConstructor
public class SemaphoreTask implements Runnable {

    Semaphore worker;
    SemaphoreSource resource;
    Counter counter;

    @Override
    public void run() {
        try {
            String threadName = Thread.currentThread().getName();

            log.info("{} is waiting for acquire resource", threadName);

            worker.acquire();

            counter.syncIterate();
            log.info("{} has acquired resource for working! Acquired size: {}", threadName, counter.getMyNum());

            Thread.sleep((long) (Math.random() * 1000) * 5);
            int size = resource.perms.size();

            worker.release(); //kaynakları serbest bırakır ve çalışacak diğer tasklar için alan açılır.

            counter.syncNegative();
            log.info("{} released resource for working!", threadName);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
