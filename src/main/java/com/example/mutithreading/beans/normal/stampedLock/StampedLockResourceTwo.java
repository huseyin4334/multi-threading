package com.example.mutithreading.beans.normal.stampedLock;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

@Data
@NoArgsConstructor
public class StampedLockResourceTwo {

    public int myNum = 100;

    public StampedLock resourceLock = new StampedLock();
}
