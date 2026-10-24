package exercises0003;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MainClassForExercises0003 {
    public static void main(String[] args){

        List<String> stringList = Arrays.asList(
                "Dhawan", "Sachin", "Kohli", "Shreyas", "KL Rahul", "Dhoni", null, "Jadeja", " ", "Bhuvanesh", "", "Shami"
        );

        List<Integer> integerList = Arrays.asList(
                10, 2, 3, null, 4, 5, 9, 6, 7, 100, 98
        );

        //Use lambda with forEach to print a list of strings with "Hello " prefix.
        System.out.println("Use lambda with forEach to print a list of strings with \"Hello \" prefix");
        stringList.forEach(s -> {
            if(s!=null && !s.isBlank()){
                System.out.println("Hello "+s);
            }
        });

        //Sort a list of integers in descending order using lambda Comparator.
        List<Integer> integerListDesc = integerList.stream()
                .filter(Objects::nonNull)
                .sorted((a,b) -> b.compareTo(a))
                .toList();

        System.out.println("Sort a list of integers in descending order using lambda Comparator, integerListDesc : "+integerListDesc);

        //Filter even numbers from a list using Predicate and lambda.
        List<Integer> evenIntegerList = integerList.stream()
                .filter(n -> n!=null && n%2==0)
                .toList();

        System.out.println("Filter even numbers from a list using Predicate and lambda, evenIntegerList : "+evenIntegerList);

        //Use Consumer to print each element of a list with uppercase.
        System.out.println("Use Consumer to print each element of a list with uppercase");
        stringList.stream()
                .filter(s -> s!=null && !s.isBlank())
                .forEach(s -> System.out.println(s.toUpperCase()));

        //Create a Supplier that generates random numbers (1–100)
        Random random = new Random();

        Supplier<Integer> supplierForRandomNumber_From_1_To_100 = () -> random.nextInt(1, 101);
        System.out.println("Create a Supplier that generates random numbers (1–100) : "+supplierForRandomNumber_From_1_To_100.get());
        System.out.println("Create a Supplier that generates random numbers (1–100) : "+supplierForRandomNumber_From_1_To_100.get());
        System.out.println("Create a Supplier that generates random numbers (1–100) : "+supplierForRandomNumber_From_1_To_100.get());

        //Use Function to convert a list of strings to uppercase.
        Function<String, String> upperCaseFunction = String :: toUpperCase;
        List<String> upperCaseStringList = stringList.stream()
                .filter(s -> Objects.nonNull(s) && !s.isBlank())
                .map(upperCaseFunction)
                .toList();
        System.out.println("Use Function to convert a list of strings to uppercase, upperCaseStringList : "+upperCaseStringList);

        //Chain Function: Convert string to uppercase, then to length.
        List<Integer> lengthOfTheStringsInTheStringList = stringList.stream()
                .filter(s -> Objects.nonNull(s) && !s.isBlank())
                .map(s -> s.toUpperCase().length())
                .toList();
        System.out.println("Chain Function: Convert string to uppercase, then to length, lengthOfTheStringsInTheStringList : "+lengthOfTheStringsInTheStringList);

        //Use Predicate to filter names starting with "S" from a list.
        List<String> nameStartsWithSInTheStringList = stringList.stream()
                .filter(s -> Objects.nonNull(s) && !s.isBlank() && s.startsWith("S"))
                .toList();
        System.out.println("Use Predicate to filter names starting with \"S\" from a list, nameStartsWithSInTheStringList : "+nameStartsWithSInTheStringList);

        //Implement a custom functional interface "Calculator" with add and subtract methods using lambda
        Calculator addMethod = Integer::sum;
        Calculator subtractMethod = (a,b) -> a-b;

        System.out.println("addMethod.operate(6,4) : "+addMethod.operate(6,4));
        System.out.println("subtractMethod.operate(6,4) : "+subtractMethod.operate(6,4));

        //Use lambda with Runnable to print "Hello from thread".
        Runnable runnable = () -> System.out.println("Hello from thread "+Thread.currentThread().getName());

        Thread t1 = new Thread(runnable, "Thread_01");
        t1.start(); //Running the runnable from thread t1
        runnable.run(); //Running the runnable from thread main

        System.out.println("Directing printing from "+Thread.currentThread().getName()); //Just to show the orders can vary because thread behaviors are non-deterministic



    }
}
