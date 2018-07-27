import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Decoder {
    private static final String LOWER_CASE_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static List<String> ways = new ArrayList<>();
    /*
    Given the mapping a = 1, b = 2, ... z = 26, and an encoded message, count the number of ways it can be decoded.
    For example, the message '111' would give 3, since it could be decoded as 'aaa', 'ka', and 'ak'.
    You can assume that the messages are decodable. For example, '001' is not allowed.
    1234  ->    1 2 3 4     12 3 4      1 23 4
    1023  ->    10 2 3      10 23
    2014  ->    20 1 4      20 14
    Characters that limit their left neighbour: 0, since it eats the character to the left of it, since a valid number can't start with a zero.
    Characters that limit their right neighbour: 3-9, since a number cant be above 26

     */
    private static Map<String, String> alphabet = new HashMap<>();

    private static void fillMap() {
        for (int i = 0; i < LOWER_CASE_ALPHABET.length(); i++) {
            alphabet.put(String.valueOf(i + 1), String.valueOf(LOWER_CASE_ALPHABET.charAt(i)));
        }
    }

    public static void main(String[] args) {
        fillMap();
    }

    public static int numOfWays(String input) {
        Math.random();
        int result = 0;
/*
        0 ->              X                                 1-2                                 X
        1 -> can be a standalone character                  1-2                                ANY
        2 -> can be a standalone character                  1-2                                0-6
        3 -> can be a standalone character                  1-2                                 X
        4 -> can be a standalone character                  1-2                                 X
        5 -> can be a standalone character                  1-2                                 X
        6 -> can be a standalone character                  1-2                                 X
        7 -> can be a standalone character                   1                                  X
        8 -> can be a standalone character                   1                                  X
        9 -> can be a standalone character                   1                                  X
        101011 -> 10 10 1 1     10 10 11
        123031 -> 1 2 30 3 1    12 30 3 1
        123013 -> 1 2 30 1 3    1 2 30 13   12 30 1 3   12 30 13
*/
        return result;
    }
}

