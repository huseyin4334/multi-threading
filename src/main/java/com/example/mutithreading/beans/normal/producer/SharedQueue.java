package com.example.mutithreading.beans.normal.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Queue;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SharedQueue {
    Queue<String> queue;
    int capacity;
}
