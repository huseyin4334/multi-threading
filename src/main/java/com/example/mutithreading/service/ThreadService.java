package com.example.mutithreading.service;

import com.example.mutithreading.beans.normal.Counter;
import com.example.mutithreading.beans.normal.SemaphoreSource;
import com.example.mutithreading.beans.normal.deadlock.ResourceOne;
import com.example.mutithreading.beans.normal.deadlock.ResourceTwo;
import com.example.mutithreading.beans.normal.lock.LockResourceOne;
import com.example.mutithreading.beans.normal.lock.LockResourceTwo;
import com.example.mutithreading.beans.normal.producer.LockSharedQueue;
import com.example.mutithreading.beans.normal.producer.SharedQueue;
import com.example.mutithreading.beans.normal.stampedLock.StampedLockResourceOne;
import com.example.mutithreading.beans.normal.stampedLock.StampedLockResourceTwo;
import com.example.mutithreading.beans.staticTypes.Constants;
import com.example.mutithreading.tasks.runnable.*;
import com.example.mutithreading.tasks.runnable.deadlock.TaskOne;
import com.example.mutithreading.tasks.runnable.deadlock.TaskTwo;
import com.example.mutithreading.tasks.runnable.lock.LockTaskOne;
import com.example.mutithreading.tasks.runnable.lock.LockTaskTwo;
import com.example.mutithreading.tasks.runnable.producerConsumer.*;
import com.example.mutithreading.tasks.runnable.semaphore.PermitTask;
import com.example.mutithreading.tasks.runnable.semaphore.SemaphoreTask;
import com.example.mutithreading.tasks.runnable.stampedLock.StampedLockTaskOne;
import com.example.mutithreading.tasks.runnable.stampedLock.StampedLockTaskTwo;
import com.example.mutithreading.tasks.runnable.tryLock.TryLockTaskOne;
import com.example.mutithreading.tasks.runnable.tryLock.TryLockTaskTwo;
import com.example.mutithreading.tasks.thread.ThreadExp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

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

    public void example7Simple() {
        Thread pageDownloader = new Thread(new PageDownloader(Constants.urls));

        try {
            long startTime = System.currentTimeMillis();
            pageDownloader.start();
            pageDownloader.join();
            long endTime = System.currentTimeMillis();
            log.info("Total time: {}s", (endTime - startTime)/100);
        } catch (InterruptedException ex) {
            log.info("InterruptedException while joining example-7");
            ex.printStackTrace();
        }
    }

    public void example7Mid() {
        Thread pageDownloader = new Thread(new PageDownloader(Constants.urls.subList(0,3)));
        Thread pageDownloader2 = new Thread(new PageDownloader(Constants.urls.subList(3,6)));

        try {
            long startTime = System.currentTimeMillis();
            pageDownloader.start();
            pageDownloader2.start();
            pageDownloader.join();
            pageDownloader2.join();
            long endTime = System.currentTimeMillis();
            log.info("Total time: {}s", (endTime - startTime)/100);
        } catch (InterruptedException ex) {
            log.info("InterruptedException while joining example-7");
            ex.printStackTrace();
        }
    }

    public void example8() {
        Thread pageDownloader = new Thread(new PageDownloader(Constants.urls.subList(0,3)));
        Thread pageDownloader2 = new Thread(new PageDownloader(Constants.urls.subList(3,6)));

        try {
            long startTime = System.currentTimeMillis();
            pageDownloader.start();
            pageDownloader2.start();

            Thread.sleep(10000);

            pageDownloader.interrupt();
            // Biraz hata fırlatacak ancak işi yarıda kesecektir. Hatanın sebebi sleep yapılmış olmasına rağmen kesmeye çalışmamızdır
            // Thread sleep uygulanmamış olursa bu hata alınmayacaktır.
            // Interrupted exception fırlatır hatayı yakalar ve ön yüze yansıtmaz. Arkada thread'i durdurmuş olur.
            pageDownloader2.join();
            long endTime = System.currentTimeMillis();
            log.info("Total time: {}s", (endTime - startTime)/100);
        } catch (InterruptedException ex) {
            log.info("InterruptedException while joining example-7");
            ex.printStackTrace();
        }
    }

    public void example9(boolean synchronize) {
        Thread worker1 = new Thread(new Synchronize(synchronize));
        Thread worker2 = new Thread(new Synchronize(synchronize));

        try {
            log.info("Start value for myNum: {}", Constants.myNum);
            worker1.start();
            worker2.start();
            worker1.join();
            worker2.join();
            log.info("End value for myNum: {}", Constants.myNum);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        /*
         * sync false olduğunda üst üste yazmalar sebebiyle beklenen sonuç alınamyacak ve her seferinde farklı çıktı gelecektir.
         * true olduğunda değere aynı anda veri işlemesi yapılamayacaktır.
         */
    }

    public void example10(boolean synchronize) {
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        Thread worker1 = new Thread(new SynchronizeSharedResources(10000, counter1, synchronize));
        Thread worker2 = new Thread(new SynchronizeSharedResources(10000, counter2, synchronize));
        Thread worker3 = new Thread(new SynchronizeSharedResources(10000, counter2, synchronize));

        try {
            log.info("Start value for myNum in counter-1: {}", counter1.getMyNum());
            log.info("Start value for myNum in counter-2: {}", counter2.getMyNum());
            worker1.start();
            worker2.start();
            worker3.start();
            worker1.join();
            worker2.join();
            worker3.join();
            log.info("End value for myNum in counter-1: {}", counter1.getMyNum());
            log.info("End value for myNum in counter-2 {}", counter2.getMyNum());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        /*
         * Paylaşılan kaynaklarda erişimi ve üzerine yazmayı nasıl yönetebileceğimizi örneklendirdik.
         */
    }

    public void example11() {
        Thread worker1 = new Thread(new TaskOne(new ResourceOne(), new ResourceTwo()), "Task-1");
        Thread worker2 = new Thread(new TaskTwo(new ResourceOne(), new ResourceTwo()), "Task-2");

        try {
            worker1.start();
            worker2.start();
            worker1.join();
            worker2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        /*
         * Bu yapı deadlock'a yol açacaktır.
         * Sebebi şöyle;
         * task1 ilk aşama olarak resourceOne kaynağını kilitleyecetir.
         * task2 ilk aşama olarak resourceTwo kaynağını kilitleyecetir.
         * task1 işlemi devam ettirip resourceOne'ın kilidini kaldırabilmesi için resourceTwo'ya erişmesi gerekiyor.
         * Tam tersi task2 için geçerli bu sebeple işlem devam edemiyor, deadlock oluyor ve interruptedException hatası alınıyor.
         * Bu deadlock senaryosunun adı hold and wait
         */
    }

    public void example12() {
        LockResourceOne one = new LockResourceOne();
        LockResourceTwo two = new LockResourceTwo();

        Thread worker1 = new Thread(new LockTaskOne(one, two), "Task-1");
        Thread worker2 = new Thread(new LockTaskTwo(one, two), "Task-2");

        try {
            worker1.start();
            worker2.start();
            worker1.join();
            worker2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        /*
         * Bu yapı deadlock'a yol açmayacaktır.
         * Çünkü nesne üzerindeki lock işlemin bitmesi ardından kaldırılacaktır.
         * Böylece istek almaya devam edecektir.
         */
    }

    public void example13(Integer timeOut, TimeUnit timeUnit, boolean timeOutEnabled) {
        LockResourceOne one = new LockResourceOne(timeOut, timeUnit);
        LockResourceTwo two = new LockResourceTwo(timeOut, timeUnit);

        Thread worker1 = new Thread(new TryLockTaskOne(one, two, timeOutEnabled), "Task-1");
        Thread worker2 = new Thread(new TryLockTaskTwo(one, two, timeOutEnabled), "Task-2");

        try {
            worker1.start();
            worker2.start();
            worker1.join();
            worker2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        /*
         * Bu yapı deadlock'a yol açmayacaktır.
         * Çünkü nesne kilitlemeye çalışacaktır ve eğer false gelirse kilitli sayılıp yola devam edilecektir.
         * Böylece runtime kilitlenmeyecektir.
         * Bunun dışında tryLock ile istediğimiz süre boyunca kilit atabiliriz.
         * Ancak interruptException alma ihtimali vardır.
         */
    }

    public void example14(String operationType) {
        StampedLockResourceOne one = new StampedLockResourceOne();
        StampedLockResourceTwo two = new StampedLockResourceTwo();

        Thread worker1 = new Thread(new StampedLockTaskOne(one, two, operationType), "Task-1");
        Thread worker2 = new Thread(new StampedLockTaskTwo(one, two, operationType), "Task-2");
        Thread worker3 = new Thread(new StampedLockTaskTwo(one, two, operationType), "AnotherTask-2");

        try {
            log.info("Resources start values -> r1: {}, r2: {}", one.myNum, two.myNum);
            worker1.start();
            worker2.start();
            worker3.start();
            worker1.join();
            worker2.join();
            worker3.join();
            log.info("Resources end values -> r1: {}, r2: {}", one.myNum, two.myNum);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        /*
         * Write senkron olacak şekilde işlemleri sıralamaktadır. Ve her işlem için bir long değer dönmektedir.
         * Read işlemi bir long değer dönmektedir ancak write gibi bir kilitleme işlemi yapmamaktadır.
         */
    }

    public void example15() {
        SharedQueue resource = new SharedQueue(new LinkedList<>(), 3);

        Thread worker1 = new Thread(new Producer(resource, Constants.itemsForPC), "Task-1");
        Thread worker2 = new Thread(new Consumer(resource, 6), "Task-2");

        worker1.start();
        worker2.start();
        /*
         *
         */
    }

    public void example16WithLock() {
        LockSharedQueue resource = new LockSharedQueue(new LinkedList<>(), 3);

        Thread worker1 = new Thread(new LockProducer(resource, Constants.itemsForPC), "Producer-1");
        Thread worker2 = new Thread(new LockProducer(resource, Constants.itemsForPC), "Producer-1");

        Thread worker3 = new Thread(new LockConsumer(resource, 2), "Consumer-1");
        Thread worker4 = new Thread(new LockConsumer(resource, 6), "Consumer-2");

        worker1.start();
        worker2.start();
        worker3.start();
        worker4.start();
        /*
         * await ve signal yapıları kullanılmıştır.
         * await işlemi signal gelene kadar durdurur ve kilidi kaldırır.
         * signal geldiğinde bekleyen await'lerden biri aktive olur ve kilitde aktive olur.
         * Böylece producer consumer'ın, consumerda producer'ın kilidini ve bekleme işlemini sonlandırır.
         * await birden çok thread tarafından üzerinde çalışılan kaynağı doğru durdurma ve işlemi devam ettirme olanağı sağlar.
         */
    }

    public void example16WithArray() {
        ArrayBlockingQueue<String> resource = new ArrayBlockingQueue<>(4);

        Thread worker1 = new Thread(new BlockProducer(resource, Constants.itemsForPC), "Producer-1");
        Thread worker2 = new Thread(new BlockProducer(resource, Constants.itemsForPC), "Producer-2");

        Thread worker3 = new Thread(new BlockConsumer(resource, 2), "Consumer-1");
        Thread worker4 = new Thread(new BlockConsumer(resource, 6), "Consumer-2");
        Thread worker5 = new Thread(new BlockConsumer(resource, 3), "Consumer-3");

        worker1.start();
        worker2.start();
        worker3.start();
        worker4.start();
        worker5.start();
        /*
         * exampleWithLock işleminin aynısını hazır ArrayBlockingQueue yardımıyla yapılmış örneğidir.
         */
    }

    public void example17() {
        Semaphore worker = new Semaphore(4);
        SemaphoreSource resource = new SemaphoreSource();
        Counter counter = new Counter();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new SemaphoreTask(worker, resource, counter), "Task-" + i);
            thread.start();
        }
    }

    public void example18() {
        int maxPermits = 4;

        Semaphore worker = new Semaphore(maxPermits);
        SemaphoreSource resource = new SemaphoreSource();

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int permits = random.nextInt(maxPermits) + 1;
            Thread thread = new Thread(new PermitTask(worker, resource, permits), "Task-" + i);
            thread.start();
        }
    }
}
