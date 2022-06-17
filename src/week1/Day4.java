package week1;


import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Day4 {

}
/**
 * Race condition
 *  int i =1
 *           T1   T2
 *    read   101  101
 *    write  102
 *                103
 *
 *
 *
 */
class Test12{
    int i;

    Test12 t12 =new Test12();
}

/**
 * 1. CAS compare and set  (one instruction) atomic
 *      Unsafe: CompareAndSet(obj,offset ,v, v+1)
 *    volatile + CAS:  optimistic lock
 *                     better to use when there is not much updating
 *
 */
class AtomicTest{
    static AtomicInteger ai = new AtomicInteger(0);
    static void incre(){
        ai.getAndIncrement();
    }

    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(()->{
            for(int i=0;i<10000;i++) {
                incre();
            }
        });
        Thread t2 = new Thread(()->{
            for(int i=0;i<10000;i++) {
                incre();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(ai.get());
    }
}

/**
 * 2. Blocking Queue
 *      que 16
 *      producer -> add element into que
 *      consumer -> poll element from que
 */
//[][][][][][][]
class MyBQ{
    //class Node{}
    //Node[] arr = new Node[16];
    Queue<Integer> que;
    int capacity;
    MyBQ(int capa){
        capacity = capa;
        que = new LinkedList<>();
    }
    synchronized void offer(int ele){
        //15 <16
        while(que.size()>=capacity){
            try{
                this.wait();      // p1, p2, c1 , c2
            }catch (Exception ex){}
        }

        que.offer(ele);
        this.notifyAll();//actually we only want to invoke the consumer

    }
    synchronized int poll(){
        while(que.size()<=0) {
            try{
                this.wait();
            }catch (Exception ex){}
        }
        int res =que.poll(); //there will empty slot in the queue
        this.notifyAll();
        return res;

    }

    public static void main(String[] args) {
        MyBQ bq = new MyBQ(16);
        Thread producer1 = new Thread(()->{
            bq.offer(1);
        });
        Thread producer2 = new Thread(()->{
            bq.offer(1);
        });
    }
}
/**
 * Disadvantages for synchronized
 *      1. only one waiting list
 *      2. no fair lock
 *      3. no try lock
 */


/**
 * ReentrantLock resolve the three cons for the synchronized
 *      lock();
 *      lock();
 *      unlock();
 *      unlock();
 */
class MyBQ2{
    //class Node{}
    //Node[] arr = new Node[16];
    Queue<Integer> que; //FIFO
    int capacity;
    ReentrantLock lock = new ReentrantLock(true);
    Condition producerWl = lock.newCondition();
    Condition consumerWl = lock.newCondition();

    MyBQ2(int capa){
        capacity = capa;
        que = new LinkedList<>();
    }
    void offer(int ele){
        //15 <16
//        if(!lock.tryLock()){
//            return;
//        }
        lock.lock();
        while(que.size()>=capacity){
            try{
               producerWl.await();  //release the lock current lock     // p1, p2, c1 , c2
            }catch (Exception ex){}
        }

        que.offer(ele);
        lock.unlock();
        consumerWl.signal();


    }
    int poll(){
        lock.lock();
        while(que.size()<=0) {
            try{
                consumerWl.await();
            }catch (Exception ex){}
        }
        int res =que.poll(); //there will empty slot in the queue
        lock.unlock();
        producerWl.signal();
        return res;

    }
}

/**
 * DeadLock
 * Thread 1 holding lock1 -> try lock 2
 * Thread 2 holding lock2 -> try lock 1
 * Solution:
 *      1.always lock in order
 *      2.try lock, after certain time not acquire all lock need, we just release all the lock
 *      3. look up table
 */
class TestDeadLock{
    public static void main(String[] args)throws Exception {
        Object o1 = new Object();
        Object o2 = new Object();
        Thread t1 = new Thread(()->{
            try{
                synchronized (o1){
                    Thread.sleep(2000);
                    synchronized (o2){
                        System.out.println("i am here1!");
                    }
                }
            }catch(Exception ex){}
        }) ;
        Thread t2 = new Thread(()->{
            try{
                synchronized (o1){
                    Thread.sleep(2000);
                    synchronized (o2){
                        System.out.println("i am here2!");
                    }
                }
            }catch(Exception ex){}
        }) ;

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}

/**
 *  ThreadPool
 *      reuse the thread, creation of thread is expensive
 *
 *      [t4][t5][..][][][][] block queue+ worker thread
 *      pool.submit(task);
 *      Executor, ExecutorService, Executors
 *            interface             provide some predefine thread
 *
 *
 *      Diff type of threadPool:
 *          1.cachedThreadPool()
 *          public ThreadPoolExecutor(int corePoolSize, // number of core thread 3
 *                                    int maximumPoolSize, // max number of thread in pool
 *                                    long keepAliveTime,
 *                                    java.util.concurrent.TimeUnit unit,
 *                                    java.util.concurrent.BlockingQueue<Runnable> workQueue )
 *          2. fixedThreadPool  ThreadPoolExecutor core == max
 *          3. ScheduleThreadPool ScheduledThreadPoolExecutor
 *          4. forkJoinPool:
 *                  origin (1^2+ 2^2+ ...+10^2)
 *                  fork into multiple small task (1^2+2^2) (3^2+4^2) (...)
 *                  join
 *                  steal alg
 */
class TestThreadPool{
    public static void main(String[] args) throws Exception{
//        Executor pool = Executors.newCachedThreadPool();
//        pool.execute(()->{
//            System.out.println("here");
//        });
        ExecutorService pool = Executors.newCachedThreadPool();
        Executors.newScheduledThreadPool(1);
        Future<Integer> future = pool.submit(()-> 10);
        System.out.println(future.get());
        ScheduledExecutorService schePool = Executors.newScheduledThreadPool(1);
        ForkJoinPool fp = new ForkJoinPool();
        
//        Callable task = new Callable() {
//            @Override
//            public Object call() throws Exception {
//                return null;
//            }
//        };
//        Runnable task2 = new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        };
    }

}