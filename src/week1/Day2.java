package week1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Day2 {

}
/**
 * 1. Exceptions
 */

/**
 * 2. Finally
 */

//JAVA Collections

/**
 * 3. Array
 */

/**
 * 4. List
 */

/**
 * 5. Diff between ArrayList and LinedList
 */

/**
 * 6. HashMap
 *      hashcode()
 *      equals()
 */

/**
 * 7. HashSet
 */


/**
 * 8. Iterator
 */
class IteratorTest{
    public static void main(String[] args) throws Exception {


        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        Thread t1 = new Thread(()->{
            Iterator<Integer> it = list.iterator();
            try{
                Thread.currentThread().sleep(2000);
            }catch(Exception ex){

            }
            it.next();
            it.remove();
        });
        t1.start();
        Thread.currentThread().sleep(1000);
        //list.add(3);
        t1.join();

    }
}