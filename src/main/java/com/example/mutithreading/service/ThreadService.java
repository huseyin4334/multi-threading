package com.example.mutithreading.service;

import com.example.mutithreading.tasks.runnable.RunnableExp;
import com.example.mutithreading.tasks.thread.ThreadExp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Slf4j
@Service
public class ThreadService {

    public Thread threadExp(String name, boolean debugMode) {
        Thread thread = new ThreadExp();
        if (name != null && !name.isBlank())
            thread.setName(name);

        if (debugMode) {
            log.info("Thread is a Runnable {}", thread instanceof Runnable);
            log.info("Thread is a Callable {}", thread instanceof Callable);
            log.info("Thread is a Runnable {}", thread instanceof Thread);
            log.info("Thread is a Runnable {}", thread instanceof Object);
        }

        return thread;
    }
    public Thread runnableExp(String name, boolean debugMode) {
        Thread thread = new Thread(new RunnableExp());
        if (name != null && !name.isBlank())
            thread.setName(name);
        if (debugMode) {
            log.info("Thread is a Runnable {}", thread instanceof Runnable);
            log.info("Thread is a Callable {}", thread instanceof Callable);
            log.info("Thread is a Runnable {}", thread instanceof Thread);
            log.info("Thread is a Runnable {}", thread instanceof Object);
        }

        return thread;
    }
    public void callableExp() {
        Thread thread = new Thread(new RunnableExp());
        thread.start();

        log.info("Thread is a Runnable {}", thread instanceof Runnable);
        log.info("Thread is a Callable {}", thread instanceof Callable);
        log.info("Thread is a Runnable {}", thread instanceof Thread);
        log.info("Thread is a Runnable {}", thread instanceof Object);
    }

    public void example1() {
        Thread thread = threadExp(null, false);
        Thread runnable = runnableExp(null,false);

        thread.start();
        //thread.start();
        // start synchronized bir fonksiyondur bir erişim sonlanmadan diğerine izin vermez.
        // Aynı anda aynı thread'in başlatılmasına izin verilmediği gibi başka bir thread yaratılması gerekmektedir
        // java.lang.IllegalThreadStateException

        //thread.run();
        // run çalıştırırsam bu hata almayacaktır ve yapıyı normal fonksiyon gibi çalıştıracaktır.
        // Senkron çalışır ve sonra çağırılan kod satırı buranın bitmesini bekler.

        runnable.start();
    }

    public void example2() {
        Thread thread = threadExp(null, false);
        Thread runnable = runnableExp(null,false);

        try {
            thread.start();
            thread.join();
            runnable.start();
        } catch (InterruptedException e) {
            log.info("InterruptedException while joining example-2");
            e.printStackTrace();
        }

        /*
        * join metodu başlatılmış olan thread işlemi bitmeden diğer adıma geçişi durdurur.
        * thread.join(5000); Belirttiğim süre boyunca bekle sonrasında bekleme ve işlemlere devam et demek
        * Boş bırakırsam işlem bitene kadar durdurur.
        * */
    }

    public void example3() {
        Thread thread = threadExp(null, false);

        log.info("State of thread after init: {}", thread.getState());

        try {
            thread.start();
            log.info("State of thread after start: {}", thread.getState());
            thread.join();
            log.info("State of thread after join: {}", thread.getState());
        } catch (InterruptedException e) {
            log.info("InterruptedException while joining example-2");
            e.printStackTrace();
        }

        /*
         * State ile durum belirtir ve threadin hangi durumda olduğunu bu değer ile anlıyabiliriz.
         * Init -> NEW
         * Start -> RUNNABLE
         * Join -> TERMINATED
         * */
    }
}
