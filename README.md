# Java 8–17 Interview Preparation : 100+ Solved Questions

Comprehensive practice of modern Java features for senior/lead roles (Spring Boot, microservices, design interviews).

main class file : [MainClassForExercises0001](src/main/java/exercises0001/MainClassForExercises0001.java)

001. Use lambda with forEach to print a list of strings with "Hello " prefix.
002. Sort a list of integers in descending order using lambda Comparator.
003. Filter even numbers from a list using Predicate and lambda.
004. Use Consumer to print each element of a list with uppercase.
005. Create a Supplier that generates random numbers (1–100).
006. Use Function to convert a list of strings to uppercase.
007. Chain Function: Convert string to uppercase, then to length.
008. Use Predicate to filter names starting with "A" from a list.
009. Implement a custom functional interface "Calculator" with add and subtract methods using lambda.
010. Use lambda with Runnable to print "Hello from thread".
011. Sort a list of employees by salary using Comparator lambda.
012. Group a list of strings by length using Collectors.groupingBy with lambda.
013. Use Optional with lambda: If value present, print it; else print default.
014. Create a Predicate that checks if a number is prime (using lambda).
015. Use lambda with Stream: Filter names starting with "A", map to uppercase, collect to list.
016. Implement a method reference for static method (e.g., String::toUpperCase).
017. Use constructor reference to create a new ArrayList.
018. Handle checked exception in lambda (wrap in try-catch or custom functional interface).
019. Use BiFunction to add two numbers with lambda.
020. Combine Predicate and Consumer: Filter and print only matching elements. 
021. Create Stream from list and print all elements using forEach.
022. Filter even numbers from list and print using forEach.
023. Map list of strings to uppercase and collect to List.
024. Sort list of integers ascending using sorted.
025. Sort descending using sorted(Comparator.reverseOrder()).
026. Count elements in list using count.
027. Find first element using findFirst (or Optional).
028. Check if any number is even using anyMatch.
029. Check if all numbers are positive using allMatch.
030. Check if no number is negative using noneMatch.
031. Reduce to sum of list using reduce.
032. Reduce to product of list using reduce.
033. FlatMap: Flatten List<List<Integer>> to List<Integer>.
034. Remove duplicates using distinct.
035. Limit to first 5 elements.
036. Skip first 3 elements.
037. Group strings by length using Collectors.groupingBy.
038. Partition numbers into even/odd using Collectors.partitioningBy.
039. Join strings with comma using Collectors.joining(", ").
040. Find max/min in list using max/min + Comparator.
041. Reduce to sum of integers in list.
042. Reduce to product of integers.
043. Reduce to concatenate strings with delimiter.
044. FlatMap: Flatten List<List<String>> to List<String>.
045. FlatMap: Flatten List<Optional<String>> to List<String>.
046. Generate 10 random numbers using Stream.generate + limit.
047. Generate even numbers sequence using Stream.iterate + limit.
048. Create LocalDate for today and print.
049. Add 10 days to today's date using plusDays.
050. Calculate age from birth date using Period.between.
051. Parse string to LocalDateTime using DateTimeFormatter.
052. Format LocalDateTime to custom pattern (e.g., "dd-MM-yyyy HH:mm").
053. Get current time in different time zones (ZoneId.of("Asia/Kolkata"), "America/New_York").
054. Convert old Date to LocalDateTime.
055. Duration between two LocalDateTime.
056. Find max/min date in list of LocalDate.
057. Group dates by month using groupingBy.
058. Infinite stream of Fibonacci numbers (limit 10).
059. Stream of primes (generate + filter + limit).
060. Debug Stream with peek (print intermediate values).
061. Use var for List, Map, Stream.
062. var with diamond operator.
063. var limitations (no initializer).
064. String.isBlank() on empty/whitespace.
065. String.lines() on multi-line → Stream<String>.
066. strip(), stripLeading(), stripTrailing().
067. String.repeat(5) on "Hello".
068. Synchronous HTTP GET (print body from https://jsonplaceholder.typicode.com/posts/1).
069. Asynchronous HTTP GET with thenAccept.
070. Files.readString from test.txt (create file first).
071. Files.writeString to file.
072. list.toArray(String[]::new).
073. Predicate.not for non-empty check.
074. Combine var + HTTP Client.
075. var with nested generics.
076. Mixed: Multi-line string → lines() → filter non-blank → collect toList, etc.
077. Mixed: Multi-line string → lines() → filter non-blank → collect toList, etc.
078. Mixed: Multi-line string → lines() → filter non-blank → collect toList, etc.
079. Mixed: Multi-line string → lines() → filter non-blank → collect toList, etc.
080. Mixed: Multi-line string → lines() → filter non-blank → collect toList, etc.
081. Create record Cricketer(String name, int centuries).
082. Add compact constructor for validation (centuries >= 0).
083. Add instance methods to record (e.g., isHighestCenturiesScorer(), isLowestCenturiesScorer(int thresold)), Demonstrate static field, static method.
084. Sealed class Vehicle with permitted Car, Bike (final).
085. Non-sealed class in hierarchy.
086. Pattern matching instanceof with String, Integer, Employee.
087. Deconstruct record in instanceof.
088. Text block for JSON string.
089. Text block for SQL query.
090. Text block with indentation handling.
091. Pattern matching in switch (enable preview in IntelliJ: Project Settings → Compiler → Java compiler → --enable-preview).
092. Guarded pattern in switch.
093. Trigger enhanced NPE and read message.
094. Record with custom equals/hashCode (override if needed).
095. Sealed interface with multiple implementations.
096. Mixed: Record + pattern matching, text block formatting, sealed hierarchy with switch, etc
097. Mixed: Record + pattern matching, text block formatting, sealed hierarchy with switch, etc
098. Mixed: Record + pattern matching, text block formatting, sealed hierarchy with switch, etc
099. Mixed: Record + pattern matching, text block formatting, sealed hierarchy with switch, etc
100. Mixed: Record + pattern matching, text block formatting, sealed hierarchy with switch, etc

## Coverage
- Lambdas & Functional Interfaces
- Streams API (filter, map, flatMap, reduce, collect)
- Optional, Date/Time API, Collectors
- Method References, Default/Static interface methods
- Records, Sealed classes, Text blocks, Pattern matching (Java 17)
- Many common interview edge cases

## Why this repo?
- Daily practice to master Java 8–17 for interviews
- Clean, commented code with explanations
- Verified with output examples

## Related Projects
- Full Spring Boot application: [Employee Management System](https://github.com/Mavishnu-KJ/employeeManagementSystem)
- Payment Wallet System : [payment-wallet-system](https://github.com/Mavishnu-KJ/payment-wallet-system)
