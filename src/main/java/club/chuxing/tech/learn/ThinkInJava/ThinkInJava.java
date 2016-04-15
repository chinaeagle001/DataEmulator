package club.chuxing.tech.learn.ThinkInJava;


import java.util.List;

/**
 * Created by xuezhangying on 3/10/16.
 */

class Person {
    String name;
    int age;
    public Person() {
        name = "a";
        age = 1;
    }
}

public class ThinkInJava {
    public static void main(String[] args) {
        Person p = new Person();
        set(p);
        System.out.println(p.name);
    }

    public static  void set(final Person p) {
        p.name = "sfd";
    }

    public static void addd(final List<Person> ps) {
        ps.add(new Person());
    }
}
