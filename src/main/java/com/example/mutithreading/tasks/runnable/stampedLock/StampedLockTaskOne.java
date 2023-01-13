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
    private boolean timeOutEnable = false;
    private String operation = Constants.WRITE;

    @Override
    public void run() {
        try {
            long writeLock = resourceOne.resourceLock.writeLock();//lockIdForOne();
            log.info("Locked on resourceOne by {}, stamp is {}", Thread.currentThread().getName(), writeLock);
            Thread.sleep(2000);
            resourceOne.myNum--;
            resourceOne.resourceLock.unlock(writeLock);
            log.info("Locked off resourceOne by {}", Thread.currentThread().getName());

            writeLock = resourceTwo.resourceLock.writeLock();//lockIdForTwo();
            log.info("Locked on resourceTwo by {}, stamp is {}", Thread.currentThread().getName(), writeLock);
            Thread.sleep(2000);
            resourceTwo.myNum++;
            resourceTwo.resourceLock.unlock(writeLock);
            log.info("Locked off resourceTwo by {}", Thread.currentThread().getName());
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    private long lockIdForOne() throws InterruptedException {
        if (Constants.WRITE.equals(operation))
            return timeOutEnable  ? resourceOne.resourceLock.tryWriteLock(resourceOne.getTimeOut(), resourceOne.getTimeUnit())
                    : resourceOne.resourceLock.tryWriteLock();
        else
            return timeOutEnable  ? resourceOne.resourceLock.tryReadLock(resourceOne.getTimeOut(), resourceOne.getTimeUnit())
                    : resourceOne.resourceLock.tryReadLock();
    }
    private long lockIdForTwo() throws InterruptedException {
        if (Constants.WRITE.equals(operation))
            return timeOutEnable  ? resourceTwo.resourceLock.tryWriteLock(resourceTwo.getTimeOut(), resourceTwo.getTimeUnit())
                    : resourceTwo.resourceLock.tryWriteLock();
        else
            return timeOutEnable  ? resourceTwo.resourceLock.tryReadLock(resourceTwo.getTimeOut(), resourceTwo.getTimeUnit())
                    : resourceTwo.resourceLock.tryReadLock();
    }
}
