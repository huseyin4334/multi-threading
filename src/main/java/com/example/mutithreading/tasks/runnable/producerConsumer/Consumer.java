package com.example.mutithreading.tasks.runnable.producerConsumer;

import com.example.mutithreading.beans.normal.producer.SharedQueue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class Consumer implements Runnable {

    SharedQueue resource;
    int capacity;

    public void consume() throws InterruptedException{
        synchronized (resource) {
            if (resource.getQueue().size() == 0) {
                log.info("Queue is empty. Consumer {} is waiting", Thread.currentThread().getName());
                resource.wait(); // resource'u kilitledi.
                log.info("Consumer {} waiting is finished", Thread.currentThread().getName());
            }
        }

        synchronized (resource) {
            String deleted = resource.getQueue().remove();
            log.info("Consumed: {} by {}", deleted, Thread.currentThread().getName());
            resource.notify(); // resuorce'u serbest bıraktı.
        }
    }

    @Override
    public void run() {
        for (int i = 0; i <= capacity; i++) {
            try {
                Thread.sleep((long) (Math.random() * 1000) * 5);
                consume();
                log.info("Consumed list: {}", String.join(",", resource.getQueue()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
