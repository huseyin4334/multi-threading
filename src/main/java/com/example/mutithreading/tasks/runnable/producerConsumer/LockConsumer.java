package com.example.mutithreading.tasks.runnable.producerConsumer;

import com.example.mutithreading.beans.normal.producer.LockSharedQueue;
import com.example.mutithreading.beans.normal.producer.SharedQueue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class LockConsumer implements Runnable {

    LockSharedQueue resource;
    int capacity;

    public void consume() throws InterruptedException{
        resource.getResourceLock().lock();

        if (resource.getQueue().size() == 0) {
            log.info("Queue is empty. Consumer {} is waiting", Thread.currentThread().getName());
            resource.getNotEmpty().await(); // notEmpty için signal gelene kadar işlem durdurulur.
        }

        String deleted = resource.getQueue().remove();
        log.info("Consumed: {} by {}", deleted, Thread.currentThread().getName());
        resource.getNotFull().signal(); // notFull için signal bekleyen işlerin devamını sağlayacaktır.

        resource.getResourceLock().unlock();
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
        log.info("Capacity is finished....");
    }
}
