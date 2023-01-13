package com.example.mutithreading.tasks.runnable;

import com.example.mutithreading.beans.staticTypes.Constants;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class Synchronize implements Runnable {

    public static final int iterateNum = 100;

    @Setter
    private boolean sync = true;

    public Synchronize(boolean sync) {
        this.sync = sync;
    }

    public static class Counter {
        public static void iterate() {
            Constants.myNum++;
        }

        public static synchronized void syncIterate() {
            Constants.myNum++;
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < iterateNum; i++) {
            if (isSync())
                Counter.syncIterate();
            else
                Counter.iterate();
        }
    }
}
