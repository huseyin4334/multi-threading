package com.example.mutithreading.tasks.runnable.producerConsumer;

import com.example.mutithreading.beans.normal.producer.LockSharedQueue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
@AllArgsConstructor
public class BlockProducer implements Runnable {
    ArrayBlockingQueue<String> resource;
    List<String> items;

    public void produce(String item) throws InterruptedException {
        resource.put(item);
        log.info("Produced: {}", item);
    }

    @Override
    public void run() {
        for (String item: items) {
            try {
                Thread.sleep((long) (Math.random() * 1000) * 5);
                produce(item);
                log.info("Produced list: {}", String.join(",", resource));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("Job finished...");
    }
}
