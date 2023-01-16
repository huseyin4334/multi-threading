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
public class StampedLockTaskOne implements Runnable {
    private StampedLockResourceOne resourceOne;
    private StampedLockResourceTwo resourceTwo;
    private String operation = Constants.WRITE;

    @Override
    public void run() {
        try {
            long lock = lockIdForOne();
            log.info("Locked on resourceOne by {}, stamp is {}", Thread.currentThread().getName(), lock);
            int newVal = resourceOne.myNum - 10;
            Thread.sleep(20000);
            resourceOne.myNum = newVal;
            resourceOne.resourceLock.unlock(lock);
            log.info("Locked off resourceOne by {}, value: {}", Thread.currentThread().getName(), resourceOne.getMyNum());

            lock = lockIdForTwo();
            log.info("Locked on resourceTwo by {}, stamp is {}", Thread.currentThread().getName(), lock);
            Thread.sleep(2000);
            resourceTwo.myNum = resourceTwo.myNum + 5;
            resourceTwo.resourceLock.unlock(lock);
            log.info("Locked off resourceTwo by {}, value: {}", Thread.currentThread().getName(), resourceTwo.getMyNum());
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
