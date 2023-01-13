package com.example.mutithreading.tasks.runnable;

import com.example.mutithreading.beans.normal.Counter;
import com.example.mutithreading.beans.staticTypes.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SynchronizeSharedResources implements Runnable {

    private int iterateNum = 100;
    private Counter counter;
    private boolean sync = true;

    @Override
    public void run() {
        assert counter != null;
        for (int i = 0; i < iterateNum; i++) {
            if (isSync())
                counter.syncIterate(); // or counter.syncIterate2();
            else
                counter.iterate();
        }
    }
}
