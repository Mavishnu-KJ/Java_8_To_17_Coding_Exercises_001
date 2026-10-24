package exercises0013;

import java.util.*;
import java.util.stream.Collectors;

public class MainClassForExercises0013 {

    public record Employee(
            String name,
            Double salary
    ){

    }

    public record Review(
            int hotelId,
            String textReview
    ){

    }

    public static void main(String[] args){

        //System.out.println("test MainClassForExercises0013");
        //List of employees salary - get second highest salary
        List<Employee> employeeList = Arrays.asList(
                new Employee("Sachin", 88888.0),
                new Employee("Virat", 55555.0),
                new Employee("Dhoni", 77777.0),
                new Employee("Rahul", 55555.0),
                new Employee("Rohit", 77777.0)
        );

        Employee secondHighestEmployee = employeeList.stream()
                .sorted((e1, e2) -> Double.compare(e2.salary(), e1.salary()))
                .skip(1)
                .findFirst()
                .orElse(null);

        System.out.println("secondHighestEmployee: "+secondHighestEmployee);

        /*
        The Scenario: A hotel has a fixed number of identical rooms. Given an array of customer check-in and check-out days,
        write a program to find the minimum number of total rooms required to ensure no booking conflicts occur.
        Input Example: bookings = [[10, 12], [11, 15], [14, 18], [10, 14]]
        */

        int[][] bookings = {{10, 12}, {11, 15}, {14, 18},{10, 14}};

        List<int[]> events = new ArrayList<>();

        for(int[] booking : bookings){

            events.add(new int[]{booking[0], 1});
            events.add(new int[]{booking[1], -1});

        }

        events.sort((a,b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        int currentRooms = 0;
        int maxRooms = 0;

        for(int[] event : events){
            currentRooms = currentRooms + event[1];
            maxRooms = Math.max(maxRooms, currentRooms);
        }

        System.out.println("Minimum number of rooms required : "+maxRooms);

        //Time Complexity : O(n log n)
        //Space Complexity : O(n)

        /*
        The Scenario: Given a list of hotel reviews where each entry contains a hotelId and a text review,
        along with a set of positive keywords (e.g., "clean", "good", "view"),
        find the Top K hotels that received the highest count of positive keywords in their reviews.
        */

        List<Review> hotelReviewList = Arrays.asList(
                new Review(101, "Good hotel"),
                new Review(102, "Nice view"),
                new Review(101, "Bad Service"),
                new Review(103, "Excellent overall"),
                new Review(102, "Clean environment"),
                new Review(102, "Bad service but good location"),
                new Review(103, "Best for view")
        );

        int k = 2;

        Set<String> positiveKeyWords = Set.of("view", "excellent", "good", "clean");

        Map<Integer, Long> scoreMap = hotelReviewList.stream()
                .collect(Collectors.groupingBy(
                        Review :: hotelId,
                        Collectors.summingLong(review ->
                                Arrays.stream(review.textReview().toLowerCase().split("\\s"))
                                        .filter(s -> positiveKeyWords.contains(s))
                                        .count()
                                )
                ));

        List<Integer> topHotelList = scoreMap.entrySet().stream()
                .sorted((e1,e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(k)
                .map(Map.Entry :: getKey)
                .toList();

        System.out.println("scoreMap : "+scoreMap);
        System.out.println("topHotelList : "+topHotelList);

        //Time Complexity : O(n*m)
        //Space Complexity : O(n)


    }
}
