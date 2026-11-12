package exercises0004;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainClassForExercises0004 {

    public static void main(String[] args) {

        //Create custom exception

        //username, name, age, pwd
        /*
    AgeException - cusom exceptionclass

        {
            AgeException(String message){
                return
        }
        }

    @RestController

    validateUser(@RequestBody userRequestDTO)

    @Service


            throw new AgeException("age shouldbe ")





    }

    @RestControllerAdvice

    @ExceptionHandler(AgeException.class){

        ErrorResponse

                return Response
    }



         */

        //create 2 list of integer,

        List<Integer> integerList1 = Arrays.asList(3, 7, 9, 7, 18, 9);

        //List<Integer> integerList2 = Arrays.asList(2, 5, 8, 2, 10, 7); //The issue is that Arrays.asList() creates a fixed‑size list, and fixed‑size lists cannot be structurally modified.
        List<Integer> integerList2 = new ArrayList<>(Arrays.asList(2, 5, 8, 2, 10, 7));

        //MergeList
        //outputList

        integerList2.addAll(integerList1);
        System.out.println("integerList2: "+integerList2);

        List<Integer> olist = integerList2.stream()
                .collect(Collectors.groupingBy(
                        n -> n,
                        Collectors.counting()
                )).entrySet().stream()
                .filter(entry -> entry.getValue() <2)
                .map(Map.Entry::getKey)
                .toList();

        System.out.println("olist : "+olist);



        List<Integer> outputList = integerList2.stream()
                .flatMap(i -> Stream.of(i))
                .toList();

        System.out.println("outputList : "+outputList);







    }
}
