package com.example.mutithreading.beans.normal.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
@NoArgsConstructor
public class LockSharedQueue {
    private Queue<String> queue;
    private int capacity;

    private Lock resourceLock = new ReentrantLock();

    private Condition notFull = resourceLock.newCondition();
    private Condition notEmpty = resourceLock.newCondition();

    public LockSharedQueue(Queue<String> queue, int capacity) {
        this.queue = queue;
        this.capacity = capacity;
    }
}
