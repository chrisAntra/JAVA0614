package week2;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;


public class Day6 {

}
/**
 *  stream use single thread
 *  parallel stream
 *      [1,2,3,4,5] -> [1*2, 2*2...] (step 1) -> [1, 2]  -> filter(step2)
 *                                      [3,4,5]
 *      using fork join pool
 *
 */
class TestParallel {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(5,10,6,8,12);
        List<Integer> result = list.parallelStream()
                .map(x->{
                    System.out.println("curr thread: "+Thread.currentThread().getName()+"work on step 1");
                    return 2*x;
                })
                .filter(x->{
                    System.out.println("curr thread: "+Thread.currentThread().getName()+"work on step 2");
                    return x>5;
                })
                .collect(()-> new LinkedList(),
                        (res, x)-> res.add(x),
                        (l1, l2)->l1.addAll(l2));
        System.out.println(result);
    }
}

/**
 * Completable Future
 *      fully asynchronous
 *      provide chain operation
 *      allOf()  anyOf() combine the list of completable future
 */
class TestFuture {
    public static void main(String[] args) throws Exception{
        String s1= "abc";
        String s2 = "bca";

        ExecutorService es = Executors.newFixedThreadPool(1);
        //Future<Integer> future = es.submit(call);
        Future<Integer> future = es.submit(()->{
            try{
                System.out.println("start sleeping");
                Thread.sleep(5000);
            }catch(Exception ex){}
            return 10;
        });
        int x = future.get(); //blocking method just like the join()
        System.out.println("resume running");
        Future futureStep2 = es.submit(()->{
            System.out.println(2*x);
        });
        //System.out.println(future.get()); //get() will block main thread until the worker thread finished it task callable
        System.out.println("in the main");
        //es.shutdown();
        //join(), get()
    }
}
class TestCompletableFuture {
//    public static void main(String[] args) {
//        CompletableFuture cf = CompletableFuture.supplyAsync(()->{
//            try{
//                System.out.println("start sleeping");
//                Thread.sleep(5000);
//            }catch(Exception ex){}
//            return 10;
//        }).thenAccept(x->{
//            System.out.println("the next step: "+ 2*x);
//        });
//
//        System.out.println("In the main thread");
//
//        //... other logic in main
//        System.out.println("main has finished all the work");
////        cf.thenAccept(x->{
////            System.out.println("the next step: "+ 2*x);
////        }).join();
//
//        cf.join();
//    }
    public static void main(String[] args) {
        List<CompletableFuture> completableFutureList = IntStream.rangeClosed(0,4) // 0 1 2 3 4
                .collect(()->new LinkedList<>(), (res, x)->{ // x:each of 0 1 2 3 4
                    res.add(CompletableFuture.supplyAsync(()->{
                        try{
                            Thread.sleep(x*1000);
                        }catch(Exception ex){}
                        System.out.println("This is the step 1 of request: "+ x);
                        return x;
                    }));
                }, (l1,l2)->{});
        //cf variable does not contain the actual return value              the allOf only take array of cf
        CompletableFuture cf = CompletableFuture.anyOf(completableFutureList.toArray(new CompletableFuture[0]));
        cf.thenAccept(Void->{
            completableFutureList.parallelStream().forEach(task->{
                task.thenAccept(x->{
                    System.out.println("This is the step 2 of request "+ x);
                    System.out.println("the final result: "+ 2*(int)x);
                }).join();
            });
        });
        cf.join();


    }
}
