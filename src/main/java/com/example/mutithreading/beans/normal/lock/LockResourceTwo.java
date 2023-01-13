package com.example.mutithreading.beans.normal.lock;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
@NoArgsConstructor
public class LockResourceTwo {

    public int myNum = 100;

    public Lock resourceLock = new ReentrantLock();

    private Integer timeOut;

    private TimeUnit timeUnit;

    public LockResourceTwo(Integer timeOut, TimeUnit timeUnit) {
        this.timeOut = timeOut;
        this.timeUnit = timeUnit;
    }
}
