package com.example.mutithreading.beans.normal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Wallet {
    AtomicReference<Integer> money = new AtomicReference<>();
    Map<String, String> history = new ConcurrentHashMap<>();
}
