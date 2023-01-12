package com.example.mutithreading.tasks.runnable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunnableExp implements Runnable {

    private String runS = "I'm running";
    private boolean threadKnowledge = false;
    private Integer waitTime = 2000;

    @Override
    public void run() {
        log.info("Runnable worker {} is started", Thread.currentThread().getName());
        for (int i = 0; i < 5; i++) {
            try {
                if (!threadKnowledge)
                    log.info("Runnable: {}, index: {} -- {}", Thread.currentThread().getName(), i, runS);
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                log.info("InterruptedException while sleep Runnable: {}, index: {}", Thread.currentThread().getName(), i);
            }
            if (threadKnowledge)
                log.info("I'm runnable. Active thread count {} in {} group.", Thread.activeCount(), Thread.currentThread().getThreadGroup().getName());
        }
        log.info("Runnable worker {} is finished", Thread.currentThread().getName());
    }
}
