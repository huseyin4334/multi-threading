package com.example.mutithreading.tasks.runnable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class RunnableExp implements Runnable {

    private final String runS = "I'm running";

    @Override
    public void run() {
        log.info("Runnable worker {} is started", Thread.currentThread().getName());
        for (int i = 0; i < 5; i++) {
            try {
                log.info("Runnable: {}, index: {} -- {}", Thread.currentThread().getName(), i, runS);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.info("InterruptedException while sleep Runnable: {}, index: {}", Thread.currentThread().getName(), i);
            }
        }
        log.info("Runnable worker {} is finished", Thread.currentThread().getName());
    }
}
