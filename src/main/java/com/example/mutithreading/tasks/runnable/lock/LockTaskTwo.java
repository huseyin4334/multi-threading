package com.example.mutithreading.tasks.runnable.lock;

import com.example.mutithreading.beans.normal.deadlock.ResourceOne;
import com.example.mutithreading.beans.normal.deadlock.ResourceTwo;
import com.example.mutithreading.beans.normal.lock.LockResourceOne;
import com.example.mutithreading.beans.normal.lock.LockResourceTwo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class LockTaskTwo implements Runnable {
    private LockResourceOne resourceOne;
    private LockResourceTwo resourceTwo;

    @Override
    public void run() {
        try {
            resourceTwo.resourceLock.lock();
            log.info("Locked on resourceTwo by {}", Thread.currentThread().getName());
            resourceTwo.myNum--;
            Thread.sleep(1000);
            resourceTwo.resourceLock.unlock();
            log.info("Locked off resourceTwo by {}", Thread.currentThread().getName());

            resourceOne.resourceLock.lock();
            log.info("Locked on resourceOne by {}", Thread.currentThread().getName());
            resourceOne.myNum++;
            Thread.sleep(1000);
            resourceOne.resourceLock.unlock();
            log.info("Locked off resourceOne by {}", Thread.currentThread().getName());
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
