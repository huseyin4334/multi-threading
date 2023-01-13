package com.example.mutithreading.tasks.runnable.deadlock;

import com.example.mutithreading.beans.normal.deadlock.ResourceOne;
import com.example.mutithreading.beans.normal.deadlock.ResourceTwo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class TaskOne implements Runnable {
    private ResourceOne resourceOne;
    private ResourceTwo resourceTwo;

    @Override
    public void run() {
        try {
            synchronized (resourceOne) {
                log.info("Locked on resourceOne by {}", Thread.currentThread().getName());
                resourceOne.myNum++;
                Thread.sleep(1000);

                synchronized (resourceTwo) {
                    log.info("Locked on resourceTwo by {}", Thread.currentThread().getName());
                    resourceTwo.myNum--;
                }
                log.info("Locked off resourceTwo by {}", Thread.currentThread().getName());
            }
            log.info("Locked off resourceOne by {}", Thread.currentThread().getName());
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
