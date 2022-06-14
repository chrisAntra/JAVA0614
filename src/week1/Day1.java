package week1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day1 {
    public void display(){}
}
/**
 * 1. OOP
 *      class instance method attribute related to instance/class
 *
 *      Polymorphism:
 *          object: upcasting
 *          method: overload  compile poly
 *                  overriding runtime poly
 *      Inheritance:
 *          reuse code
 *
 *      Encapsulation:
 *          access modifier:
 *              default
 *              *private
 *              public
 *              protected
 *      Abstraction:
 *          hiding detail
 *              Interface
 *                  implement multiple interface at same time
 *                  cannot constructor
 *                  before java8:
 *                      method: public abstract
 *                      attribute: public final static
 *                  after java8: static & default method
 *                  after java9: private method
 *
 *
 *              abstract class
 *                  does not support multiple inheritance just like normal class
 *                  either abstract method, normal method
 *
 */
class PolyTest{
    private int a;

    public int getA() {
        return a;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list = new LinkedList<>();
    }
    static int dummyMethod(int i){
        return 1;
    }
    static boolean dummyMethod(){
        return false;
    }
    void display(){
        System.out.println("i am parent class");
    }
}
interface I1{
    int a=1;
    void display();
    static void staticTest(){}
    default void dummyMethod(){
        ppp();
        System.out.println("this is default");
    }
    private void ppp(){}

}
interface I2{
    int a=2;
}

class A implements I1, I2{
    public static void main(String[] args) {
        I1.staticTest();
//        A a = new A();
//        a.dummyMethod();
    }

    public void display(){
        System.out.println(I1.a);
        System.out.println(I2.a);
    }
}
class ChildClz extends PolyTest{
    @Override
    void display() {
        System.out.println("i am child class");
    }

    public static void main(String[] args) {
//        PolyTest cc = new ChildClz();
//        cc.display();
        List<Integer> list = new LinkedList<>();
        list = new ArrayList<>();
        //loose coupling
    }
}



/**
 * 2. Static
 *      cannot inherit
 *      method, attribute belongs to the class itself, not related to instance
 *      static block vs non-static block
 */
class Test{

    public static void main(String[] args) throws Exception{
        //TestStatic ts = new TestStatic();
        Class clz = Class.forName("week1.TestStatic");
        TestStatic ts = new TestStatic();

    }
}
class TestStatic{

    static {
//        System.out.println("i am non static");
        System.out.println("i am static");
    }
    static class InnerClz{}
    class InnerClzNon{}
    public static void main(String[] args) {
        InnerClz ic = new InnerClz();
        TestStatic ts = new TestStatic();
        InnerClzNon ic1 = ts.new InnerClzNon();
//        TestStatic ts = new TestStatic();
//        TestStatic ts1 = new TestStatic();
//        TestStatic ts2 = new TestStatic();
    }
//    void nonStatic(){}
//    void dummyMethod(){
//        this.nonStatic();
//        //"this" refer to
//    }
//    static void stMethod(){
////        TestStatic ts = new TestStatic();
////        ts.nonStatic();
//    }
}
//class Test{
//    public static void main(String[] args) {
//        TestStatic.stMethod();
//    }
//}

/**
 * 3. Java pass by value
 */
class Student{
    int id;
}
class TestPass{
//    static void changeValue(int a){
//        a=10;
//    }
    static void changeId(Student s){
        s.id=10;
    }
    static void changeStu(Student s){
        Student s2 = new Student();
        s2.id=10;
        s=s2;
    }
// stu , s
    public static void main(String[] args) {
        Student stu = new Student(); //stu(address value) -> new Student()
        stu.id=1;
        System.out.println("before: "+ stu.id);
//        changeId(stu); // s(address value)
        changeStu(stu);
        System.out.println("after: "+stu.id);
//        int i=1;
//        System.out.println("before: "+ i);
//        changeValue(i);
//        System.out.println("after: " +i);
    }
}

/**
 * 4. Final
 *      variable: the value cannot be changed once initialized
 *      method: method cannot be overriden
 *      class: cannot be inherited
 */
final class Ftest{
    final int a=1;
//    Ftest(){
//        a=1;
//    }
}

/**
 * 5. Immutable
 *      String
 *      wrapper class Integer, Boolean
 *
 */
class TestImm{

    public static void main(String[] args) {
//        String str = "abc";
//        //str ="aaaa";
//        String res="";
//        StringBuilder sb = new StringBuilder();
//        for(int i=str.length()-1;i>=0;i--){
//            //res+= str.charAt(i);
//            sb.append(str.charAt(i));
//            //"c" "cb" "cba"
//
//        }
//        System.out.println(sb.toString());
//        Integer a=127;
//        Integer b=127;
//        int a=128;
        Integer b=128;
        Integer a=128;
        //-128 ~ 127
        System.out.println(b==a);//equals
    }
}
//Immutable List 1. cannot change address value 2. cannot the element inside node
final class MyList{
    private final List<Integer> list; //list =new LinkedList(); list.add(); list.remove()
    public MyList(List<Integer> ls){
        //deepcopy
        list = new LinkedList<>(ls);
    }
    public List<Integer> getList(){
        //deepcopy
        return new LinkedList<>(list);
    }
}
class Test2{
    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        MyList ml = new MyList(list);
        List<Integer> ls = ml.getList();
        ls.add(1);


        list.add(1);
    }
}

