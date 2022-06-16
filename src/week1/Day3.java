package week1;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.function.Function;

public class Day3 {
}
/**
 * JVM Memory model
 *      stack + heap
 *      stack: (Last in first out)
 *          local variable reference
 *          method frame(method parameter, local variable)    fib(10) fib(9) fib(8)....
 *
 *      heap:
 *          object, class meta data, method execution code...
 *      [eden ][survivor0][s1] young generation (serial GC/parallel GC/parallelNewGC)
 *      [old                 ] old generation (parallelOldGC/CMS concurrentMarkSweep)
 *      [                    ] permanent
 *      STW
 *      Concurrent Mark Sweep
 *          1.initial Mark (STW)
 *          2.concurrent mark
 *          3. final mark(STW)
 *          4. concurrent sweep
 *      gc root:(work as start point for mark)
 *          classLoader, class, monitor...
 *      flow:
 *      once obj  created : eden ->(after survive several gc)->  s0/s1 -> old
 *      minor gc:(happend in young)
 *      major gc:
 *      full gc:
 *          g1
 *          [][S][E][][O][][]
 *          [][][S][U][][][]
 *          ...
 *          [][][][][][][]
 *
 *
 *      Can we force GC running?
 *          No, we can only give suggestion
 *      System.gc();
 *      finalize();
 *
 *
 *
 *
 *
 */
class Test10 {
    @Override
    public void finalize(){
        System.out.println("I am out!");
    }

    public static void main(String[] args) {
        new Test10();
        System.gc(); //suggestion
    }
}

/**
 *  Thread
 *      create new thread?
 *          1.extend a thread class
 *          2. provide a runnable instance
 *
 *      1. create thread -> new
 *      2. start() -> active
 *      3. sleep()(wont release the lock)/wait() (release the lock that the thread curr holding) -> wait/block notify()
 *      4. terminated
 *
 *
 */
class ThreadTest extends Thread{
    @Override
    public void run(){
        try{
            Thread.sleep(3000);
        }catch (Exception ex){
        }

        System.out.println("My name is: "+Thread.currentThread().getName());
    }
}

class Test11 {
    public static void main(String[] args) throws Exception{
//        ThreadTest t1 = new ThreadTest();
//        t1.start();
        //t1.join();
        //what is runnable functional interface
//        Function<Integer,Integer> func = (x)->{return 1;};
//        System.out.println(func.apply(10));
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("i am anonymous class");
            }
        };
        Thread t1 = new Thread(runnable);
        t1.start();
        t1.start();
        //lambda expression -> anonymous class that implement the runnbable interface-> instantiate object from
        //t1.start();
    }
}

/**
 * Volatile itself cannot secure threadsafe
 *       t1     t2
 *      cache  cache
 *      memory
 *      1. visibility
 *      2. mfence
 *      3. prevent reordering
 *      boolean flag =true;        t2
 *                                  if(!flag) {
 *                                      sout(a);
 *                                  }
 *      volatile int a=1;
 *      {
 *          a =10;
 *          flag= false;
 *      }
 *
 *      ...
 *
 */
class VisTest {
    static volatile boolean flag = false;

    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(()->{
            while(!flag){}
            System.out.println("i am out!"); // main, t1 -> always check the flag
        });
        t1.start();
        Thread.sleep(3000);
        flag =true;

    }
}


/**
 * Race condition
 *  int i =1
 *           T1   T2
 *    read   101  101
 *    write  102
 *                102
 *                103
 */
class TestRace{
    static volatile int i;
    static synchronized void addOne(){
        i++;
    }
    synchronized void addOneNon(){
        i++;
    }

    public static void main(String[] args) throws Exception{
        TestRace tr1 = new TestRace();
        TestRace tr2 = new TestRace();
        Thread t1 = new Thread(()->{
            for(int i=0;i<10000;i++){
                tr1.addOneNon();
            }
        });
        Thread t2 = new Thread(()->{
            for(int i=0;i<10000;i++){
                tr1.addOneNon();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}

/**
 * Synchronized work with monitor
 *      if synchronized static block, thread try to acquire class monitor
 *      if synchronized non-static, the object monitor is acquired
 */

