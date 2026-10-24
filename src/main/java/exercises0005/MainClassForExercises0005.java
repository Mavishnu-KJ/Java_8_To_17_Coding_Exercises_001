package exercises0005;

public class MainClassForExercises0005 {
    public static void main(String[] args){

        /*

        Given a string s consisting of characters including the following brackets:
Round brackets: ( and )
Square brackets: [ and ]
Curly braces: { and }

Write a java program using stack that determines whether the input string s is balanced or not.
Time Complexity: O(n)
Space Complexity: O(n)

Input:  s = "{[}"
Output: false

Input:  s = "{}"
Output: true

Input:  s = "(((((())))))"
Output: true

        */


        String input1 = "{[}";
        boolean isBalanced = isBalanced(input1);
        System.out.println("input"+input1+", output"+isBalanced);

        String input2 = "{}";
        boolean isBalanced1 = isBalanced(input1);
        System.out.println("input"+input2+", output"+isBalanced1);




    }

    /*
    Left, right pointers. While loop
    */

    public static boolean isBalanced(String s){


        int stringLength = s.length();

        int left=0;
        int right = stringLength -1;

        //Edge cases
        if(s.isEmpty()){
            return true;
        }

        if(s.length() %2 !=0){
            return false;
        }



        /*
        kafka, code efficiency
        coding - you need to improve the code efficiency
        spring boot- fair understanding, deepdive on utilizing different annotations on different scenarios
        microservices - clear understanding, brush up deep dive scenario based,
         */

        /*Rough
        {[}

        n = 3

        right=2 =>
        left =0, right 2
        left == right true;
        left = 1, right 1
        right = 1



         */

        //Iterate s
       while(left<right){

            if(s.charAt(left) != s.charAt(right)){
                return false;
            }

            left++;
            right--;

        }

       return true;

    }


}
