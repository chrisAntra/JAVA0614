package week2;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Day5 {

}
/**
 * Java 8 New Features
 *      static default method for interface
 */
/**
 *  1. functional interface
 *      only contains one abstract method
 *      runnable
 *      callable
 *      comparator
 *      supplier
 *      consumer
 *      function R apply(T t)
 *      bifunction
 *      predicate boolean test(T t)
 *
 *
 *
 */

/**
 *  runnable                     vs         callable
 *  run()                                    call()
 *  void                                     return object
 *  cannot handle checked exception          provide throws can handle check exception
 */


class Test123{
    Consumer csm = new Consumer() {
        @Override
        public void accept(Object o) {

        }
    };
    static Supplier spl = new Supplier() {
        @Override
        public Object get() {
            return null;
        }
    };
    static Comparator comparator = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return 0;
        }
    };
    static Comparator<Integer> com2 = (x1,x2)->x2-x1;

    public static void main(String[] args) throws Exception{
        String s1= "abc";
        String s2 = "bca";

        ExecutorService es = Executors.newFixedThreadPool(1);
        //Future<Integer> future = es.submit(call);
        Future<?> future = es.submit(run);
        future.get();
        //System.out.println(future.get()); //get() will block main thread until the worker thread finished it task callable
        System.out.println("in the main");
        //es.shutdown();
        //join(), get()
    }

    static Runnable run = new Runnable() {
        @Override
        public void run(){
            //throw new Exception();
            //...

            int x = 1/0;
        }
    };
    static Callable<Integer> call = new Callable() {
        @Override
        public Integer call() throws Exception {
            Class clz = Class.forName("week2.Day5");
            throw new Exception();
            //return 100;
        }
    };
}




/**
 *  2. lambda expression
 *  () -> {return 10;}
 *  ()-> 10
 */
class Test1111{
    public static void main(String[] args) {
        Supplier spl = ()-> {
            return 10;
        }; //provide an instance with an implemented method
        Comparator<Integer> cmp = (x1, x2) -> {
            return x1-x2;
        };
        List<Integer> list  = Arrays.asList(1,10,9,5,6);
        System.out.println(list);
        //System.out.println(spl.get().getClass());

    }
}
/**
 * 3. optional
 *
 */
class OptionalTest {
    public static void main(String[] args) {
        Optional<Integer> opInt = Optional.ofNullable(null);

        Integer x = null;
        System.out.println(opInt.orElseGet(()->{
            //...
            System.out.println("there is no value present , so i provide the default value...");
            return 99;}));

    }
}

/**
 * 4. stream api
 *      list of integer
 *
 *      use to replace for loop
 *      achieve chain operation
 *
 *      create a stream obj
 *          .stream()
 *          Arrays.stream()
 *          chars()  : string ->intStream
 *      intermediate step:
 *          map  provide function f i   x -> 2*x
 *          filter  predicate     x-> boolean
 *          mapToInt
 *      terminate step
 *          forEach void
 *          collect()  Collectors
 *          collect()  supplier, biConsumer, biConsumer
 *          reduce()
 *
 *
 *

 */
class TestSteam{
    public static void main(String[] args) {
//
        List<Integer> list = Arrays.asList(1,4,5,6,7,7,10,3);
//        //sum of the list
//        System.out.println(list.stream().reduce(0,(res, x)->res+x));
//        int[] arr = list.stream().mapToInt(x->(int)x).toArray();
//        List<Integer> newList = Arrays
//                                    .stream(arr)
//                                    .mapToObj(x->(Integer)x)//int -> Integer
//                                    .collect(Collectors.toList());
//        List<Integer> res = new LinkedList<>();
////        for(int x: list){
////            x= 2*x;
////            if(x>5) res.add(x);
////        }
//        res = list
//                .stream()
//                .map(x->x*2)
//                .filter( x-> x>5)
//                .collect(()-> new LinkedList<>(), (r,x)->{if(x>5) r.add(x);}, (fl, l)->{fl.addAll(l);});
//
//        //System.out.println(list);
//        //System.out.println(res);
        String test = "aabbuidac";
        HashMap<Character, Integer> hm =test
                                    .chars()
                                    .mapToObj(x-> (char)x) // map int(acii code) -> Character
                                    .collect(()-> new HashMap<Character,Integer>(), (res, x)-> {
                                        res.put(x,res.getOrDefault(x,0)+1);
                                    }, (h1,h2)->{});
        Collections.sort(list);
        List<List<Integer>> res = list
                                    .stream()
                                    .collect(()-> {
                                        List<List<Integer>> list1 = new LinkedList<>();
                                        list1.add(new LinkedList<>());
                                        list1.add(new LinkedList<>());
                                        return list1;
                                    }, (res1, x)-> {
                                        if(x<5){
                                            res1.get(0).add(x);
                                        }else{
                                            res1.get(1).add(x);
                                        }

                                    },(l1,l2)->{})
                                    .stream()
                                    .collect(()->new LinkedList(), (res2, l)->{
                                        Collections.sort(l);
                                        res2.add(l);
                                    },(l1,l2)->{});
        System.out.println(res);


        //System.out.println(hm);
        //LeetCode 49 group anagram



    }
}