package exercises0002;

import java.util.*;
import java.util.stream.Collectors;

public class MainClassForExercises0002 {

    //For practice purpose : These are created for ready to use. In actual interview, you need to create - START
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
    //For practice purpose : These are created for ready to use. In actual interview, you need to create - END

    public static void main(String[] args){

        /*
        01. Group and Aggregate Data: Given a list of Employee objects (with fields: id, name, department, salary),
        write a stream pipeline to find the highest-paid employee in each department.

            => Highest paid employee
            => Highest paid employee using Collectors.reducing
            => Second highest paid employee

        */

        List<Employee> employeeList = Arrays.asList(
                new Employee(7, "Dhoni", "WK Plus Batting", 777777.0),
                new Employee(10, "Sachin", "Cricket", 888888.0),
                new Employee(18, "Virat Kohli", "Batting", 666666.0),
                new Employee(7, "Dhawan", "Batting", 444444.0),
                new Employee(7, "Yuvraj", "All Rounder", 777777.0),
                null,
                new Employee(7, "KL Rahul", "WK Plus Batting", 555555.0),
                new Employee(7, "Jadeja", "All Rounder", 444444.0)
        );

        //Highest paid employee department wise
        Map<String, Employee> highestPaidEmployeeDepartmentWiseMap = employeeList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.maxBy(Comparator.comparingDouble(Employee::salary))
                )).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry :: getKey,
                        entry -> entry.getValue().orElse(null)
                ));

        System.out.println("=".repeat(100));
        System.out.println("Highest paid employee department wise");
        highestPaidEmployeeDepartmentWiseMap.forEach((dept, emp) -> {
            System.out.println(dept+" - "+emp);
        });

        //Highest paid employee using Collectors.reducing
        Map<String, Employee> highestPaidEmployeeDepartmentWiseMap1 = employeeList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.reducing((e1, e2) -> e1.salary() > e2.salary() ? e1 : e2)
                )).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry :: getKey,
                        entry -> entry.getValue().orElse(null)
                ));

        System.out.println("=".repeat(100));
        System.out.println("Highest paid employee department wise using Collectors.reducing");
        highestPaidEmployeeDepartmentWiseMap1.forEach((dept, emp) -> {
            System.out.println(dept+" - "+emp);
        });

        //Second highest paid employee
        Map<String, Employee> secondHighestPaidEmployeeDepartmentWiseMap = employeeList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.collectingAndThen(
                                Collectors.toList(),

                                list -> {
                                    if(list.size() <2) return null;

                                    list.sort((e1, e2) -> Double.compare(e2.salary(), e1.salary()));
                                    return list.get(1);
                                }
                        )
                ));

        System.out.println("=".repeat(100));
        System.out.println("Second highest paid employee department wise");
        secondHighestPaidEmployeeDepartmentWiseMap.forEach((dept, emp) -> {
            System.out.println(dept+" - "+emp);
        });

        /*

        02. Frequency Count: Given a string, find the frequency of each character,
        and filter out the top 3 most frequent elements using Streams.

            => Using Set
            => Using Map (Show with frequency)

        */

        String str = "aaabbbbbbcccdddd";

        //Using Set
        Set<Character> top3MostFrequentElementSet = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        c -> c,
                        Collectors.counting()
                )).entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .map(entry -> entry.getKey())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        System.out.println("=".repeat(100));
        System.out.println("Top 3 most frequent elements using Streams, using Set");
        top3MostFrequentElementSet.forEach(element -> System.out.println(element));

        //Using Map (Show with frequency)
        Map<Character, Integer> top3MostFrequentElementMap = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        c -> c,
                        Collectors.counting()
                )).entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().intValue(),
                        (existing, duplicate) -> existing,
                        LinkedHashMap::new
                ));

        System.out.println("=".repeat(100));
        System.out.println("Top 3 most frequent elements using Streams, using Map");
        top3MostFrequentElementMap.forEach((ch, freq)->{
            System.out.println(ch+" repeated "+freq+" times");
        });

        /*

        03. Frequency Count: Given a list of words, find the frequency of each word,
        and filter out the top 3 most frequent elements using Streams.

        */

        List<String> wordList = Arrays.asList(
                "banana", "banana", "apple", "orange", "banana",
                "banana", "orange", "banana", "apple", "orange"
        );

        Map<String, Integer> top3MostFrequentWordMap = wordList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        s -> s,
                        Collectors.counting()
                )).entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().intValue(),
                        (existing, duplicate) -> existing,
                        LinkedHashMap::new
                ));

        System.out.println("=".repeat(100));
        System.out.println("Top 3 most frequent words");
        top3MostFrequentWordMap.forEach((s, freq)->{
            System.out.println(s+" repeated "+freq+" times");
        });

        /*
        04. Flattening Nested Collections: Given a list of Order objects,
        where each order contains a list of Item objects, extract a distinct,
        sorted list of all item names across all orders using flatMap.

        05. Partitioning Data: Given a list of transactions,
        partition them into two groups (e.g., transactions above ₹50,000 and below) and
        calculate the average transaction amount for each group simultaneously.

        06. Custom String Joining: Given a list of strings, filter out empty strings, convert them to uppercase,
        and join them with a comma separator enclosed in square brackets like [A, B, C].

        07. Data Aggregation: Given a collection of complex Employee,
        use Streams to sort them dynamically by department name first, and then by salary descending.

        08. Find the number which is repeated the same number of times.
        Given List : [12,13,2,2,3,5,4,4,4,4], Can you guess the output?
        Output {2,4}

        09. Return the consecutive two numbers 2 peer. Output will be List<List>.
        Given list [1,2,4,5,7,8,9], Output {{1,2}, {4,5}, {7,8}, {8,9}}

        10. Print non-repeating first character. Consider any String. your wish.

        11. Binary Grouping: Take a number in binary form and count the groups of isolated 0s individually before a 1 appears.
        (e.g., Input: 0011000 → Output: groups of size 2 and 3).

        Note : Time Complexity Explanation:
                O(n) — String split and stream operations process each character linearly.
                Space Complexity Explanation:
                O(n) — For storing the resulting list of group sizes.

        12. Merge Overlapping Intervals: Given a collection of intervals (e.g., [1,3], [2,6], [8,10]),
        merge all overlapping intervals to output [1,6], [8,10]. (Tests sorting and custom collection logic).
*/
        //For practice purpose : These are created for ready to use. In actual interview, you need to create - START

        List<Order> orderList = List.of(
                new Order(List.of(new Item("Keyboard"), new Item("Mouse"))),
                new Order(List.of(new Item("Laptop"), new Item("Bag"))),
                new Order(List.of(new Item("Keyboard"), new Item("Mobile"))),
                new Order(List.of(new Item("Tab"), new Item("Mouse"))),
                new Order(List.of(new Item("Pen")))
        );

        List<Transaction> transactionList = List.of(
                new Transaction("10", 80000.0),
                new Transaction("18", 60000.0),
                new Transaction("7", 70000.0),
                new Transaction("3", 40000.0),
                new Transaction("1", 45000.0),
                new Transaction("15", 20000.0)
        );

        List<String> stringList = Arrays.asList(
                "Sachin", "Shewag", "Gambhir", "Virat", "", "Yuvraj", null, "Dhoni", "   ", "Raina"
        );

        List<Integer> integerList = List.of(12,13,2,2,3,5,4,4,4,4);

        List<Integer> integerList1 = List.of(1,2,4,5,7,8,9);

        String s1 = "aabbcddxxyyza";

        String binaryNumber = "0011000";

        int[][] intervals = {{1,3}, {2,6}, {8,10}, {15,18}, {9,11}};

        //For practice purpose : These are created for ready to use. In actual interview, you need to create - END

        //Start Coding from here






    }

}
