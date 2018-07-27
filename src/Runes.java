import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runes {
    private static final int DECIMAL_DIGITS_TOTAL = 9;

    private static final Pattern REGEXP = Pattern.compile("(-?[\\d?]+)([+\\-*]{1})(-?[\\d?]+)(=-?[\\d?]+)");
    public static Runes rune = new Runes();

    public static int solveExpression(final String expression) {
        Term t1 = obtainTerm(1, expression);
        Character operation = obtainOperation(expression);
        Term t2 = obtainTerm(2, expression);
        Term result = obtainResult(expression);
        Matcher m = REGEXP.matcher(expression);
        List<Integer> digitsToCheck = getDigitsToCheck(expression);
        System.out.println(t1);
        System.out.println(operation);
        System.out.println(t2);
        System.out.println(result);
        switch (operation) {
            case '+':
                for (Integer integer : digitsToCheck) {
                    if (constructNumber(t1, integer) + constructNumber(t2, integer) == constructNumber(result, integer)) {
                        return integer;
                    }
                }
                break;
            case '-':
                for (Integer integer : digitsToCheck) {
                    if (constructNumber(t1, integer) - constructNumber(t2, integer) == constructNumber(result, integer)) {
                        return integer;
                    }
                }
                break;
            case '*':
                for (Integer integer : digitsToCheck) {
                    if (constructNumber(t1, integer) * constructNumber(t2, integer) == constructNumber(result, integer)) {
                        return integer;
                    }
                }
                break;
            default:
                break;
        }
        return -1;
    }

    private static Term obtainResult(String expression) {
        Matcher matcher = REGEXP.matcher(expression);
        if (matcher.find()) {
            return rune.new Term(matcher.group(4).substring(1));
        }
        return null;
    }

    private static Character obtainOperation(String expression) {
        Matcher matcher = REGEXP.matcher(expression);
        if (matcher.find()) {
            return matcher.group(2).charAt(0);
        }
        return null;
    }

    private static Term obtainTerm(int i, String expression) {
        Matcher matcher = REGEXP.matcher(expression);
        if (matcher.find()) {
            if (i == 1) {
                return rune.new Term(matcher.group(i));
            }
            if (i == 2) {
                return rune.new Term(matcher.group(3));
            }

        }
        return null;

    }
    public static void main(String[] s){
        String expr = "-??*?=-99";
        System.out.println(solveExpression(expr));
    }

    private static int constructNumber(Term term, int digit) {
        int result = 0;

        for (Integer integer : term.digits.keySet()) {
            if (term.digits.get(integer) != null) {
                result += (int) Math.pow(10, integer) * term.digits.get(integer);
            } else {
                result += (int) Math.pow(10, integer) * digit;
            }
        }
        if(term.isNegative){
            result*=-1;
        }
        return result;
    }

    private static List<Integer> getDigitsToCheck(String expression) {
        List<Integer> digitsToCheck = new ArrayList<>();
        for (int i = 0; i <= DECIMAL_DIGITS_TOTAL; i++) {
            if (!expression.contains(Integer.toString(i))) {
                digitsToCheck.add(i);
            }
        }
        for (int i = 0; i < expression.length() - 1; i++) {
            if (expression.charAt(i) == '?' && expression.charAt(i + 1) == '?') {
                digitsToCheck.remove(Integer.valueOf(0));
            }
        }
        return digitsToCheck;
    }

    private static int getNumber(String numberString) {
        int result = 0;
        for (int i = 0; i < numberString.length(); i++) {
            if (Character.getNumericValue(numberString.charAt(i)) > 0) {
                result += Character.getNumericValue(numberString.charAt(i)) * Math.pow(10, numberString.length() - i - 1);
            } else {
                return -1;
            }
        }
        return result;
    }

    private class Term {
        private Integer numericValue = 0;
        private int length;
        private Map<Integer, Integer> digits;
        private List<Integer> questionableDigits = new ArrayList<>();
        private boolean isNegative;

        Term(String number) {
            this.length = number.length();
            isNegative = number.charAt(0) == '-';
            digits = obtainDigits(number);
            estimateNumericValue();
        }

        void estimateNumericValue() {
            digits.forEach((k, v) -> {
                if (v == null) {
                    questionableDigits.add(k);
                } else {
                    numericValue += (int) Math.pow(10, k) * v;
                }
            });
            if (isNegative) {
                numericValue *= -1;
            }
        }

        @Override
        public String toString() {
            return "Term{" +
                    "numericValue=" + numericValue +
                    ", length=" + length +
                    ", digits=" + digits +
                    ", questionableDigits=" + questionableDigits +
                    ", isNegative=" + isNegative +
                    '}';
        }

        Map<Integer, Integer> obtainDigits(String numberString) {
            Map<Integer, Integer> result = new HashMap<>();
            for (int i = 0; i < numberString.length(); i++) {
                if (Character.getNumericValue(numberString.charAt(i)) >= 0) {
                    result.put(numberString.length() - i - 1, Character.getNumericValue(numberString.charAt(i)));
                } else {
                    if(numberString.charAt(i)=='-'){
                        continue;
                    }
                    result.put(numberString.length() - i - 1, null);
                }
            }
            return result;
        }

    }
}