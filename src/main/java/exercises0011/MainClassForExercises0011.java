package exercises0011;

public class MainClassForExercises0011 {

    public static void main(String[] args){

        System.out.println("TEST MainClassForExercises0011");

        /*
        Design a Rate Limiter.
Implement a class
class RateLimiter {

    boolean allowRequest(String userId)
}
Requirements -
Each user can make 5 requests per minute.
If exceeded
Return false.
After one minute
Requests should automatically expire.

Constraints

O(1) average lookup

Multiple users

Thread-safe

Efficient memory cleanup

         */ /*


        @Configuration
        public class RedisService{

            SetIfAbsent (60)

        }

        class RateLimiter {

            @Autowired
            RedisService redisService;

            boolean allowRequest(String userId){

                String uniqueKey = "User :"+ userId;

                if(!lockAcquired){

                    //process
                }else{
                    return exception("resoruced process ");
                }


            }
        }


*/


    }

}
