package exercises0001;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainClassForExercises0001 {

    public static void main(String[] args) {

        List<String> stringList = Arrays.asList(
                "Sachin", "Shewag", "Gambhir", "Virat", "", "Yuvraj", null, "Dhoni", "Raina"
        );

        List<String> stringListWithDuplicates = Arrays.asList(
                "Sachin Tendulkar", "Virat Kohli", "Dhoni", "Raina", null, "", "Jadeja", "   ", "Dhoni", "Raina"
        );

        List<List<String>> nestedStringList = List.of(
                Arrays.asList("Alpha", "Beta", "Gamma"),
                Arrays.asList("Delta, Sigma")
        );

        List<Optional<String>> optionalStringlist = Arrays.asList(
                Optional.of("Alpha"),
                Optional.empty(),
                Optional.of("Beta"),
                Optional.of("Gamma")
        );

        //System.out.println("nestedStringList is "+nestedStringList);

        List<Integer> integerList = Arrays.asList(10, 44, 18, 12, 7, 3);
        List<Integer> emptyList = List.of();

        List<List<Integer>> nestedIntegerList = List.of(
                Arrays.asList(1, 3, 5, 7),
                Arrays.asList(2, 4, 6, 8)
        );
        //System.out.println("nestedIntegerList is "+nestedIntegerList);

        String nonNullValueString = "Funkynshot";
        String nullValueString = null;

        List<LocalDate> localDateList = Arrays.asList(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 12),
                LocalDate.of(2026, 3, 3),
                LocalDate.of(2026, 10, 10),
                LocalDate.of(2026, 2, 2),
                null,
                LocalDate.now(),
                LocalDate.of(2026, 1, 5),
                LocalDate.of(2026, 1, 3),
                LocalDate.of(2026, 1, 4),
                LocalDate.of(2026, 2, 3)
        );

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        String multiLinesText = """
                Sachin Tendulkar 
                Dhoni
                Virat Kohli 
                
                Jadeja
                Raina
                
                KL Rahul
                
                """;

        String testStringForStrip = "   Hey  see here !! ";

        //Use lambda with forEach to print a list of strings with "Hello " prefix
        stringList.forEach(s -> {
            if (s != null && !s.isBlank()) {
                System.out.println("Hello " + s);
            }
        });

        //Sort a list of integers in descending order using lambda Comparator.
        List<Integer> integerListDesc = integerList.stream()
                .filter(Objects::nonNull)
                .sorted((a, b) -> b.compareTo(a))
                .toList();
        System.out.println("Sort a list of integers in descending order using lambda Comparator : " + integerListDesc);

        //Filter even numbers from a list using Predicate and lambda.
        List<Integer> evenIntegerList = integerList.stream()
                .filter(n -> Objects.nonNull(n) && n % 2 == 0)
                .toList();

        System.out.println("Filter even numbers from a list using Predicate and lambda : " + evenIntegerList);

        //Use Consumer to print each element of a list with uppercase.
        //NOTE : map is the FUNCTION, forEach is the Consumer
        System.out.println("Use Consumer to print each element of a list with uppercase :");
        stringList.forEach(s -> {
            if (Objects.nonNull(s) && !s.isBlank()) {
                System.out.println(s.toUpperCase());
            }
        });

        //Create a Supplier that generates random numbers (2–100)
        Random random = new Random();
        Supplier<Integer> supplier = () -> random.nextInt(100 - 2 + 1) + 2;
        //NOTE : formula is random.nextInt(max-min+1)+min

        //If Interviewer wants modern way random.ints
        Supplier<Integer> supplier1 = () -> random.ints(2, 101).findFirst().orElse(2);

        //Thread-safe version (if interviewer asks about concurrency)
        Supplier<Integer> supplier2 = () -> ThreadLocalRandom.current().nextInt(2, 101);

        System.out.println("Supplier that generated random numbers (2–100), random number : " + supplier.get());
        System.out.println("Supplier that generated random numbers (2–100), random number : " + supplier1.get());
        System.out.println("Supplier that generated random numbers (2–100), random number : " + supplier2.get());

        //Use Function to convert a list of strings to uppercase
        //NOTE : map is a FUNCTION, forEach is a CONSUMER
        List<String> upperCaseStringList = stringList.stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .map(String::toUpperCase)
                .toList(); // Use collect(Collectors.toList()) if Java 8-15 compatibility needed

        System.out.println("Use Function to convert a list of strings to uppercase : " + upperCaseStringList);

        //Chain Function: Convert string to uppercase, then to length.
        List<Integer> lengthOfStringsInStringList = stringList.stream()
                .filter(s -> s != null && !s.isBlank())
                .map(s -> s.toUpperCase().length())
                .toList();
        System.out.println("Chain Function: Convert string to uppercase, then to length : " + lengthOfStringsInStringList);

        //Use Predicate to filter names starting with "S" from a list
        List<String> stringStartsWithS = stringList.stream()
                .filter(s -> Objects.nonNull(s) && !s.isBlank() && s.startsWith("S"))
                .toList();
        System.out.println("Use Predicate to filter names starting with \"S\" from a list : " + stringStartsWithS);

        //Implement a custom functional interface "Calculator" with add and subtract methods using lambda.
        //NOTE : Functional Interface is also known as Single Abstract Method (SAM) interface
        Calculator add = Integer::sum; // We can use (a,b) -> a+b as well
        Calculator subtract = (a, b) -> a - b;

        System.out.println("Using custom functional Interface Calculator, addition of 6,4 is " + add.calc(6, 4));
        System.out.println("Using custom functional Interface Calculator, subtraction of 6,4 is " + subtract.calc(6, 4));

        //Use lambda with Runnable to print "Hello from thread"
        Runnable runnable = () -> System.out.println("Hello From Thread " + Thread.currentThread().getName());

        //Thread t1 = new Thread(runnable); //If we do not mention the thread name, by default it will be Thread-0
        Thread t1 = new Thread(runnable, "Thread_01"); //Defining thread name as Thread 01
        t1.start(); //Calling runnable from thread t1
        runnable.run(); //Calling runnable from main thread

        System.out.println("Printing Hello from main"); //Just to show the thread running orders may vary because threads are non-deterministic

        //Sort a list of employees by salary using Comparator lambda.
        List<Employee> employeeList = Arrays.asList(
                new Employee(10L, "Sachin", 88888),
                new Employee(18L, "Virat Kohli", 55555),
                new Employee(7L, "Dhoni", 66666)
        );

        List<Employee> employeeListSortedBySalary = employeeList.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(Employee::getEmployeeSalary))
                //.sorted((e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()))
                .toList();

        System.out.println("Sort a list of employees by centuries using Comparator lambda : " + employeeListSortedBySalary);

        //Find highest paid employee
        Employee highestPaidEmployee = employeeList.stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(Employee::getEmployeeSalary))
                .orElse(null);
        System.out.println("Highest paid employee : " + highestPaidEmployee);

        //Find second highest paid employee
        Employee secondHighestPaidEmployee = employeeList.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(Employee::getEmployeeSalary).reversed())
                .limit(2)
                .skip(1)
                .findFirst()
                .orElse(null);
        System.out.println("Second Highest paid employee : " + secondHighestPaidEmployee);

        //Assuming the Employee can be null inside EmployeeList, So use null-check Comparator and sort the employeeListBySalary descending
        List<Employee> employeeListSortedBySalaryDesc = employeeList.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.nullsLast(Comparator.comparingInt(Employee::getEmployeeSalary).reversed()))
                .toList();
        System.out.println("Sort a list of employees by centuries descending : " + employeeListSortedBySalaryDesc);

        //Group a list of strings by length descending using Collectors.groupingBy with lambda
        Map<Integer, List<String>> stringListGroupingByLengthDesc = stringList.stream()
                .filter(s -> s != null && !s.isBlank())
                //.map(s -> s.length())
                //.sorted(Comparator.reverseOrder())
                .collect(Collectors.groupingBy(
                        String::length,
                        () -> new TreeMap<>((a, b) -> b.compareTo(a)),
                        Collectors.toList()
                ));

        System.out.println("Group a list of strings by length descending using Collectors.groupingBy with lambda : " + stringListGroupingByLengthDesc);

        //Group a list of strings by length ascending using Collectors.groupingBy with lambda
        Map<Integer, List<String>> stringListGroupingByLengthAsc = stringList.stream()
                .filter(s -> Objects.nonNull(s) && !s.isBlank())
                //.map(s -> s.length())
                //.sorted(Comparator.naturalOrder())
                .collect(Collectors.groupingBy(
                        String::length,
                        TreeMap::new, //Equivalent to () -> new TreeMap<>()
                        Collectors.toList()
                ));

        System.out.println("Group a list of strings by length ascending using Collectors.groupingBy with lambda : " + stringListGroupingByLengthAsc);

        //Use Optional with lambda: If value present, print it; else print default
        Optional<String> optionalWithNonNullValueString = Optional.ofNullable(nonNullValueString);
        Optional<String> optionalWithNullValueString = Optional.ofNullable(nullValueString);

        //Case 1: If value is present
        optionalWithNonNullValueString.ifPresentOrElse(
                (value) -> System.out.println(value), //First lambda: Consumer if value present // Can use System.out::println instead 
                () -> System.out.println("default") //Second lambda: Runnable if absent
        );

        //Case 2: If value is not present
        optionalWithNullValueString.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("default")
        );

        //Use Optional with lambda: If value present, print it; else print default, NOTE : Don't use ifPresentOrElse
        //Case 1: Value is present
        System.out.println(optionalWithNonNullValueString.orElse("default"));
        //Case 2: Value is not present
        System.out.println(optionalWithNullValueString.orElse("default"));

        //Create a Predicate that checks if a number is prime (using lambda)
        //Prime number is a number which is divided by 1 and itself
        //1 is not a prime number by the prime number rule
        //2 is the only even prime number
        //Numbers divided by 2 are not the prime numbers
        Predicate<Integer> isPrime = n -> {
            if (n < 2)
                return false;
            if (n == 2)
                return true;
            if (n % 2 == 0)
                return false;
            for (long i = 3; i <= Math.sqrt(n); i = i + 2) { //Use long for i in loop (safer for very large n)
                if (n % i == 0)
                    return false;
            }
            return true;
        };

        System.out.println("Is 1 prime number ? " + isPrime.test(1));
        System.out.println("Is 2 prime number ? " + isPrime.test(2));
        System.out.println("Is 3 prime number ? " + isPrime.test(3));
        System.out.println("Is 8 prime number ? " + isPrime.test(8));
        System.out.println("Is 33 prime number ? " + isPrime.test(33));

        //Use lambda with Stream: Filter names starting with "S", map to uppercase, collect to list.
        List<String> upperCaseStringList1 = stringList.stream()
                .filter(s -> s != null && !s.isBlank() && s.startsWith("S"))
                .map(String::toUpperCase)
                .toList();

        System.out.println("Use lambda with Stream: Filter names starting with \"S\", map to uppercase, collect to list : " + upperCaseStringList1);

        //Implement a method reference for static method (e.g., String::toUpperCase, Integer::parseInt, Math::abs)
        Function<String, String> toUpperCase = String::toUpperCase;
        Function<String, Integer> parseInt = Integer::parseInt;
        Function<Integer, Integer> mathAbs = Math::abs;
        Function<LocalDate, Month> getMonthFromLocalDate = LocalDate::getMonth;

        System.out.println("Method Reference using Function, toUpperCase.apply(\"Funkynshot\") " + toUpperCase.apply("Funkynshot")); //Output: FUNKYNSHOT
        System.out.println("Method Reference using Function, parseInt.apply(\"500\") " + parseInt.apply("500")); //Output: 500
        System.out.println("Method Reference using Function, mathAbs.apply(-12) " + mathAbs.apply(-12)); //Output: 12
        System.out.println("Method Reference using FUnction, getMonthFromLocalDate.apply(LocalDate.of(2026,2,19))) " + getMonthFromLocalDate.apply(LocalDate.of(2026, 2, 19))); //Output : FEBRUARY

        //Use constructor reference to create a new ArrayList.
        Supplier<ArrayList<Integer>> arrayListSupplier = ArrayList::new;

        ArrayList<Integer> arrayList1FromSupplier = arrayListSupplier.get();
        arrayList1FromSupplier.add(10);
        arrayList1FromSupplier.add(7);

        ArrayList<Integer> arrayList2FromSupplier = arrayListSupplier.get();
        arrayList2FromSupplier.add(18);
        arrayList2FromSupplier.add(1);

        System.out.println("Using constructor reference, created arrayList arrayList1FromSupplier is " + arrayList1FromSupplier);
        System.out.println("Using constructor reference, created arrayList arrayList2FromSupplier is " + arrayList2FromSupplier);

        //Handle checked exception in lambda (wrap in try-catch or custom functional interface).

        //1. Wrap in try-catch
        //NOTE : NullPointerException, RunTimeException are unchecked exceptions
        //NOTE : IOException, ParseException are checked exceptions
        System.out.println("\nHandling checked exception in lambda by wrap in try-catch : ");
        stringList.forEach(s -> {
                    if (s != null && !s.isBlank()) {
                        try {
                            if (s.startsWith("S")) {
                                throw new IOException("String starts with S");
                            }
                            System.out.println(s);
                        } catch (IOException e) {
                            System.out.println("Caught checked exception : " + e.getMessage());
                        }
                    }
                }
        );

        //Using custom functional interface
        System.out.println("\nHandling checked exception in lambda using custom functional interface : ");
        ThrowIOException<String> throwIOExceptionInterface = (s) -> {
            if (s.startsWith("S")) {
                throw new IOException("String starts with S");
            }
        };

        stringList.forEach(s -> {
                    if (s != null && !s.isBlank()) {
                        try {
                            throwIOExceptionInterface.throwIOException(s);
                            System.out.println(s);
                        } catch (IOException e) {
                            System.out.println("Caught checked exception : " + e.getMessage());
                        }
                    }
                }
        );


        //Use BiFunction to add two numbers with lambda
        BiFunction<Integer, Integer, Integer> additionUsingBiFunction = (a, b) -> a + b; //We can use Integer::sum instead
        System.out.println("Addition of 6, 4 using BiFunction, additionUsingBiFunction.apply(6,4) is " + additionUsingBiFunction.apply(6, 4));

        //Combine Predicate and Consumer: Filter and print only matching elements.
        System.out.println("Combined Predicate and Consumer, Printing only the matching elements :");
        stringList.stream()
                .filter(s -> s != null && !s.isBlank() && s.startsWith("S")) //Predicate
                .forEach(System.out::println); //Consumer

        //Create Stream from list and print all elements using forEach
        System.out.println("Created stream from list and printing all elements using forEach : ");
        integerList.stream()
                .filter(Objects::nonNull)
                .forEach(System.out::println);

        //Filter even numbers from list and print using forEach
        System.out.println("Even numbers from integerList : ");
        integerList.stream()
                .filter(n -> Objects.nonNull(n) && n % 2 == 0)
                .forEach(System.out::println);

        //Map list of strings to uppercase and collect to List
        List<String> upperCaseStringList2 = stringList.stream()
                .filter(s -> Objects.nonNull(s) && !s.isBlank())
                .map(String::toUpperCase)
                .toList();

        System.out.println("Map list of strings to uppercase and collect to List, upperCaseStringList2 is " + upperCaseStringList2);

        //Sort list of integers ascending using sorted
        List<Integer> integerListSortedAsc = integerList.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.naturalOrder()) // We can use just sorted() instead for ascending order
                .toList();

        System.out.println("Sort list of integers ascending using sorted, integerListSortedAsc is " + integerListSortedAsc);

        //Sort descending using sorted(Comparator.reverseOrder()).
        List<Integer> integerListSortedDesc = integerList.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.reverseOrder())
                .toList();

        System.out.println("Sort descending using sorted, integerListSortedDesc is " + integerListSortedDesc);

        //Count elements in list using count
        long numberOfElementsInStringList = stringList.stream()
                //.filter(Objects::nonNull)
                .count();

        System.out.print("number of elements in stringList is : " + numberOfElementsInStringList);

        //Find first element using findFirst (or Optional).
        String firstElement = stringList.stream()
                .filter(s -> s != null && !s.isBlank())
                .findFirst()
                .orElse(null);

        System.out.println("Find first element using findFirst (or Optional), firstElement is " + firstElement);

        //Check if any number is even using anyMatch
        boolean isIntegerListHasEven = integerList.stream()
                .filter(Objects::nonNull)
                .anyMatch(n -> n % 2 == 0);

        System.out.println("integerList " + integerList + " has even number ? " + isIntegerListHasEven);

        //Using anyMatch with emptyList
        boolean isEmptyListHasEven = emptyList.stream()
                .filter(Objects::nonNull)
                .anyMatch(n -> n % 2 == 0);

        System.out.println("emptyList " + emptyList + " has even number ? " + isEmptyListHasEven);

        //Check if all numbers are positive using allMatch
        boolean isAllNumbersInIntegerListPositive = integerList.stream()
                .filter(Objects::nonNull)
                .allMatch(n -> n > 0);

        System.out.println("integerList " + integerList + " has all numbers positive ? " + isAllNumbersInIntegerListPositive);

        //Check if no number is negative using noneMatch.
        boolean isNoNegativeNumberInIntegerList = integerList.stream()
                .filter(Objects::nonNull)
                .noneMatch(n -> n < 0);

        System.out.println("integerList " + integerList + " has no negative numbers ? " + isNoNegativeNumberInIntegerList);

        //Reduce to sum of list using reduce

        //Method 1 : Using orElse
        Integer sumOfIntegersInTheIntegerList = integerList.stream()
                .filter(Objects::nonNull)
                .reduce((a, b) -> a + b)// We can use Integer::sum instead
                .orElse(null);
        System.out.println("Reduce to sum of list using reduce, using Optional, sumOfIntegersInTheIntegerList is " + sumOfIntegersInTheIntegerList);

        //Method 2 : Using Optional<Integer>
        Optional<Integer> sumOfIntegersInTheEmptyList = emptyList.stream()
                .filter(Objects::nonNull)
                .reduce(Integer::sum); // It will return Optional.empty if the list is empty
        System.out.println("Reduce to sum of list using reduce, using Optional, sumOfIntegersInTheEmptyList is " + sumOfIntegersInTheEmptyList);

        //Method 3 : Use int instead of Integer
        int sumOfIntegersInTheIntegerList1 = integerList.stream()
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                //.reduce(0,Integer::sum);
                .sum();

        System.out.println("Reduce to sum of list using int instead of Integer, sumOfIntegersInTheIntegerList1 is " + sumOfIntegersInTheIntegerList1);

        /* NOTE : prefer using int instead of Integer, reduce(0, Integer::sum) instead of reduce(Integer:;sum) if not Optional
            reduce(Integer::sum) will give Optional.empty incase of the list is empty
            reduce(0, Integer::sum) will give 0 in case of the list is empty - This is the expected output,
            So, prefer reduce(0, Integer::sum) over reduce(Integer::sum)
         */

        //Reduce to product of list using reduce
        long productOfIntegersInTheIntegerList = integerList.stream()
                .filter(Objects::nonNull)
                .mapToLong(Integer::longValue)
                .reduce(1L, (a, b) -> a * b); // We can use Integer::multiplyExact instead

        //NOTE : We can use Math::multiplyExact to throw ArithmeticException on overflow, RECOMMENDED

        System.out.println("Reduce to product of list using reduce, productOfIntegersInTheIntegerList is " + productOfIntegersInTheIntegerList);

        //FlatMap: Flatten List<List<Integer>> to List<Integer>
        List<Integer> flattenedList = nestedIntegerList.stream()
                .filter(Objects::nonNull)
                //.flatMap(innerList->innerList.stream()) //For each inner List<Integer>, it calls .stream() → returns Stream<Integer>
                .flatMap(innerList -> innerList == null ? Stream.empty() : innerList.stream())
                //.flatMap(List::stream) // We can use List::stream instead but only if innerList is not null
                .toList();

        System.out.println("Flattened the nestedIntegerList " + nestedIntegerList + " to " + flattenedList);

        //Remove duplicates using distinct.
        List<String> stringListWithoutDuplicates = stringListWithDuplicates.stream()
                .filter(s -> s != null && !s.isBlank())
                .distinct()
                .toList();

        System.out.println("Removed duplicates using distinct, stringListWithoutDuplicates is " + stringListWithoutDuplicates);

        /* NOTE : If interviewer asks "does distinct remove based on equals?"
        Answer: "Yes — distinct() uses Object.equals() and hashCode().
        For custom objects, override them if needed."
         */

        //Limit to first 5 elements
        List<Integer> first5ElementsInIntegerList = integerList.stream()
                .filter(Objects::nonNull)
                .limit(5)
                .toList();

        System.out.println("Limit to first 5 elements, first5ElementsInIntegerList is " + first5ElementsInIntegerList);

        //Skip first 3 elements
        List<Integer> skippedFirst3ElementsInIntegerList = integerList.stream()
                .filter(Objects::nonNull)
                .skip(3) //skip first 3 elements
                .toList();

        System.out.print("Skipped first 3 elements , skippedFirst3ElementsInIntegerList is " + skippedFirst3ElementsInIntegerList);

        //Group strings by length using Collectors.groupingBy.
        Map<Integer, List<String>> groupingStringsByLength = stringList.stream()
                .filter(s -> Objects.nonNull(s) && !s.isBlank())
                .collect(Collectors.groupingBy(
                        String::length,
                        () -> new TreeMap<>(), //NOTE : use Linked LinkedHashMap::new to preserve insertion order
                        Collectors.toList()
                ));

        System.out.println("Group strings by length using Collectors.groupingBy, groupingStringsByLength is " + groupingStringsByLength);

        //Partition numbers into even/odd using Collectors.partitioningBy.
        Map<Boolean, List<Integer>> partitionedByEvenOdd = integerList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0
                ));

        System.out.println("Partition numbers into even/odd using Collectors.partitioningBy, partitionedByEvenOdd is " + partitionedByEvenOdd);
        System.out.println("integerList : " + integerList + ", Evens : " + partitionedByEvenOdd.get(true));
        System.out.println("integerList : " + integerList + ", Odds : " + partitionedByEvenOdd.get(false));

        //Using Collectors.counting with Collectors.partitioningBy
        Map<Boolean, Long> partitionedByEvenOddCount = integerList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0,
                        Collectors.counting()
                ));

        System.out.println("integerList : " + integerList + ", partitionedByEvenOddCount : " + partitionedByEvenOddCount);

        //Join strings with comma using Collectors.joining(", ").
        String joinedStringWithDelimeter = stringList.stream()
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(", "));

        System.out.println("Joining Strings with comma using Collectors.joining : " + joinedStringWithDelimeter);

        //Join strings by using Collectors.joining, with delimeter ", ", Prefix "[", Suffix "]"
        String joinedStringWithDelimeterPrefixSuffix = stringList.stream()
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(", ", "[", "]"));

        System.out.println("Joining Strings by using Collectors.joining, with delimeter , Prefix , Suffix  : " + joinedStringWithDelimeterPrefixSuffix);

        //Find max/min in list using max/min + Comparator.
        //Case 1 : Find max in list
        Integer maxInIntegerList = integerList.stream()
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalStateException("List is empty"));

        System.out.println("integerList " + integerList + ", max: " + maxInIntegerList);

        //Case 2 : Fin min in List
        Optional<Integer> minInIntegerList = integerList.stream()
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder());

        minInIntegerList.ifPresentOrElse(
                value -> System.out.println("integerList " + integerList + ", min: " + value),
                () -> System.out.println("List is empty")
        );

        //Reduce to sum of integers in list.
        long sumOfIntegersInIntegerList = integerList.stream()
                .filter(Objects::nonNull)
                .mapToLong(Integer::longValue)
                .reduce(0L, Long::sum);

        System.out.println("integerList : " + integerList + ", sumOfIntegersInIntegerList : " + sumOfIntegersInIntegerList);

        //Reduce to product of integers
        long productOfIntegersInIntegerList = integerList.stream()
                .filter(Objects::nonNull)
                .mapToLong(Integer::longValue)
                .reduce(1L, Math::multiplyExact);

        System.out.println("integerList : " + integerList + ", productOfIntegersInIntegerList : " + productOfIntegersInIntegerList);

        //With overflow handling (if interviewer asks "what if product overflows?"):
        long productSafe;
        try {
            productSafe = integerList.stream()
                    .filter(Objects::nonNull)
                    .mapToLong(Integer::longValue)
                    .reduce(1L, Math::multiplyExact);
        } catch (ArithmeticException e) {
            productSafe = Long.MAX_VALUE;  // or handle as needed
            System.out.println("Overflow detected: " + e.getMessage());
        }

        //Reduce to concatenate strings with delimiter.
        String concatenatedStringWithDelimter = stringList.stream()
                .filter(s -> s != null && !s.isBlank())
                .reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b);

        System.out.println("Used reduce to concatenate string with Delimeter : " + concatenatedStringWithDelimter);

        //FlatMap: Flatten List<List<String>> to List<String>.
        List<String> flattenedStringList = nestedStringList.stream()
                .filter(Objects::nonNull)
                .flatMap(innerList -> innerList == null ? Stream.empty() : innerList.stream())
                .toList();

        System.out.println("nestedStringList " + nestedStringList + " flattened to " + flattenedStringList);

        //FlatMap: Flatten List<Optional<String>> to List<String>.
        List<String> flattenedOptionalStringList = optionalStringlist.stream()
                .filter(Objects::nonNull) //Even Optional can be null, but very rare
                //.flatMap(innerOptional -> innerOptional.isPresent() ? innerOptional.stream() : Stream.empty())
                .flatMap(Optional::stream) //We can simply use Optional::stream instead
                .toList();

        System.out.println("nestedOptionalStringlist " + optionalStringlist + " flattened to " + flattenedOptionalStringList);

        //Java 8 compatible (if interviewer specifies older Java)
        List<String> flattenedOptionalStringList1 = optionalStringlist.stream()
                .filter(Objects::nonNull)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        System.out.println("nestedOptionalStringlist " + optionalStringlist + " flattened to " + flattenedOptionalStringList1);

        //Generate even numbers sequence using Stream.iterate + limit.
        List<Integer> evenNumbers = Stream.iterate(0, n -> n + 2)
                .limit(20) //NOTE : limit is required for infinite streams, else program will hang, eventually run out of memory
                .toList();

        System.out.println("Generate even numbers sequence using Stream.iterate + limit, evenNumbers : " + evenNumbers);

        //Generate 10 random numbers using Stream.generate + limit
        List<Integer> randomNumbers = Stream.generate(() -> new Random().nextInt(2, 101)) //2 to 100 inclusive (bound is exclusive)
                .limit(10) //As they asked 10 random numbers only
                .toList();

        System.out.println("Generate 10 random numbers using Stream.generate + limit, randomNumbers : " + randomNumbers);

        //Create LocalDate for today and print.
        LocalDate today = LocalDate.now();
        System.out.println("today date : " + today);

        //Add 10 days to today's date using plusDays
        LocalDate todayPlus10Days = today.plusDays(10);
        System.out.println("10 days added to today's date : " + todayPlus10Days);

        //Calculate age from birth date using Period.between.
        Period age = Period.between(
                LocalDate.of(2000, 5, 15),
                LocalDate.of(2026, 1, 31)
        );

        System.out.println("Calculated age using Period.between, Age  Years :" + age.getYears() + " Months :" + age.getMonths() + " Days :" + age.getDays());

        //Parse string to LocalDateTime using DateTimeFormatter.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedDateTime = LocalDateTime.parse("2026-02-18 11:40:25", formatter);
        System.out.println("Parse string to LocalDateTime using DateTimeFormatter, parsedDateTime is " + parsedDateTime);

        //Format LocalDateTime to custom pattern (e.g., "dd-MM-yyyy HH:mm")
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedLocalDateTime = formatter.format(LocalDateTime.now());
        System.out.println("Format LocalDateTime to custom pattern (e.g., \"dd-MM-yyyy HH:mm\"), formattedLocalDateTime is " + formattedLocalDateTime);

        //Get current time in different time zones (ZoneId.of("Asia/Kolkata"), "America/New_York").
        ZonedDateTime asiaZonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        ZonedDateTime americaZonedDateTime = ZonedDateTime.now(ZoneId.of("America/New_York"));

        System.out.println("Current time with time zone Asia/Kolkata : " + asiaZonedDateTime);
        System.out.println("Current time with time zone America/New_York : " + americaZonedDateTime);

        //Convert old Date to LocalDateTime
        Date legacyDate = new Date(); //Old Date means legacy date (ie legacy java.util package, modern datetimeapi java.time package)
        Instant instant = legacyDate.toInstant(); // Converting the legacy date to instant
        LocalDateTime localDateTimeFromLegacyDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()); // ofInstant with zoneId systemDefault()
        System.out.println("localDateTimeFromLegacyDate is " + localDateTimeFromLegacyDate);

        //Convert java.time api date to legacy date
        LocalDateTime ldt = LocalDateTime.now(); // java.time package api date
        Instant instant1 = ldt.atZone(ZoneId.systemDefault()).toInstant(); //Converting the new api date to instant
        Date legacyDate1 = Date.from(instant1);
        System.out.println("Converted legacy date from new api date is legacyDate1" + legacyDate1);

        //Duration between two LocalDateTime.
        Duration durationBetweenTwoLocalDateTime = Duration.between(
                LocalDateTime.of(2026, 1, 31, 14, 30),
                LocalDateTime.of(2026, 1, 31, 16, 30)
        );

        System.out.println("Duration between local dates, durationBetweenTwoLocalDateTime is " + durationBetweenTwoLocalDateTime);

        //Find max/min date in list of LocalDate
        //NOTE : max - newest date, min - earliest date
        //1. max date
        LocalDate maxDate = localDateList.stream()
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                //.max((date1, date2)->date1.compareTo(date2)) //This can also be used instead of above
                //.max(LocalDate::compareTo) //This can also be used instead of above
                .orElseThrow(() -> new IllegalStateException("Date list is empty"));

        System.out.println("max date from localDateList is " + maxDate);

        //2. min date
        LocalDate minDate = localDateList.stream()
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                //.min((date1,date2)->date1.compareTo(date2)) //This can also be used instead of above
                //.min(LocalDate::compareTo) //This can also be used instead of above
                .orElseThrow(() -> new IllegalStateException("Date list is empty"));

        System.out.println("max date from localDateList is " + minDate);

        //Group dates by month using groupingBy
        Map<Month, List<LocalDate>> localDatesGroupingByMonth = localDateList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        LocalDate::getMonth, //Function<LocalDate, Month> //localDate -> localDate.getMonth() can also be used instead
                        TreeMap::new,
                        Collectors.toList()
                ));

        System.out.println("Group dates by month using groupingBy, localDatesGroupingByMonth is " + localDatesGroupingByMonth);

        //Infinite stream of Fibonacci numbers (limit 10) using generate
        /*
        NOTE : Fibonacci series formula : F(i) = F(i-1)+F(i-2) (for i>1)
        F(0) = 0, F(1) = 1
        0,1,1,2,3,5,8,...
         */

        Supplier<Long> fibonacciNumberSupplier = new Supplier<Long>() { //Since we are overriding the get method, we should use Anonymous inner class instead of lambda

            private long[] pair = {0, 1};

            @Override
            public Long get() {
                long fib = pair[0]; // return current Fibonacci number
                long next = pair[0] + pair[1]; // calculate next
                pair[0] = pair[1]; // shift
                pair[1] = next; // shift
                return fib;
            }
        };

        List<Long> fibonacciSeries = Stream.generate(fibonacciNumberSupplier)
                .limit(10)
                .toList();

        System.out.println("Infinite stream of Fibonacci numbers, limit 10, fibonacciSeries : " + fibonacciSeries);

        //Infinite stream of Fibonacci numbers (limit 10) using iterate, avoiding mutation
        List<Long> fibIterate = Stream.iterate(
                        new long[]{0, 1}, // seed: [F(n-2), F(n-1)]
                        pair -> new long[]{pair[1], pair[0] + pair[1]} // next pair: [F(n-1), F(n)]
                )
                .map(pair -> pair[0]) // extract the current Fibonacci number
                .limit(10)
                .toList();

        System.out.println("Infinite stream of Fibonacci numbers, limit 10, fibIterate : " + fibIterate);

        //Stream of primes (generate + filter + limit).
        //2,3,5,7,...
        Supplier<Long> primeNumberSupplier = new Supplier<Long>() {
            private long n = 0;

            @Override
            public Long get() {

                n = n + 1; //Incremented for next iteration from Stream

                if (n < 2) {
                    return null;
                } else if (n == 2) {
                    return n;
                } else if (n % 2 == 0) {
                    return null;
                } else {
                    for (int i = 3; i <= Math.sqrt(n); i = i + 2) { //We can use i*i<=n instead of i<=Math.sqrt(n)
                        if (n % i == 0) {
                            return null;
                        }
                    }
                }
                return n;
            }

        };

        List<Long> primeNumbersList = Stream.generate(primeNumberSupplier)
                .filter(Objects::nonNull)
                .limit(10)
                .toList();

        System.out.println("Stream of primes using Stream.generate() + limit 10, primeNumbersList is " + primeNumbersList);

        //Stream of primes using Stream.iterate
        List<Long> primeNumbersListUsingIterate = Stream
                .iterate(2L, n -> n + 1)
                .filter(n -> {
                    if (n < 2) return false;
                    if (n == 2) return true;
                    if (n % 2 == 0) return false;
                    for (int i = 3; i * i <= n; i = i + 2) {
                        if (n % i == 0) return false;
                    }
                    return true;
                })
                .limit(10)
                .toList();

        System.out.println("Stream of primes using Stream.iterate() + limit 10, primeNumbersListUsingIterate is " + primeNumbersListUsingIterate);

        //Debug Stream with peek (print intermediate values)

        // Debug Stream with peek: print intermediate values at each step
        List<Integer> result = numbers.stream()
                .peek(n -> System.out.println("Original: " + n))                  // Step 1: original numbers
                .filter(n -> n % 2 == 0)                                          // Keep only even
                .peek(n -> System.out.println("  After filter (even): " + n))     // Step 2: after filter
                .map(n -> n * 2)                                                  // Double each even number
                .peek(n -> System.out.println("    After map (×2): " + n))        // Step 3: after map
                .limit(4)                                                         // Take only first 4
                .peek(n -> System.out.println("      After limit: " + n))         // Step 4: after limit
                .toList();

        System.out.println("\nFinal result: " + result);

        //Use var for List, Map, Stream.
        var stringsGroupingByLengthDesc = stringList.stream()
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.groupingBy(
                        String::length,
                        () -> new TreeMap<>((a, b) -> b.compareTo(a)),
                        Collectors.toList()
                ));

        System.out.println("Strings grouped by length desc, used var instead of map, stringsGroupingByLengthDesc is " + stringsGroupingByLengthDesc);

        //var with diamond operator
        /*
        var on left + diamond on right (recommended – cleanest)Java
        var list = new ArrayList<String>();               // infers ArrayList<String>
        var map  = new HashMap<Integer, String>();        // infers HashMap<Integer, String>
        var set  = new HashSet<Long>();                   // infers HashSet<Long>
        var strings = new ArrayList<String>();                  // ArrayList<String>
        var numbers = new HashSet<Integer>();                   // HashSet<Integer>
        var map     = new HashMap<String, List<Employee>>();    // HashMap<String, List<Employee>>
        var queue   = new LinkedList<Long>();                   // LinkedList<Long>

        Invalid / Compilation Error Cases (Because Compiler cannot infer)
        var list = new ArrayList<>();          // Error: cannot infer type arguments
        var map  = new HashMap<>();            // Error: diamond needs type on one side

        When to use full type on left:
        => Legacy code
        => Very complex generics (e.g., Map<String, List<Map<Integer, String>>>) — var can make it harder to read
         */

        //var limitations (no initializer).
        /*
        NOTE : var (local variable type inference, Java 10+) cannot be used without an initializer on the right side.
        => The compiler must see an expression on the right to infer the type.
        => No initializer = no type info → compilation error.
        => var requires an initializer on the same line — no exceptions.
        Limitation: No Initializer → Compilation Error :
        var count;           // Error: cannot infer type for local variable count
                     // (no initializer for variable)

        var result;          // Error: cannot infer type for local variable result

        var person;          // Error

        count = 10;          // Even assigning later doesn't help

        Error message (typical):
        error: cannot infer type for local variable count
        (cannot use 'var' on variable without initializer)

        Other Related Limitations of var (Quick Summary):
        Limitation,Example,Allowed?,Reason
        No initializer,var x;,No,Compiler can't infer type
        Null alone,var x = null;,No,null has no type
        Lambda alone,"var func = () -> ""hi"";",No,Lambda needs target type
        Array initializer,"var arr = {1,2,3};",No,Needs new int[]
        Field / method param,var field;,No,var only for local variables
        With diamond only,var list = new ArrayList<>();,No,Diamond needs type on one side

         */

        //String.isBlank() on empty/whitespace.
        long emptyStringsCount = stringListWithDuplicates.stream()
                .filter(s -> s != null && s.isEmpty())
                .count();

        long blankStringsCount = stringListWithDuplicates.stream()
                .filter(s -> s != null && s.isBlank())
                .count();

        System.out.println("stringListWithDuplicates: " + stringListWithDuplicates + ", emptyStringsCount: " + emptyStringsCount + ", blankStringsCount: " + blankStringsCount);

        //NOTE : From the above example, we know that .empty() considers empty space as value,
        // .blank() considers empty space as invalid value.
        // .empty().trim() is equivalent to .blank()

        //String.lines() on multi-line → Stream<String>
        System.out.println("multiLinesText : \n" + multiLinesText);
        List<String> linesAsStringList = multiLinesText.lines() // provides Stream<String>
                .filter(line -> !line.isBlank()) //Optional: Removing blank lines
                .map(String::trim) //Optional: Removing trailing, leading space if any
                .toList();

        System.out.println("linesAsStringList : " + linesAsStringList);

        //strip(), stripLeading(), stripTrailing().
        System.out.println("testStringForStrip : \n'" + testStringForStrip + "'");
        System.out.println("testStringForStrip.strip() : \n'" + testStringForStrip.strip() + "'");
        System.out.println("testStringForStrip.stripLeading() : \n'" + testStringForStrip.stripLeading() + "'");
        System.out.println("testStringForStrip.stripTrailing() : \n'" + testStringForStrip.stripTrailing() + "'");

        //String.repeat(5) on "Hello".
        System.out.println("Hello".repeat(5));
        //NOTE : System.out.println("Hello".repeat(-1)); // Throws IllegalArgumentException

        //Synchronous HTTP GET (print body from https://jsonplaceholder.typicode.com/posts/1).
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                URI.create("https://jsonplaceholder.typicode.com/posts/1")
        )
        .timeout(Duration.ofSeconds(30))
        .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Synchronous HTTP GET, response.statusCode() : " + response.statusCode());
            System.out.println("Synchronous HTTP GET, response.body() : " + response.body());
        }catch(InterruptedException e){
            System.err.println("Synchronous HTTP GET, InterruptedException error : "+e.getMessage());
        }catch(IOException e){
            System.err.println("Synchronous HTTP GET, IOException error : "+e.getMessage());
        }

        //Asynchronous HTTP GET with thenAccept.
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                //.thenApply(HttpResponse::body), if we use like this, will lose Response in further step, cannot print statusCode
                .thenAccept(response->{
                    //System.out.println("Asynchronous HTTP GET, response statusCode : "+response.statusCode());
                    //System.out.println("Asynchronous HTTP GET, response body : "+response.body());
                })
                .exceptionally(e->{
                   //System.out.println("Asynchronous HTTP GET, error : "+e.getMessage());
                   return null;
                });

        /*
        NOTE : Why the asynchronous response is not printing (even after waiting) ?

        client.sendAsync(...) returns a CompletableFuture and starts the HTTP request in the background.
        The moment the main method finishes executing (which happens almost immediately after starting the async call),
        the JVM exits — and any background threads (like the async HTTP client threads) are terminated before
        they can complete and trigger your thenAccept

        => Synchronous send() → blocks until done → prints immediately
        => Asynchronous sendAsync() → non-blocking → main thread finishes → JVM shuts down → callback never centuries

        => So, in order to make async call completion, we need to block main thread until Async done or error
           Will use completableFuture.get()

        => completableFuture.get() blocks and returns the result (or throws exception)

         */

        CompletableFuture<String> completableFuture =  client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
        try{
            String body = completableFuture.get();  // blocks until ready
            //System.out.println("Asynchronous HTTP GET, body : " + body);
        }catch (Exception e){
            System.out.println("Async error : "+e.getMessage());
            e.printStackTrace();
        }

        //Files.readString from test.txt (create file first).
        //D:\Mavishnu\000_PREPARATION\PREPARATION_2025\SpringBoot
        //String path = "D:\\Mavishnu\\000_PREPARATION\\PREPARATION_2025\\SpringBoot\\test.txt"; // We can use \\ or single /
        String path = "D:/Mavishnu/000_PREPARATION/PREPARATION_2025/SpringBoot/test.txt";
        Path filePath = Path.of(path);

        // Create file if it doesn't exist
        if(!Files.exists(filePath)){
            try {
                Files.createFile(filePath);
            }catch (IOException e){
                System.err.println("Files.createFile, IOException : "+e.getMessage());
                e.printStackTrace();
            }
        }

        String content = "";
        try {
            content = Files.readString(filePath);
        }catch (IOException e){
            System.err.println("Files.readString, IOException : "+e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Files.readString, content : "+content);

        //Files.writeString to file
        try{
            Files.writeString(filePath, "Funkynshot");
        }catch (IOException e){
            System.err.println("Files.writeString, IOException : "+e.getMessage());
            e.printStackTrace();
        }

        try {
            System.out.println("Files.readString, after Files.writeString, content : " + Files.readString(filePath));
        }catch (IOException e){
            System.err.println("Files.readString, after Files.writeString, IOException : "+e.getMessage());
            e.printStackTrace();
        }

        //Append instead of overwrite (if you ever want to keep history):
        try{
            Files.writeString(filePath, "\nNewly added text", StandardOpenOption.APPEND);
            System.out.println("Files.readString, after Files.writeString, APPEND content : " + Files.readString(filePath));
        }catch(IOException e){
            System.err.println("Files.writeString, APPEND, IOException : "+e.getMessage());
            e.printStackTrace();
        }

        //list.toArray(String[]::new).
        String[] stringArray = stringList.toArray(String[] :: new); //Converting List<String> stringList into String[] stringArray
        System.out.println("list.toArray(String[]::new), Arrays.toString(stringArray) is "+Arrays.toString(stringArray));
        System.out.println("list.toArray(String[]::new), stringArray.getClass() is "+stringArray.getClass());

        /*
        => String[]::new is a constructor reference of IntFunction<String[]>.
        => The method internally calls generator.apply(list.size()) to create an array of exact size
        => Returns correctly typed array — no cast needed, no warning.
        => Common syntax : Type[] typedArray = Collection.toArray(Type[]::new);
         */

        //list.toArray(Integer[]::new)
        Integer[] integerArray = integerList.toArray(Integer[] :: new);
        System.out.println("list.toArray(Integer[]::new), Arrays.toString(integerArray) is "+Arrays.toString(integerArray));

        //Predicate.not for non-empty check.
        System.out.println("Printing non-empty strings from stringList, using predicate.not : ");

        stringList.stream()
                .filter(Objects::nonNull)
                .filter(Predicate.not(String::isEmpty))
                .forEach(System.out::println);

        //Combine var + HTTP Client.
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder(
                URI.create("https://jsonplaceholder.typicode.com/posts/1")
        ).timeout(Duration.ofSeconds(30)
        ).build();

        try {
            var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Combined var+HttpClient, Response status code : "+httpResponse.statusCode());
            System.out.println("Combined var+HttpClient, Response body : "+httpResponse.body());
        }catch (InterruptedException e){
            System.err.println("InterruptedException error : "+e.getMessage());
        }catch (IOException e){
            System.err.println("IOException error : "+e.getMessage());
        }

        //var with nested generics.
        var flattenedIntegerList = nestedIntegerList.stream()
                //.flatMap(innerList->innerList==null?Stream.empty():innerList.stream())
                .flatMap(Collection::stream)
                .toList();

        System.out.println("var with nested generics, nestedIntegerList"+nestedIntegerList+ ", flattenedIntegerList is "+flattenedIntegerList);

        //Map<Integer, List<String>> optionalStringListGroupingByLength = optionalStringlist.stream()
        var optionalStringListGroupingByLength = optionalStringlist.stream()
                .filter(Objects::nonNull)
                //.filter(Optional::isPresent)
                //.map(Optional::get)
                .flatMap(Optional::stream)//We can use this line instead of above 2, remember its not map, its flatMap
                .collect(Collectors.groupingBy(
                    String::length
                ));

        System.out.println("var with nested generics, optionalStringListGroupingByLength : "+optionalStringListGroupingByLength);

        //Mixed: Multi-line string → lines() → filter non-blank → collect toList, etc
        var nonBlankLinesList = multiLinesText.lines() //var type is List<String> here
                .filter(Predicate.not(String::isBlank))
                .toList();

        System.out.println("Multi-line string → lines() → filter non-blank → collect toList, nonBlankLinesList is "+nonBlankLinesList);

        //Instantiating objects for the record Cricketer
        //Cricketer tendulkar = new Cricketer(); // Will give compilation error as record won't have default implicit no-arg constructor
        Cricketer tendulkar = new Cricketer("Sachin Tendulkar", 100);
        Cricketer dhoni = new Cricketer("MS Dhoni", 16);
        Cricketer kohli = new Cricketer("Virat Kohli", 85);
        Cricketer jadeja = new Cricketer("Ravindra Jadeja", 6);
        Cricketer sooryavanshi = Cricketer.unCappedCricketer("Sooryavanshi"); //Instantiating Object with Static (factory) method

        //Check if he has highest centuries
        System.out.println("Is Tendulkar highest centuries scorer? "+tendulkar.isHighestCenturiesScorer()+", total centuries : "+tendulkar.centuries());
        System.out.println("Is Kohli highest centuries scorer? "+kohli.isHighestCenturiesScorer()+", total centuries : "+kohli.centuries());

        //Check if he has lowest centuries
        System.out.println("Is Dhoni lowest centuries scorer? "+dhoni.isLowestCenturiesScorer(10)+", total centuries : "+dhoni.centuries());
        System.out.println("Is Jadeja lowest centuries scorer? "+jadeja.isLowestCenturiesScorer(10)+", total centuries : "+jadeja.centuries());
        System.out.println("Is Sooryavanshi lowest centuries scorer? "+sooryavanshi.isLowestCenturiesScorer(10)+", total centuries : "+sooryavanshi.centuries());
        
        //Sealed class
        Vehicle vehicle = new Vehicle();
        Vehicle car = new Car();
        Vehicle bike = new Bike();
        Vehicle electricVehicle = new ElectricVehicle();
        Vehicle tesla = new Tesla();

        vehicle.start(); // Output : Vehicle starts...
        car.start(); // Output : Car starts...
        bike.start(); // Output : Bike starts...
        electricVehicle.start(); // Output : Electric vehicle starts...
        tesla.start(); // Output : Tesla starts...

        //Text block for JSON string
        String jsonString = """
                {
                	"cricketer": {
                		"name": "MS Dhoni",
                		"centuries": 16,
                		"matches": 350,
                		"teams": [
                			"India",
                			"Chennai Super Kings",
                			"Rising Pune Supergiant"
                		],
                		"retired": true,
                		"message": "He said \\"The process is more important than the result\\"",
                	}
                }
                """;

        //Text block for SQL query
        String sqlQueryString = """
                SELECT * FROM EMPLOYEES E 
                WHERE E.ID = %d and E.name = %s ;
                """.formatted(10, "Sachin Tendulkar");

        //Text block with indentation handling
        String indentationTextSample = """
                What are Text Blocks in Java 15/17?
                    => Text blocks are multi-line string literals using triple quotes \"""
                    => They eliminate escape sequences for new lines/quotes and preserve formatting
                    => Incidental leading white space gets trimmed automatically
                    
                Line 1
                Line 2
                    Line 3 (extra indent)
                Line 4    
                """;

        /*
        NOTE : Quick Rules to Remember

        To include """ inside the text block → write \ """ (backslash + triple quotes)
        The backslash escapes the first quote → the whole """ is treated as literal text
        You can escape any number of consecutive triple quotes the same way
        No need to escape single or double quotes unless they are part of a larger escape sequence

         */

        System.out.println("Text block for JSON string, jsonString : \n"+jsonString);
        System.out.println("Text block for SQL query, sqlQueryString : \n"+sqlQueryString);
        System.out.println("Text block with indentation handling, indentationTextSample : \n"+indentationTextSample);

        //Pattern matching instanceof with String, Integer, record Language.
        Object obj1 = 10; //Integer
        Object obj2 = 18L; //Long
        Object obj3 = 3.14; //Double
        Object obj4 = "Funkynshot"; //String
        Object obj5 = new Language("Java","Enterprise-Level applications"); //record
        Object obj6 = new Employee(10L, "Sachin", 888888); //class
        Object obj7 = Cricketer.unCappedCricketer("Sooryavanshi"); //record
        Object obj8 = new Cricketer("Sachin Tendulkar", 100);
        Object obj9 = new Employee(5L, "Jing", 80000);

        List<Object> objectList = Arrays.asList(
                obj1, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9
        );

        //NOTE : Primitives (int, long, double) not supported in pattern matching

        //1. Call pattern matching instanceof using if-else statement
        //describeObject(obj1);
        //describeObject(obj2);
        //describeObject(obj3);
        //describeObject(obj4);
        //describeObject(obj5);
        //describeObject(obj6);
        objectList.forEach(obj->describeObject(obj));

        //2. Call pattern matching instanceof using switch case
        /*
        describeObjectUsingSwitch(obj1);
        describeObjectUsingSwitch(obj2);
        describeObjectUsingSwitch(obj3);
        describeObjectUsingSwitch(obj4);
        describeObjectUsingSwitch(obj5);
        describeObjectUsingSwitch(obj6);
        */
        objectList.forEach(MainClassForExercises0001:: describeObjectUsingSwitch);


        //3. Call pattern matching instanceof using concise switch case
        System.out.println("Pattern matching using concise switch case");
        objectList.forEach(obj->System.out.println(describeObjectUsingSwitchConcise(obj)));

        //4. Call guarded pattern in switch
        System.out.println("Guarded pattern in switch");
        objectList.forEach(obj->System.out.println(describeObjectWithGuardedPatternInSwitch(obj)));

        //5. Sealed interface with multiple implementations and Exhaustive Pattern Matching
        PaymentStatus success = PaymentStatus.fromCode(200); //Using static method
        PaymentStatus failure = PaymentStatus.fromCode(400); //Using factory method
        PaymentStatus pending = PaymentStatus.fromCode(500); //Using factory method

        List<PaymentStatus> paymentStatusList = Arrays.asList(
                success, failure, pending
        );

        System.out.println("Exhaustive Pattern Matching with switch - sealed interface");
        paymentStatusList.forEach(obj->System.out.println(describeObjectWithExhaustivePattern(obj)));

        //Record with custom equals/hashCode (override if needed).
        Cricketer c1 = new Cricketer("Sachin", 100);
        Cricketer c2 = new Cricketer("SacHIN", 150);
        Cricketer c3 = new Cricketer("Sachin Tendulkar", 100);

        System.out.println("Record with custom equals, hashCode methods, case in-sensitive equals");
        System.out.println("c1.equals(c2) ? : "+c1.equals(c2)); //Output : true due to case in-sensitive equals method in record
        System.out.println("c1.equals(c3) ? : "+c1.equals(c3)); //Output : false

        //Trigger enhanced NPE and read message
        //Case 1: Classic NPE (Pre-Java 14 - vague message)
        String nullString = null;
        //System.out.println("NPE, nullString.length() : "+nullString.length()); //NullPointerException (no helpful detail in Pre-Java 14)

        //Case 2: Enhanced NPE (Java 14+) – detailed message
        try{
            System.out.println("NPE, tyring to find null string length : "+nullString.length());
        }catch(NullPointerException e){
            System.out.println("NPE, exception : "+e.getMessage());
            //Output : NPE, exception : Cannot invoke "String.length()" because "nullString" is null
        }

        //Case 3 : Null array element
        String[] nullStringArray = {"Sachin", null, "Virat"};
        try{
            System.out.println("NPE, nullStringArray[1].length() : "+nullStringArray[1].length());
        }catch(NullPointerException e){
            System.out.println("NPE, exception : "+e.getMessage());
            //Output : NPE, exception : Cannot invoke "String.length()" because "nullStringArray[1]" is null
        }

        //Case 4 : More complex chain (shows exact location)
        Person person = new Person(null, new Address(null, "Chennai"));
        try{
            System.out.println("NPE, person.address().street().length() : "+person.address().street().length());
        }catch(NullPointerException e){
            System.out.println("NPE, exception : "+e.getMessage());
            //NPE, exception : Cannot invoke "String.length()" because the return value of "java_8_To_17_Exercises.Address.street()" is null
        }

        //NOTE : So, the enhance NPE Message clearly shows which variable was null and which method failed

    }

    //1. Pattern matching instanceof using if-else statement
    public static void describeObject(Object obj){
        System.out.println("Pattern matching using if-else block");
        if(obj instanceof Integer i){
            System.out.println("Integer : "+i);
        }else if(obj instanceof Long l){
            System.out.println("Long : "+l);
        }else if(obj instanceof Double d){
            System.out.println("Double : "+d);
        }else if(obj instanceof String s){
            System.out.println("String : "+s+", length : "+s.length());
        }else if(obj instanceof Language lang){ //record Language
            System.out.println("Language : "+lang);
            System.out.print("name : "+lang.name()+", primary Use : "+lang.primaryUse()+"\n"); //record deconstruction
        }else if(obj == null){
            System.out.println("null value");
        }else { //If nothing matched, then default case
            System.out.println("Unknown type : "+obj.getClass().getSimpleName());
        }
    }

    //2. Pattern matching instanceof using switch case
    public static void describeObjectUsingSwitch(Object obj){
        System.out.println("Pattern matching using switch case");
        switch (obj){
            case Integer i -> System.out.println("Integer : "+i);
            case Long l -> System.out.println("Long : "+l);
            case Double d -> System.out.println("Double : "+d);
            case String s -> System.out.println("String : "+s);
            case Language lang -> {
                System.out.println("Language : "+lang);
                System.out.print("name : "+lang.name()+", primary Use : "+lang.primaryUse()+"\n"); //record deconstruction
            }
            case null -> System.out.println("null value");
            default -> System.out.println("Unknown type : "+obj.getClass().getSimpleName());
        };
    }

    //3. Pattern matching instanceof using concise switch case, Here return type is String
    public static String describeObjectUsingSwitchConcise(Object obj){
        return switch (obj){
            case Integer i -> "Integer : "+i;
            case Long l -> "Long : "+l;
            case Double d -> "Double : "+d;
            case String s -> "String : "+s;
            case Language lang -> {
                String recordDeconstruction = "name : "+lang.name()+", primary use : "+lang.primaryUse();
                yield "Language : "+lang+"\n"+recordDeconstruction; //yield keyword is used to return the value in switch
            }
            case null -> "null value";
            default -> "Unknown type : "+obj.getClass().getSimpleName();
        };
    }

    //4. Guarded pattern in switch
    public static String describeObjectWithGuardedPatternInSwitch(Object obj){

        return switch (obj){

            //Guarded pattern with when
            case Integer i when i>0 && i%2==0 -> "Integer : even number "+i;
            case Integer i when i>0 && i%2!=0 -> "Integer : odd number "+i;

            //Without guarded pattern
            case Integer i -> "Integer : "+i;

            //record deconstruction + Guarded pattern with when
            case Cricketer c when c.centuries() == 100 -> {
                String recordDeconstruction = "name : "+c.name()+", centuries : "+c.centuries();
                yield "Cricketer : Highest century scorer "+recordDeconstruction;
            }

            //Without guarded pattern
            case Cricketer c -> "Cricketer : "+c;

            //Class deconstruction + Guarded pattern with when
            case Employee emp when emp.getEmployeeSalary() < 100000 -> {
                String classDeconstruction = "name : "+emp.getEmployeeName()+", salary : "+emp.getEmployeeSalary();
                yield "Employee : lowest salary employee "+classDeconstruction;
            }

            //Without guarded pattern
            case Employee emp -> "Employee : "+emp;
            case null -> "null value";

            default -> "Unknown type : "+obj.getClass().getSimpleName();
        };
    }

    //5. Exhaustive Pattern Matching with switch - sealed interface
    public static String describeObjectWithExhaustivePattern(PaymentStatus paymentStatus){
        return switch (paymentStatus){
            case PaymentSuccess success -> "Payment success : "+success.getStatusMessage();
            case PaymentFailure failure -> "Payment failed : "+failure.getStatusMessage();
            case PaymentPending pending -> "Payment pending : "+pending.getStatusMessage();
            //default -> "Default" // No default needed — compiler knows all cases covered!,
            //This is exhaustive pattern compiler check
        };
    }



}


