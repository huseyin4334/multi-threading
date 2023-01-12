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

    public void example3Simple() {
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

    public void example3Mid() {
        Thread thread = threadExp(null, false);

        log.info("State of walkerTread after init: {}", thread.getState());
        log.info("State of main thread after walkerTread init: {}", Thread.currentThread().getState());

        try {
            thread.start();
            log.info("State of walkerTread after start: {}", thread.getState());
            log.info("State of main thread after walkerTread start: {}", Thread.currentThread().getState());

            Thread.sleep(1000);
            thread.join(5000);
            log.info("State of walkerTread after join: {}", thread.getState());
            log.info("State of main thread after walkerTread join: {}", Thread.currentThread().getState());

            log.info("Main thread will sleep 20s....");
            Thread.sleep(20000);
            log.info("State of walkerTread after end: {}", thread.getState());
            log.info("State of main thread after walkerTread end: {}", Thread.currentThread().getState());

        } catch (InterruptedException e) {
            log.info("InterruptedException while joining example-3");
            e.printStackTrace();
        }

        /*
         * Main Thread daima çalışır pozisyondadır.
         * Thread.sleep komutu üzerinde çalışılan thread'e uyuma komutu gönderir. Uyuma süresi boyunca bir sonraki satırın işini başlatamaz.
         * */
    }

    public void example4() {
        Thread thread = threadExp(null, false);

        log.info("isAlive walkerTread after init: {}", thread.isAlive());

        try {
            thread.start();
            log.info("isAlive of walkerTread after start: {}", thread.isAlive());

            thread.join();
            log.info("isAlive walkerTread after join: {}", thread.isAlive());

        } catch (InterruptedException e) {
            log.info("InterruptedException while joining example-3");
            e.printStackTrace();
        }

        /*
         * Thread çalıştığı sürece isAlive true sonucunu dönecektir.
         * */
    }

    public void example5() {
        Thread walker = threadExp(null, false);
        Thread runner = runnableExp(null, false);

        walker.setPriority(9);
        runner.setPriority(2);

        walker.start();
        runner.start();

        log.info("walkerTread id: {}", walker.getId());
        log.info("runnerThread id: {}", runner.getId());
        log.info("mainThread id: {}", Thread.currentThread().getId());

        log.info("walkerTread name: {}", walker.getName());
        log.info("runnerThread name: {}", runner.getName());
        log.info("mainThread name: {}", Thread.currentThread().getName());

        log.info("walkerTread group: {}", walker.getThreadGroup());
        log.info("runnerThread group: {}", runner.getThreadGroup());
        log.info("mainThread group: {}", Thread.currentThread().getThreadGroup());

        log.info("walkerTread priority: {}", walker.getPriority());
        log.info("runnerThread priority: {}", runner.getPriority());
        log.info("mainThread priority: {}", Thread.currentThread().getPriority());

        log.info("Active thread Count: {}", Thread.activeCount());

        /*
         * Öncelik seviyesi bekleyen işlerde karar mekanizmasını etkileyen bir değerdir.
         * default değer 5'tir.
         * */
    }

    public void example6Simple() {
        Thread walker = threadExp(null, false);
        Thread runner = runnableExp(null, false);

        runner.setDaemon(true);

        log.info("walkerTread daemon: {}", walker.isDaemon());
        log.info("runnerThread daemon: {}", runner.isDaemon());
        log.info("mainThread daemon: {}", Thread.currentThread().isDaemon());

        try {
            walker.start();
            walker.join(5000);
            runner.start();
        } catch (InterruptedException e) {
            log.info("InterruptedException while joining example-3");
            e.printStackTrace();
        }

        /*
         * Daemon değeri true veya false olabilir.
         * Eğer bu değer true olursa JVM bu thread'in işlemlerine interrupt atamaz.
         * Garbage Collector bu değerin true olduğu bir örnektir.
         * */
    }

    public void example6Mid() {
        // GROUPS
        ThreadGroup group1 = new ThreadGroup("Group-1");
        ThreadGroup group2 = new ThreadGroup("Group-2");

        // WALKER THREADS
        Thread walker1 = new Thread(group1, new ThreadExp("I'm walking", true, 2000));
        Thread walker2 = new Thread(group2, new ThreadExp("I'm walking", true,1000));
        Thread walker3 = new Thread(group2, new ThreadExp("I'm walking", true, 3000));

        // RUNNER THREADS
        Thread runner1 = new Thread(group1, new RunnableExp("I'm running", true, 3000));
        Thread runner2 = new Thread(group2, new RunnableExp("I'm running", true,1000));

        walker1.start();
        walker2.start();
        walker3.start();
        runner1.start();
        runner2.start();

        log.info("Active thread in main: {}", Thread.activeCount());
        log.info("Active thread in group-1: {}", group1.activeCount());
        log.info("Active thread in group-2: {}", group2.activeCount());

        /*
         *
         * */
    }
}
