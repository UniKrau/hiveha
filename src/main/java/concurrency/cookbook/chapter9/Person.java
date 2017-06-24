package concurrency.cookbook.chapter9;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by lhao on 6/20/17.
 */
public class Person {

    public enum Sex {
        MALE, FEMALE
    }

    String name;
    LocalDate birthday;
    Sex gender;
    String emailAddress;

    public int getAge() {
        // ...

        return  0;
    }

    public void printPerson() {
        // ...
    }

    // One simplistic approach is to create several methods;
    // each method searches for members that match one characteristic, such as gender or age.
    // The following method prints members that are older than a specified age:

    public static void printPersonsOlderThan(List<Person> roster, int age) {

        for (Person p: roster) {

            if (p.getAge() >= age)
                p.printPerson();
        }
    }

}
