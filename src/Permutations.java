import java.util.*;
import java.util.stream.Collectors;

public class Permutations{
    static List<String> totalList = new ArrayList<>();
    static List<String> current = new ArrayList<>();
    public static void main(String[] args){
        String testArg = "asdihb";
        thing(testArg);
        List<String> filteredList = totalList.stream().filter(s->s.length()>testArg.length()-1).collect(Collectors.toList());
        System.out.println(filteredList.size());
        System.out.println(filteredList);
//        System.out.println(totalList);
    }
    private static void thing(String s){
        if(s.length()==0){
            return;
        }
        String lastLetter = s.substring(s.length()-1);
        String nextArg = s.substring(0, s.length()-1);
        if(current.size() == 0){
            current.add(lastLetter);
            lastLetter = s.substring(s.length()-2, s.length()-1);
            nextArg = s.substring(0, s.length()-2);
        }
        for (String elementInCurr : current) {
            for (int i = 0; i < elementInCurr.length() + 1; i++) {
                StringBuilder permutatedString = new StringBuilder(elementInCurr);
                permutatedString.insert(i, lastLetter);
                totalList.add(permutatedString.toString());
            }
        }
        current.clear();
        current.addAll(totalList);
        thing(nextArg);
    }

}