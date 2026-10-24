package exercises0007;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainClassForExercises0007 {

    public static void main(String[] args){

        //System.out.println("test");
        //LTIMindtree
        //Java, Spring Boot, Hibernate
        // arrayformat, Hibernate, Spring Boot, Java
        List<String> stringList = Arrays.asList("Java", "Spring Boot", "Hibernate");
        String[] outputStringArray = new String[stringList.size()];
        int k = stringList.size();

        for(int i = stringList.size() -1, j=0; i>=0 && j<k; i--, j++){

            if(j<k) {
                outputStringArray[j] = stringList.get(i);

            }

        }
        System.out.println("String List : "+stringList);
        System.out.println("Reverse order : "+Arrays.toString(outputStringArray));

        String[] outputStringArray1 = stringList.stream()
                .collect(
                        Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> {
                            Collections.reverse(list);   // Reverse the list
                            return list.toArray(new String[0]);
                        }
                ));

        System.out.println("Original List     : " + stringList);
        System.out.println("Reversed Array    : " + Arrays.toString(outputStringArray1));






    }

}
