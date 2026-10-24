package exercises0004;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainClassForExercises0004 {
    public static void main(String[] args){

        /*
         Take an array containing mixed integers, Objects, and nested arrays
         Example : {{1}, 2, {3, 4}, 5, {6, {7, 8}}}
         and flatten it into a single-level stream.
        */

        Object[] inputArrayWithMixedObjectsArraysIntegers = {
            new int[] {1},
            2,
            new int[] {3,4},
            5,
            new Object[]{6, new int[] {7,8}}
        };

        System.out.println("inputArrayWithMixedObjectsArraysIntegers : "+Arrays.toString(inputArrayWithMixedObjectsArraysIntegers));

        int[] flattenedArray = flatten(inputArrayWithMixedObjectsArraysIntegers);
        System.out.println("flattenedArray : "+Arrays.toString(flattenedArray));


        /*
        Group and Aggregate Data: Given a list of Employee objects (with fields: id, name, department, salary),
        write a stream pipeline to find the highest-paid employee in each department.
        */

        List<Employee> employeeList = List.of(
                new Employee(7, "Dhoni", "IPL", 777777.00),
                new Employee(10, "Sachin", "Cricket", 888888.00),
                new Employee(18, "Virat", "IPL", 555555.00),
                new Employee(12, "Dhawan", "Cricket", 444444.00)
        );

        Map<String, Employee> highestPaidEmployeeMap = employeeList.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        HashMap::new, //Not mandatory
                        Collectors.maxBy(Comparator.comparingDouble(Employee::salary))
                        )
                ).entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(), //Map.Entry::getKey
                        entry -> entry.getValue().orElse(null)
                        )
                );

        //System.out.println("Highest paid employee by department wise : "+highestPaidEmployeeMap);
        System.out.println("Highest paid employee by department wise : ");
        highestPaidEmployeeMap.forEach((dept, emp) -> {
            System.out.println(dept+", highest paid employee : "+emp.name()+", Salary: "+emp.salary());
        });

        Map<String, Employee> highestPaidEmployee1 = employeeList.stream()
                .collect(Collectors.groupingBy(
                        Employee :: department,
                        Collectors.reducing((e1,e2) -> e1.salary() > e2.salary() ? e1 : e2)
                        )
                ).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry :: getKey,
                        entry -> entry.getValue().orElse(null)
                ));

        System.out.println("Highest paid employee department wise, using Reducing");
        highestPaidEmployee1.forEach((dept, emp) -> {
            if(emp !=null){
                System.out.println(dept+", employee : "+emp.name()+", Rs. "+emp.salary());
            }
        });

        Map<String, Employee> secondHighestPaidEmployeeMap = employeeList.stream()
                .collect(Collectors.groupingBy(
                        Employee :: department,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    if (list.size() < 2) return null;

                                    list.sort((e1, e2) -> Double.compare(e2.salary(), e1.salary()));
                                    return list.get(1); //Second highest
                                }
                        )
                ));

        System.out.println("Second Highest Paid Employee department wise : ");
        secondHighestPaidEmployeeMap.forEach((dept, emp) -> {
            System.out.println(dept+", Employee : "+emp.name()+", Rs. "+emp.salary());
        });

        /*
        Frequency Count: Given a string,
        find the frequency of each character, and filter out the top 3 most frequent elements using Streams.
        */

        String str = "aaabbbbbbcccdddd";
        Map<Character, Long> characterFrequencyMap = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        c -> c,
                        Collectors.counting()
                )).entrySet().stream()
                .sorted((e1,e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry :: getKey,
                        Map.Entry :: getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap :: new
                ));

        characterFrequencyMap.forEach((c, freq)->{
            System.out.println(c +" repeated "+freq+" times");
        });

        /*
        Frequency Count: Given a list of words,
        find the frequency of each word, and filter out the top 3 most frequent elements using Streams.
        */

        List<String> listOfWords = Arrays.asList(
                "banana", "banana", "apple", "orange", "banana",
                "banana", "orange", "banana", "apple", "orange"
        );

        Map<String, Long> wordFrequencyMap = listOfWords.stream()
                .collect(Collectors.groupingBy(
                        s -> s,
                        Collectors.counting()
                )).entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry :: getKey,
                        Map.Entry :: getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap :: new
                ));

        wordFrequencyMap.forEach((word, freq) ->{
            System.out.println(word+" repeated "+freq+" times");
        });

        /*
        Flattening Nested Collections: Given a list of Order objects, where each order contains a list of Item objects,
        extract a distinct, sorted list of all item names across all orders using flatMap.
        */

        List<Order> orderList = Arrays.asList(
                new Order(List.of(new Item("Mouse"), new Item("Keyboard"))),
                new Order(List.of(new Item("Laptop"), new Item("Mobile"))),
                new Order(List.of(new Item("Keyboard"), new Item("Laptop"))),
                new Order(List.of(new Item("Pen"), new Item("Mouse")))
        );

        List<String> sortedDistinctItemsList = orderList.stream()
                .flatMap(order -> order.getItemList().stream())
                .map(item -> item.getItemName())
                .distinct()
                .sorted()
                .toList();

        System.out.println("sortedDistinctItemsList : "+sortedDistinctItemsList);

        /*
        Find the number which is repeated the same number of times
        Given List : [12,13,2,2,3,5,4,4,4,4], Can you guess the output?
        Output {2,4}
        */

        List<Integer> inputList = Arrays.asList(12,13,2,2,3,5,4,4,4,4);

        Set<Integer> outputList = inputList.stream()
                .collect(Collectors.groupingBy(
                        num -> num,
                        Collectors.counting()
                )).entrySet().stream()
                .filter(entry -> entry.getKey() == entry.getValue().intValue()) //Operator '==' cannot be applied to 'java.lang.Integer', 'java.lang.Long'
                .map(Map.Entry :: getKey)
                .collect(Collectors.toSet());

        System.out.println("inputList : "+inputList);
        System.out.println("outputList : "+outputList);


        /*
        Return the consecutive two numbers 2 peer. Output will be List<List>
        Given list [1,2,4,5,7,8,9], Output {{1,2}, {4,5}, {7,8}, {8,9}}
        */

        List<Integer> inputList1 = Arrays.asList(1,2,4,5,7,8,9);
        List<List<Integer>> outputList1 = IntStream.range(0, inputList1.size()-1)
                .filter(i -> inputList1.get(i+1) == inputList1.get(i) +1)
                .mapToObj(i -> Arrays.asList(inputList.get(i), inputList1.get(i+1)))
                .toList();

        System.out.println("outputList1 : "+outputList1);

        /*
        Print non-repeating first character. Consider any String. your wish.
        */

        String s1 = "aabbcddxxyyza";

        s1.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        c -> c,
                        Collectors.counting()
                )).entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .limit(1)
                .map(entry -> entry.getKey())
                .forEach(c -> System.out.println("First Non repeating character : "+c));











    }

    public static int[] flatten(Object[] nested){

        return Arrays.stream(nested)
                .flatMapToInt(item ->{
                    if(item instanceof int[] arr){
                        return Arrays.stream(arr);
                    }else if(item instanceof Integer integer){
                        return IntStream.of(integer);
                    }else if(item instanceof Object[] objectArray){
                        // Convert int[] returned by recursion into IntStream
                        return Arrays.stream(flatten(objectArray));
                    }
                    return IntStream.empty();
                })
                .toArray();

    }

}
