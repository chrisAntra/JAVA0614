package week1;

import com.sun.jdi.PathSearchingVirtualMachine;

import java.util.*;

public class Day2 {
    void display() throws Exception{}
    public static void main(String[] args) {
//        try{
//            Class clz = Class.forName("week1.Day2");
//        }catch(Exception ex){
//            System.out.println(ex);
//            //...
//        }
        try{
            Integer[] arr = new Integer[2];
            //System.out.println(arr[1].equals(1));
        }catch (IllegalArgumentException ex) {
            System.out.println(ex);
        }catch (NullPointerException nex){
            System.out.println(nex);
        }finally {
            System.out.println("i am finally block");
        }

//        int[] arr = new int[2];
//        System.out.println(arr[1]);
//        Integer[] arr = new Integer[2];
////        arr[1]=1;
//        System.out.println(arr[1].equals(1));
    }

}
/**
 * 1. Exceptions
 *      Throwable
 *      /        \
 *     error     Exceptions:
 *               1. checked exceptions
 *                  ClassNotFound
 *                  FileNotFound
 *                  ...
 *               2. unchecked exceptions
 *                  ArrayIndexOutOfBoundsException
 *                  NullPointer
 * two ways to resolve the exception:
 *      1. throws
 *      2. try/ catch/ finally
 */


/**
 * 2. Finally
 */

//JAVA Collections

/**
 * 3. Array
 *      1. fix size
 *      2. contigous memory allocation O(1) access by index
 *      3. be cautious primitive type / object
 */


/**
 * 4. List
 *      ArrayList vs LinkedList
 */
class ListDemo{
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        List<Integer> list1= new LinkedList<>();
    }
}


/**
 * 5. Diff between ArrayList      and   LinkedList
 *               array                  node + pointer
 *      1. access time    O(1)              O(N)
 *      2. resize        O(N)           no need resize
 *      3. continuous memory allocation      random
 *      4. insertion/deletion           might have O(N) search time
 *                                      O(1) manipulate pointers
 *         when we need resize
 *         O(N)
 *
 */

/**
 * 6. HashMap
 *      hashcode()
 *      equals()
 *
 *      K V
 *      Node - either be List, be red black tree
 *      Node[]  constant access
 *          8 24(n) 3(k)
 *          we will use hashcode of key, hc%sz      8    1, 9 17 ... O(K)
 *
 *      Conclusion:
 *          1.Node[]
 *          2.bucket: LinkedList/redblacktree node
 *          3.put(k, v) -> putVal()
 *          4.putVal:
 *              use hashcode to compute the index of node array: (n - 1) & hash
 *              check whether there is a key-val exist in the bucket
 *              if not we just insert the new key val
 *              if it is, then we first check the first node in the bucket,
 *              if we found the target key, we just update the value
 *              if not we will either search in the bn search tree, or we just loop the linkedlist
 *              if after all we loop all list, and not found the key, we just insert the new node
 *              if we found the target key, we just update value
 *
 *      HashMap is not threadsafe
 *      HashTable is threadsafe lock the whole table
 *      ConcurrentHashMap  lock the node entry SIZE OF 16 , at most have 16 thread
 *
 *
 */
class Stud{
    int id;
    String name;

    public Stud(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stud stud = (Stud) o;
        return id == stud.id && Objects.equals(name, stud.name);
    }
}
class TestHashMap{
    public static void main(String[] args) {
        //HashMap
        Stud stu1 = new Stud(1,"x");
//        Stud stu2 = new Stud(1,"x");
//        System.out.println(stu1.equals(stu2));
        HashMap<Stud, Integer> hm = new HashMap<>();
        hm.put(stu1,10);
        stu1.id=10;
        Stud stu3 = new Stud(10,"x");
        System.out.println(hm.get(stu3));
    }

}

/**
 * 7. HashSet
 *      internally just a hashmap with a dummy value
 *     List   vs   Set
 *     order       no order
 *     duplicate   no duplicate
 *
 *
 */
class SetDemo{
    public static void main(String[] args) {
        //Hashtable<Integer,Integer> ht = new Hashtable<>();
    }
}
/**
 * TreeMap using the redblack tree to store all the k v pairs, it will sort by the key
 */


/**
 * 8. Iterator
 */
class IteratorTest{
    public static void main(String[] args) throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
//        for(Integer ele: list){
//            list.remove(0);
//        }
       // System.out.println(list);
        Iterator<Integer> it = list.iterator();
        while(it.hasNext()){ //1 2 3
            if(it.next()==2) it.remove();
        }
        System.out.println(list);
//        Thread t1 = new Thread(()->{
//            Iterator<Integer> it = list.iterator();
//            try{
//                Thread.currentThread().sleep(2000);
//            }catch(Exception ex){
//
//            }
//            it.next();
//            it.remove();
//        });
//
//        t1.start();
//        Thread.currentThread().sleep(1000);
//        //list.add(3);
//        t1.join();

    }
}