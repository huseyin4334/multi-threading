package com.example.mutithreading.tasks.runnable.producerConsumer;

import com.example.mutithreading.beans.normal.producer.LockSharedQueue;
import com.example.mutithreading.beans.normal.producer.SharedQueue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@AllArgsConstructor
@Slf4j
public class LockProducer implements Runnable {
    LockSharedQueue resource;
    List<String> items;

    public void produce(String item) throws InterruptedException {
        resource.getResourceLock().lock();

        if (resource.getQueue().size() >= resource.getCapacity()) {
            log.info("Queue is full. Producer is waiting");
            resource.getNotFull().await(); // notFull için signal komutu gelene kadar bu işlemi durduracaktır.
        }

        resource.getQueue().add(item);
        log.info("Produced: {}", item);
        resource.getNotEmpty().signal(); // notEmpty için await varsıysa onu devam ettirecektir.

        resource.getResourceLock().unlock();
    }

    @Override
    public void run() {
        for (String item: items) {
            try {
                Thread.sleep((long) (Math.random() * 1000) * 5);
                produce(item);
                log.info("Produced list: {}", String.join(",", resource.getQueue()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("Job finished...");
    }
}
