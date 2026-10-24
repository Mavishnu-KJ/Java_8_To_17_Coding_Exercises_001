package exercises0002;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MainClassForExercises0002 {
    public static void main(String[] args){

        List<String> stringList = Arrays.asList(
                "Dhawan", "Sachin", "Kohli", "Shreyas", "KL Rahul", "Dhoni", null, "Jadeja", " ", "Bhuvanesh", "", "Shami"
        );

        List<Integer> integerList = Arrays.asList(
                10, 2, 3, null, 4, 5, 9, 6, 7, 100, 98
        );

        String nonNullValueString = "Funkynshot";
        String nullValueString = null;
        String emptyString = "";


        //Use lambda with forEach to print a list of strings with "Hello " prefix.
        stringList.forEach(s -> {
            if(s != null && !s.isBlank()){
                System.out.println("Hello "+s);
            }
        });

        //Sort a list of integers in descending order using lambda Comparator.
        List<Integer> integerListDesc = integerList.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.reverseOrder())
                .toList();

        System.out.println("Sort a list of integers in descending order using lambda Comparator, integerListDesc is "+integerListDesc);

        //Filter even numbers from a list using Predicate and lambda.
        List<Integer> evenIntegerList = integerList.stream()
                .filter(n -> n!=null && n%2==0)
                .toList();

        System.out.println("Filter even numbers from a list using Predicate and lambda, evenIntegerList is "+evenIntegerList);

        //Use Consumer to print each element of a list with uppercase.
        System.out.println("Use Consumer to print each element of a list with uppercase");
        stringList.forEach( s->{
                if(s!=null && !s.isBlank()){
                    System.out.println(s.toUpperCase());
                }
        });

        //Create a Supplier that generates random numbers (1–100).
        Random random = new Random();
        Supplier<Integer> supplier = () -> random.nextInt(1,101);
        System.out.println("Create a Supplier that generates random numbers (1–100)");
        System.out.println(supplier.get()+", "+supplier.get()+", "+supplier.get()+", "+supplier.get());

        //Use Function to convert a list of strings to uppercase.
        Function<String, String> upperCaseFunction = String::toUpperCase;
        List<String> upperCaseStringList = stringList.stream()
                .filter(s -> Objects.nonNull(s) && !s.isBlank())
                .map(upperCaseFunction) //This is also correct
                //.map(s -> upperCaseFunction.apply(s)) //This is also correct
                .toList();
        System.out.println("Use Function to convert a list of strings to uppercase, upperCaseStringList is "+upperCaseStringList);

        //Chain Function: Convert string to uppercase, then to length.
        List<Integer> lengthOfTheStringsInTheStringList = stringList.stream()
                .filter(s -> Objects.nonNull(s) && !s.isBlank())
                .map(s -> s.toUpperCase().length())
                .toList();
        System.out.println("Chain Function: Convert string to uppercase, then to length, lengthOfTheStringsInTheStringList is "+lengthOfTheStringsInTheStringList);

        //Use Predicate to filter names starting with "S" from a list.
        Predicate<String> namesStartingWithSPredicate = str -> str.startsWith("S");
        List<String> namesStartsWithSInTheStringList = stringList.stream()
                .filter(s -> s!=null && !s.isBlank() && namesStartingWithSPredicate.test(s))
                .toList();

        System.out.println("Use Predicate to filter names starting with \"S\" from a list, namesStartsWithSInTheStringList is "+namesStartsWithSInTheStringList);

        //Implement a custom functional interface "Calculator" with add and subtract methods using lambda.
        Calculator addFunction = (a, b) -> a+b;
        Calculator subtractFunction = (a, b) -> a-b;

        System.out.println("Implement a custom functional interface \"Calculator\" with add and subtract methods using lambda");
        System.out.println("addFunction.operate(6,4) is "+addFunction.operate(6,4));
        System.out.println("subtractFunction.operate(6,4) is "+subtractFunction.operate(6,4));

        //Use lambda with Runnable to print "Hello from thread".
        Runnable runnable = () -> System.out.println("Hello from thread "+Thread.currentThread().getName());

        Thread t1 = new Thread(runnable, "Thread_01");
        t1.start(); //Calling runnable from thread t1
        runnable.run(); //Calling runnable from main thread

        System.out.println("Direct Printing from "+Thread.currentThread().getName()); //Just to show the thread running orders may vary because threads are non-deterministic

        //Sort a list of employees by salary using Comparator lambda
        List<Employee> employeeList = Arrays.asList(
                new Employee(18, "Virat", 55555.00),
                new Employee(10, "Sachin", 88888.00),
                new Employee(7, "Dhoni", 77777.00)
        );

        List<Employee> employeeListSortedBySalary = employeeList.stream()
                .filter(Objects::nonNull)
                //.sorted((e1, e2) -> e1.employeeSalary().compareTo(e2.employeeSalary()))
                .sorted(Comparator.comparingDouble(Employee::employeeSalary))
                .toList();
        System.out.println("Sort a list of employees by salary using Comparator lambda, employeeListSortedBySalary is "+employeeListSortedBySalary);

        //Group a list of strings by length desc using Collectors.groupingBy with lambda
        Map<Integer, List<String>> stringListGroupingByLengthDesc = stringList.stream()
                .filter(s -> s!=null && !s.isBlank())
                .collect(
                        Collectors.groupingBy(
                                String :: length,
                                () -> new TreeMap<>((a,b) -> b.compareTo(a)),
                                Collectors.toList()
                        )
                );

        System.out.println("Group a list of strings by length desc using Collectors.groupingBy with lambda, stringListGroupingByLengthDesc is \n"+stringListGroupingByLengthDesc);

        //Use Optional with lambda: If value present, print it; else print default.
        //Value present
        Optional.ofNullable(nonNullValueString).ifPresentOrElse(
                (value) -> System.out.println(value),
                () -> System.out.println("default")
        );
        //Value not present
        Optional.ofNullable(nullValueString).ifPresentOrElse(
                (value) -> System.out.println(value),
                () -> System.out.println("default")
        );

        //empty string will be considered as valid value in the context of Optional
        Optional.ofNullable(emptyString).ifPresentOrElse(
                (value) -> System.out.println(value),
                () -> System.out.println("default")
        );

        //Create a Predicate that checks if a number is prime (using lambda).
        Predicate<Integer> isPrimeNumber = n -> {

            //Prime number rules
            //0 is not a prime number
            //1 is not a prime number
            //2 is only one even prime number
            //if n%2 == 0, not prime number
            //i<=Math.sqrt(n) or i*i <= n

            if(n <= 1){
                return false;
            }

            if(n==2){
                return true;
            }

            if(n%2==0){
                return false;
            }

            for(long i=3; i*i<=n; i=i+2){
                if(n%i==0){
                    return false;
                }
            }

            return true;

        };

        System.out.println("Create a Predicate that checks if a number is prime (using lambda)");
        System.out.println("Is 0 prime? "+isPrimeNumber.test(0));
        System.out.println("Is 1 prime? "+isPrimeNumber.test(1));
        System.out.println("Is 2 prime? "+isPrimeNumber.test(2));
        System.out.println("Is 3 prime? "+isPrimeNumber.test(3));
        System.out.println("Is 33 prime? "+isPrimeNumber.test(33));

        //Use lambda with Stream: Filter names starting with "D", map to uppercase, collect to list.
        List<String> nameStartsWithDInTheStringList = stringList.stream()
                .filter(s -> Objects.nonNull(s) && !s.isBlank() && s.startsWith("D"))
                .map(String::toUpperCase)
                .toList();

        System.out.println("Use lambda with Stream: Filter names starting with \"D\", map to uppercase, collect to list, nameStartsWithSInTheStringList : "+nameStartsWithDInTheStringList);

        //Implement a method reference for static method (e.g., String::toUpperCase).

        Function<String, String> toUpperCase = String::toUpperCase;
        Function<String, Integer> parseInt = Integer::parseInt;
        Function<Integer, Integer> mathAbs = Math::abs;
        Function<LocalDate, Month> getMonthFromLocalDate = LocalDate::getMonth;

        System.out.println("Method Reference using Function, toUpperCase.apply(\"Funkynshot\") " + toUpperCase.apply("Funkynshot")); //Output: FUNKYNSHOT
        System.out.println("Method Reference using Function, parseInt.apply(\"500\") " + parseInt.apply("500")); //Output: 500
        System.out.println("Method Reference using Function, mathAbs.apply(-12) " + mathAbs.apply(-12)); //Output: 12
        System.out.println("Method Reference using FUnction, getMonthFromLocalDate.apply(LocalDate.of(2026,2,19))) " + getMonthFromLocalDate.apply(LocalDate.of(2026, 2, 19))); //Output : FEBRUARY

        //Use constructor reference to create a new ArrayList.
        Supplier<ArrayList<String>> arrayListSupplier = ArrayList :: new;

        List<String> arrayListFromSupplier = arrayListSupplier.get();
        arrayListFromSupplier.add("Hey");
        arrayListFromSupplier.add("Hi");
        arrayListFromSupplier.add("Cool");
        System.out.println("Use constructor reference to create a new ArrayList, arrayListFromSupplier : "+arrayListFromSupplier);

        //Handle checked exception in lambda (wrap in try-catch or custom functional interface).


    }
}
