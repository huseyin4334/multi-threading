package com.example.mutithreading.beans.normal.lock;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@NoArgsConstructor
@Data
public class LockResourceOne {
    public int myNum = 100;

    public Lock resourceLock = new ReentrantLock();

    private Integer timeOut;

    private TimeUnit timeUnit;

    public LockResourceOne(Integer timeOut, TimeUnit timeUnit) {
        this.timeOut = timeOut;
        this.timeUnit = timeUnit;
    }
}
