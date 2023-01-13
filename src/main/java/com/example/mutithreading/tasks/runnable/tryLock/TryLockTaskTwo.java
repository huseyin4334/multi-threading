package com.example.mutithreading.tasks.runnable.tryLock;

import com.example.mutithreading.beans.normal.lock.LockResourceOne;
import com.example.mutithreading.beans.normal.lock.LockResourceTwo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class TryLockTaskTwo implements Runnable {
    private LockResourceOne resourceOne;
    private LockResourceTwo resourceTwo;
    private boolean timeOutEnable = false;

    @Override
    public void run() {
        try {

            boolean isLockedNow = isLockedNowForOne();
            if (isLockedNow) {
                log.info("Locked on resourceTwo by {}", Thread.currentThread().getName());
                resourceTwo.myNum--;
                Thread.sleep(1000);
                resourceTwo.resourceLock.unlock();
                log.info("Locked off resourceTwo by {}", Thread.currentThread().getName());
            }

            isLockedNow = isLockedNowForTwo();
            if (isLockedNow) {
                log.info("Locked on resourceOne by {}", Thread.currentThread().getName());
                resourceOne.myNum++;
                Thread.sleep(1000);
                resourceOne.resourceLock.unlock();
                log.info("Locked off resourceOne by {}", Thread.currentThread().getName());
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    private boolean isLockedNowForOne() throws InterruptedException {
        return timeOutEnable  ? resourceOne.resourceLock.tryLock(resourceOne.getTimeOut(), resourceOne.getTimeUnit())
                : resourceOne.resourceLock.tryLock();
    }
    private boolean isLockedNowForTwo() throws InterruptedException {
        return timeOutEnable  ? resourceTwo.resourceLock.tryLock(resourceTwo.getTimeOut(), resourceTwo.getTimeUnit())
                : resourceTwo.resourceLock.tryLock();
    }
}
