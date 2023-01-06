# Multi Threading And Concurrency

## Thread Safety Classes
1. <span style="color:orange">Lock Objects </span>
   1. Interfaces and their implementations
2. <span style="color:orange">Concurrent Collections </span>
   1. Thread safe data structures
3. <span style="color:orange">Volatile And Atomic Variables </span>
   1. Thread safe for primitive types
4. <span style="color:orange">Executors for thread-pool management </span>


### 1 - Lock and semaphores
- Implementations from <span style="color:red">java.util.concurrent<span>
- TimeUnit and Enum for scheduling and task executor service

#### Specific Lock types
- ConcurrentHashMap
  - HashMap
  - Farklı threadlerden aynı nesneye erişilmeye ve yazılmaya çalışıldığında <span style="color:red">ConcurrentModificationException</span> hatası alınmaktadır.
  - Hashmap nesnesine göre daha yavaş olabilir. Çünkü threadlere aynı anda erişime izin vermez.
- CopyOnWriteArrayList
  - ArrayList
  - Iterator objesi immutable yapı döner ve bu dönen yapı ile silme ve create yapılamız.
  - Bu sırada farklı bir threadde her hangi bir değişiklik olsa bile göreceğimiz değerler değişmemiş olacaktır.
  - iterator o ana ait son durumu iletir. 
  - Yani iterator alındıktan sonra bir add yapılmış olsun. Biz o değişikliği bu iterator ile göremeyiz.
- PriorityBlockingQueue
  - PriorityQueue
- ArrayBlockingQueue

### 2 - Volatile And Atomic Variables
- Volatile
  - Bu değer thread safety'yi garanti eder. Her thread nesnedeki güncel değeri okur.
  - Threadler direk memoryden değeri alır. Local cache'den değil.
  - Yazıldığında da direk memory'yi günceller
- Atomic Variable
  - Primitive type variable'lar için thread safety'yi sağlar.
  - Atomic diğerlerinden farklıdır. Compare and swap tekniği uygular. 
  - Threadler birbirini bloklamazlar ama memoryde eski değer için kontrol yaparlar.
  - Beklenen değer ile mevcut değer aynı ise yazma işlemini gerçekleştirirler.
  - Diğer araçlara göre daha az yüke sebep olur.


> **Not:** Tüm bu araçlar tread safety'yi sağladılar ancak birçok thread ve iş parçacığını blokladılar (Atomic hariç). 
> Buda uygulamanın yavaşlamasına yol açacaktır.

### 3 - Executors And Thread Pool
- Executors And Thread Pools
  - Threadler ayaktaki projede iş yürütebilir ortamlardır.
  - Bu sayıyı sınırlandırabiliriz.
  - Executors ise threadler ile alakalı tüm süreçleri yöneten yapıdır.
  - Taskların istekleri buraya ulaşır.
  - Scheduling, process işlemleri gibi gibi işlemleri yönetir.
    - Örneğin diyelim ki 4 çalışabilir thread'e sahibiz. 
    - Her gelen işlem bir threadde çalışmaya başlar. 
    - Ancak 4.den fazla gelen istek eğer thread havuzunda boşta bir thread yoksa sıraya(queue) alınır.
    - Boşa çıkan thread oldukça sıradan işlemler alınmaya devam edilir.
  - Yukarıdaki örnek executor servis tarafından yönetilir. Kaynak alanı ise thread pooldur.
- Tasklar 2 tip olabilir. Task generate için implement edilirler ve override edilen fonk. ile çalışırlar.
  - Runnable
    - run() ile çalışır.
    - Return yoktur. Run ile direk çalışır.
  - Callable
    - call() ile çalışır. 
    - Return vardır. Çalışması bittiğinde değer döndürür.