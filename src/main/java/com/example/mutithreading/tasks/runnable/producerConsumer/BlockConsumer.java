package com.example.mutithreading.tasks.runnable.producerConsumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;

@AllArgsConstructor
@Slf4j
public class BlockConsumer implements Runnable {

    ArrayBlockingQueue<String> resource;
    int capacity;

    public void consume() throws InterruptedException{
        String deleted = resource.take();
        log.info("Consumed: {} by {}", deleted, Thread.currentThread().getName());
    }

    @Override
    public void run() {
        for (int i = 0; i <= capacity; i++) {
            try {
                Thread.sleep((long) (Math.random() * 1000) * 5);
                consume();
                log.info("Consumed list: {}", String.join(",", resource));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("Capacity is finished....");
    }
}
