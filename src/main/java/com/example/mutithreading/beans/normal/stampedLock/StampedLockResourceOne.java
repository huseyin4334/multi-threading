package com.example.mutithreading.beans.normal.stampedLock;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

@NoArgsConstructor
@Data
public class StampedLockResourceOne {
    public int myNum = 100;

    public StampedLock resourceLock = new StampedLock();
}
