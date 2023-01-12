package com.example.mutithreading.tasks.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadExp extends Thread {

    private final String walk = "I'm walking";

    // I don't have to override
    @Override
    public void run() {
        log.info("Thread worker {} is started", getName());
        for (int i = 0; i < 5; i++) {
            try {
                log.info("Thread: {}, index: {} -- {}", getName(), i, walk);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.info("InterruptedException while sleep Thread: {}, index: {}", getName(), i);
            }
        }
        log.info("Thread worker {} is finished", getName());
    }
}
