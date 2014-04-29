package com.ivan.utils.lang.comparison;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorsTest {
    private static class Person implements Comparable<Person> {
        private final String name;

        private final int age;

        private final int id;

        private static int idSeq;

        public Person(final String name, final int age) {
            this.name = name;
            this.age = age;
            id = idSeq++;
        }

        public int getAge() {
            return age;
        }

        @Override
        public int compareTo(final Person o) {
            System.out.println("name comparison: " + name + "  " + o.name);
            return name.compareTo(o.name);
        }

        @Override
        public String toString() {
            return name + " " + age + " (" + id + ")";
        }
    }

    public static void main(final String[] args) {
        final CompositeComparator<Person> cmp = CompositeComparator.<Person> newBuilder()
                .add(new Comparator<Person>() {
                    @Override
                    public int compare(final Person o1, final Person o2) {
                        final int a1 = o1.getAge();
                        final int a2 = o2.getAge();
                        System.out.println(" age comparison: " + a1 + "  " + a2);
                        return a1 < a2 ? -1 : a1 > a2 ? 1 : 0;
                    }
                })
                .add(new ComparableComparator<Person>())
                .build();

        final List<Person> people = new ArrayList<Person>();
        people.add(new Person("Joe", 26));
        people.add(new Person("Sally", 32));
        people.add(new Person("Joe", 17));
        people.add(new Person("Joe", 26));
        people.add(new Person("Sally", 26));

        Collections.sort(people, cmp);
        for (final Person person : people) {
            System.out.println(person);
        }
    }
}
