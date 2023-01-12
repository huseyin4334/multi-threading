package com.example.mutithreading.tasks.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ThreadExp extends Thread {

    private String walk = "I'm walking";
    private boolean threadKnowledge = false;
    private Integer waitTime = 2000;

    // I don't have to override
    @Override
    public void run() {
        log.info("Thread worker {} is started", getName());
        for (int i = 0; i < 5; i++) {
            try {
                if (!threadKnowledge)
                    log.info("Thread: {}, index: {} -- {}", getName(), i, walk);
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                log.info("InterruptedException while sleep Thread: {}, index: {}", getName(), i);
            }
            if (threadKnowledge)
                log.info("I'm thread type. Active thread count {} in {} group.", Thread.activeCount(), Thread.currentThread().getThreadGroup().getName());
        }
        log.info("Thread worker {} is finished", getName());
    }
}
