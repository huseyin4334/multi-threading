package com.example.mutithreading.beans.normal;

import lombok.Getter;

@Getter
public class Counter {

    private int myNum;

    public void iterate() {
        myNum++;
    }

    public synchronized void syncIterate() {
        myNum++;
    }

    public synchronized void syncNegative() {
        myNum--;
    }

    public void syncIterate2() {
        synchronized (this) {
            // synchronized block
            // Bu bloğun içine yazmadığım işlemler senkronizasyona dahil olmayacaktır.
            myNum++;
        }
    }
}
