package com.example.mutithreading.tasks.runnable.producerConsumer;

import com.example.mutithreading.beans.normal.producer.SharedQueue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class Producer implements Runnable {

    SharedQueue resource;
    List<String> items;

    public void produce(String item) throws InterruptedException{
        synchronized (resource) {
            if (resource.getQueue().size() >= resource.getCapacity()) {
                log.info("Queue is full. Producer is waiting");
                resource.wait(); // resource'u kilitledi.
                log.info("Producer waiting is finished");
            }
        }

        synchronized (resource) {
            resource.getQueue().add(item);
            log.info("Produced: {}", item);
            resource.notify(); // resuorce'u serbest bıraktı.
        }
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
    }
}
