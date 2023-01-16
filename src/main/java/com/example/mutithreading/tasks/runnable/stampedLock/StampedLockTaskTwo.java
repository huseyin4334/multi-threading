package com.example.mutithreading.tasks.runnable.stampedLock;

import com.example.mutithreading.beans.normal.lock.LockResourceOne;
import com.example.mutithreading.beans.normal.lock.LockResourceTwo;
import com.example.mutithreading.beans.normal.stampedLock.StampedLockResourceOne;
import com.example.mutithreading.beans.normal.stampedLock.StampedLockResourceTwo;
import com.example.mutithreading.beans.staticTypes.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class StampedLockTaskTwo implements Runnable {
    private StampedLockResourceOne resourceOne;
    private StampedLockResourceTwo resourceTwo;
    private String operation = Constants.WRITE;

    @Override
    public void run() {
        try {

            long lock = lockIdForTwo();
            log.info("Locked on resourceTwo by {}, stamp is {}", Thread.currentThread().getName(), lock);
            Thread.sleep(1000);
            resourceTwo.myNum = resourceTwo.myNum + 6;
            resourceTwo.resourceLock.unlock(lock);
            log.info("Locked off resourceTwo by {}, value: {}", Thread.currentThread().getName(), resourceTwo.getMyNum());

            lock = lockIdForOne();
            log.info("Locked on resourceOne by {}, stamp is {}", Thread.currentThread().getName(), lock);
            Thread.sleep(1000);
            resourceOne.myNum = resourceOne.myNum - 3;
            resourceOne.resourceLock.unlock(lock);
            log.info("Locked off resourceOne by {}, value: {}", Thread.currentThread().getName(), resourceOne.getMyNum());

        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    private long lockIdForOne() {
        if (Constants.WRITE.equals(operation))
            return resourceOne.resourceLock.writeLock();
        else
            return resourceOne.resourceLock.readLock();
    }
    private long lockIdForTwo() {
        if (Constants.WRITE.equals(operation))
            return resourceTwo.resourceLock.writeLock();
        else
            return resourceTwo.resourceLock.readLock();
    }
}
