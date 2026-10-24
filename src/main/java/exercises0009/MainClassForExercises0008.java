package exercises0009;

import java.util.*;
import java.util.stream.Collectors;

public class MainClassForExercises0008 {

    public static void main(String[] args){
        //System.out.println("Test MainClassForExercises0008");

        //input : aaadddffffhhhhdddddaaagggg
        //output : a3d3f3h4d4a3g4

        String inputString = "aaadddffffhhhhdddddaaagggg";

        Map<Character, Integer> freqMap = inputString.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        c -> c,
                        LinkedHashMap::new,
                        Collectors.counting()
                )).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry :: getKey,
                        entry -> entry.getValue().intValue()
                ));

        StringBuilder outputString = new StringBuilder();
        freqMap.forEach((c, frequency) -> {
            outputString.append(c).append(frequency);

        });
        System.out.print("inputString : "+inputString);
        System.out.println("\n outputString : "+outputString);

        /*

        input : this is my life
        output : siht si my efil

        Explanation : you need to reverse the word only if it has vowels(a, e, i, o, u)
        */

        String sentence = "this is my life";

        List<String> stringList  = Arrays.stream(sentence.split(" "))
                .filter(s ->
                    !s.isEmpty()
                )
                .map(s -> {
                    if(s.contains("a") || s.contains("e") || s.contains("i") || s.contains("o") || s.contains("u")){
                        //char[] tempArray = s.toCharArray();
                        //return s; //return reversed String, dont know this at this moment
                        return new StringBuilder(s).reverse().toString();
                    }else{
                        return s;
                    }
                })
                .toList();

        System.out.println(stringList);









    }

}
