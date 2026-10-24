package exercises0006;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainClassForExercises0006 {

    public static record Employee(
            int id,
            String name,
            String department,
            Double salary
    ){

    }

    public static record Item(
            String itemName
    ){

    }

    public static record Order(
            List<Item> itemList
    ){

    }

    public static record Transaction(
            String transactionId,
            Double amount
    ){

    }

    public static void main(String[] args){

        /*
        Group and Aggregate Data: Given a list of Employee objects (with fields: id, name, department, salary),
        write a stream pipeline to find the highest-paid employee in each department.
        */

        List<Employee> employeeList = Arrays.asList(
                new Employee(7, "Dhoni", "WK Plus Batting", 777777.0),
                new Employee(10, "Sachin", "Cricket", 888888.0),
                new Employee(18, "Virat Kohli", "Batting", 666666.0),
                new Employee(7, "Dhawan", "Batting", 444444.0),
                new Employee(7, "Yuvraj", "All Rounder", 777777.0),
                new Employee(7, "KL Rahul", "WK Plus Batting", 555555.0),
                new Employee(7, "Jadeja", "All Rounder", 444444.0)
        );

        //Highest paid employee
        Map<String, Employee> employeeMap = employeeList.stream()
                .collect(Collectors.groupingBy(
                                Employee :: department, // grouped by department
                                Collectors.maxBy(Comparator.comparingDouble(Employee :: salary)) // Employee sorted by salary descending, one max employee only
                        )// HashMap : Key : department, Value -> Optional<Employee>
                ).entrySet().stream()
                //converting Map into stream for further processing
                .collect(Collectors.toMap( //Iterating the map
                        Map.Entry :: getKey, // Department as key
                        entry -> entry.getValue().orElse(null) // Unwrap Optional<Employee>
                ));

        System.out.println("Highest paid employee department wise : ");
        employeeMap.forEach((dept, emp) -> {
            System.out.println(dept+"  - Highest Paid Employee : "+emp.name+" (Salary : Rs. "+emp.salary+" )");
        });

        //Highest paid employee using Collectors.reducing
        Map<String, Employee> employeeMap1 = employeeList.stream()
                .collect(Collectors.groupingBy(
                        Employee :: department,
                        Collectors.reducing((e2, e1) -> e1.salary() > e2.salary() ? e1 : e2)
                )).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry :: getKey,
                        entry -> entry.getValue().orElse(null)
                ));

        System.out.println("=".repeat(20));
        System.out.println("Highest paid employee department wise using Collectors.reducing : ");
        employeeMap1.forEach((dept, emp) -> {
            System.out.println(dept+"  - Highest Paid Employee : "+emp.name+" (Salary : Rs. "+emp.salary+" )");
        });

        //Second highest paid employee
        Map<String, Employee> employeeMap2 = employeeList.stream()
                .collect(Collectors.groupingBy(
                        Employee :: department,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    if(list.size() < 2) return null;

                                    list.sort((e1, e2) -> Double.compare(e2.salary(), e1.salary()));
                                    return list.get(1);
                                }
                        )
                )).entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry :: getKey,
                        Map.Entry :: getValue
                ));

        System.out.println("=".repeat(20));
        System.out.println("Second highest paid employee department wise using Collectors.reducing : ");
        employeeMap2.forEach((dept, emp) -> {
            if(emp != null) {
                System.out.println(dept + " - Second highest Paid Employee : " + emp.name + " (Salary : Rs. " + emp.salary + " )");
            }
        });


        /*
        Frequency Count: Given a string,
        find the frequency of each character, and filter out the top 3 most frequent elements using Streams.
        */

        String str = "aaabbbbbbcccdddd";
        Set<Character> mostFrequentCharacterList = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        c -> c,
                        Collectors.counting()
                )).entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .map(entry -> entry.getKey())
                .collect(Collectors.toSet());

        System.out.println("Top 3 most frequent elements in the String "+str);
        mostFrequentCharacterList.forEach(element -> System.out.println(element));

        String str1 = "aaabbbbbbcccdddd";
        Map<Character, Integer> frequencyMap = str1.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        c -> c,
                        Collectors.counting()
                )).entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry :: getKey,
                        entry -> entry.getValue().intValue(),
                        (existing, duplicate) -> existing,
                        LinkedHashMap :: new
                ));

        System.out.println("Top 3 most frequent elements in the String "+str);
        frequencyMap.forEach((character, freq) -> {
            System.out.println(character+" repeated "+freq+" times");
        });

        /*
        Frequency Count: Given a list of words,
        find the frequency of each word, and filter out the top 3 most frequent elements using Streams.
        */

        List<String> wordList = Arrays.asList(
                "banana", "banana", "apple", "orange", "banana",
                "banana", "orange", "banana", "apple", "orange"
        );

        Map<String, Long> mostFrequentWordMap = wordList.stream()
                .collect(Collectors.groupingBy(
                        s -> s,
                        Collectors.counting()
                )).entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry :: getKey,
                        Map.Entry :: getValue,
                        (existing, duplicate) -> existing,
                        LinkedHashMap :: new
                ));

        System.out.println("=".repeat(20));
        System.out.println("Top 3 most frequent elements in the list of words : ");
        mostFrequentWordMap.forEach((s, freq) -> {
            System.out.println(s+" repeated "+freq+" times");
        });

        /*
        Flattening Nested Collections: Given a list of Order objects, where each order contains a list of Item objects,
        extract a distinct, sorted list of all item names across all orders using flatMap.
        */

        List<Order> orderList = List.of(
                new Order(List.of(new Item("Keyboard"), new Item("Mouse"))),
                new Order(List.of(new Item("Laptop"), new Item("Bag"))),
                new Order(List.of(new Item("Keyboard"), new Item("Mobile"))),
                new Order(List.of(new Item("Tab"), new Item("Mouse"))),
                new Order(List.of(new Item("Pen")))
        );

        List<String> itemNameList = orderList.stream()
                .flatMap(order -> order.itemList().stream())
                .map(item -> item.itemName())
                .distinct()
                .sorted()
                .toList();

        System.out.println("=".repeat(20));
        System.out.println("orderList before flattening : "+orderList);
        System.out.println("\nFlattened the orderList and printing the distinct and sorted item names from the list : ");
        itemNameList.forEach(System.out::println);

        /*
        Partitioning Data: Given a list of transactions,
        partition them into two groups (e.g., transactions above ₹50,000 and below) and
        calculate the average transaction amount for each group simultaneously.
        */

        List<Transaction> transactionList = List.of(
                new Transaction("10", 80000.0),
                new Transaction("18", 60000.0),
                new Transaction("7", 70000.0),
                new Transaction("3", 40000.0),
                new Transaction("1", 45000.0),
                new Transaction("15", 20000.0)
        );



        Map<Boolean, Double> transactionMapPartitionedByAmount = transactionList.stream()
                .collect(Collectors.partitioningBy(
                        transaction -> transaction.amount > 50000.0,
                        Collectors.averagingDouble(transaction -> transaction.amount())
                ));

        System.out.println("=".repeat(20));
        System.out.println("given transactionList : "+transactionList);
        transactionMapPartitionedByAmount.forEach((aboveCutOff, average) -> {

            if(aboveCutOff){
                System.out.println("In the given transactionList, for Group (above 50000), average is "+average);
            }else{
                System.out.println("In the given transactionList, for Group (below 50000), average is "+average);
            }

        });

        /*
        Custom String Joining: Given a list of strings, filter out empty strings,
        convert them to uppercase, and join them with a comma separator enclosed in square brackets [A, B, C].
        */

        List<String> stringList = Arrays.asList(
                "Sachin", "Shewag", "Gambhir", "Virat", "", "Yuvraj", null, "Dhoni", "   ", "Raina"
        );

        String joinedString = stringList.stream()
                .filter(s -> Objects.nonNull(s) && !s.isBlank())
                .map(String :: toUpperCase)
                .collect(Collectors.joining(",", "[", "]"));

        System.out.println("=".repeat(20));
        System.out.println("Joined string : "+joinedString);

        /*
        Data Aggregation: Given a collection of complex Employee,
        use Streams to sort them dynamically by department name first, and then by salary descending.
        */

        Map<String, List<Employee>> employeeListByDepartmentAscSalaryDesc = employeeList.stream()
                .collect(Collectors.groupingBy(
                        Employee :: department,
                        TreeMap :: new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    if(list == null || list.isEmpty()) return null;

                                    list.sort((e1, e2) -> Double.compare(e2.salary(), e1.salary()));
                                    return list;
                                }
                        )
                ));

        System.out.println("=".repeat(20));
        System.out.println("employeeList before sorting : "+employeeList);
        System.out.println("employeeList after sorting as per requirement : ");
        employeeListByDepartmentAscSalaryDesc.forEach((dept, emp) -> {

            System.out.println("-".repeat(10));
            System.out.println(dept);
            System.out.println("-".repeat(10));
            emp.forEach(e -> System.out.println(e.name+"(Salary Rs. "+e.salary+" )"));
            System.out.println("-".repeat(10));


        });

        /*
        Find the number which is repeated the same number of times
        Given List : [12,13,2,2,3,5,4,4,4,4], Can you guess the output?
        Output {2,4}
        */

        List<Integer> integerList = List.of(12,13,2,2,3,5,4,4,4,4);
        List<Integer> outputList = integerList.stream()
                .collect(Collectors.groupingBy(
                        num -> num,
                        Collectors.counting()
                )).entrySet().stream()
                .filter(entry -> entry.getKey() == entry.getValue().longValue())
                .map(entry -> entry.getKey())
                .toList();
        System.out.println("=".repeat(20));
        System.out.println("integerList : "+integerList);
        System.out.println("outputList : "+outputList);

        /*
        Return the consecutive two numbers 2 peer. Output will be List<List>
        Given list [1,2,4,5,7,8,9], Output {{1,2}, {4,5}, {7,8}, {8,9}}
        */

        List<Integer> integerList1 = List.of(1,2,4,5,7,8,9);
        List<List<Integer>> outputList1 = IntStream.range(0, integerList1.size()-1)
                .filter(i -> integerList1.get(i+1).equals( integerList1.get(i) + 1))
                .mapToObj(i -> List.of(integerList1.get(i), integerList1.get(i+1)))
                .toList();

        System.out.println("=".repeat(20));
        System.out.println("integerList1 : "+integerList1);
        System.out.println("outputList1 : "+outputList1);

        /*
        Print non-repeating first character. Consider any String. your wish.
        */

        String s1 = "aabbcddxxyyza";
        Character nonRepeatingFirstCharacter = s1.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        c -> c,
                        LinkedHashMap::new,
                        Collectors.counting()
                )).entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        System.out.println("=".repeat(20));
        System.out.println("nonRepeatingFirstCharacter in the String "+s1+" is "+nonRepeatingFirstCharacter);

        /*
        Binary Grouping: Take a number in binary form and count the groups of isolated 0s individually before a 1 appears
        (e.g., Input: 0011000 → Output: groups of size 2 and 3).
        */

        /*
        Time Complexity Explanation:
        O(n) — String split and stream operations process each character linearly.
        Space Complexity Explanation:
        O(n) — For storing the resulting list of group sizes.
        */

        String binaryNumber = "0011000";
        List<Integer> zeroGroups = Arrays.stream(binaryNumber.split("1+"))  // Split on one or more 1s
                .filter(s -> !s.isEmpty())      // Remove empty strings
                .map(String :: length)          // Get length of each zero group
                .toList();



        System.out.println("=".repeat(20));
        System.out.println("binaryNumber : "+binaryNumber);
        System.out.println("zeroGroups : "+zeroGroups);

        /*
        Merge Overlapping Intervals: Given a collection of intervals (e.g., [1,3], [2,6], [8,10]),
        merge all overlapping intervals to output [1,6], [8,10]. (Tests sorting and custom collection logic).
        */

        int[][] intervals = {{1,3}, {2,6}, {8,10}, {15,18}, {9,11}};

        List<List<Integer>> merged = Arrays.stream(intervals)
                .sorted((a,b) -> Integer.compare(a[0], b[0]))
                .collect(
                        ArrayList::new,
                        (mergedList, currentInterval) -> {
                            //List<Integer> lastMerged = mergedList.get(mergedList.size()-1);
                            if(mergedList.isEmpty() ||
                                    mergedList.get(mergedList.size()-1).get(1) < currentInterval[0]){

                                mergedList.add(new ArrayList<>(Arrays.asList(currentInterval[0], currentInterval[1])));
                            }else{

                                List<Integer> lastMerged = mergedList.get(mergedList.size()-1);
                                lastMerged.set(1, Math.max(lastMerged.get(1), currentInterval[1]));
                            }
                        },
                        (list1, list2) -> list1.addAll(list2)
                );

        System.out.println("Non Overlapping merged list : ");
        merged.forEach(System.out::println);



    }

}
