package Programs;

import java.util.*;

public class CombinationsString {

    public static void combinations(String s) {
        int length = s.length();
        System.out.println("The length is " + length);
        Set<String> charset = new TreeSet<>();
        Set<String> combinations = new HashSet<>();

        for (int i = 0; i < length; i++) {
            charset.add(s.substring(i, i + 1));
        }

        for (String m : charset) {
            System.out.println("The character in the set is " + m);
        }

        Object[] charArray = charset.toArray();
        String string = "";
        Set<String> result = new TreeSet<>();
        for (int j = 0; j < length; j++) {
            for (int k = j; k < length; k++) {
                System.out.println("The value of j is " + j);
                System.out.println("The value of k is " + k);
                if (k == j) {
                    string = (String) charArray[k];
                    result.add(string);
                } else {
                    string = charArray[j] + (String) (charArray[k]);
                    result.add(string);
                    string = charArray[k] + (String) (charArray[j]);
                    result.add(string);
                    if (j + k < length) {
                        int n = j + k;
                        System.out.println("The value of j+k is " + n);
                        string = charArray[j] + (String) (charArray[n]);
                        result.add(string);
                        string = charArray[n] + (String) (charArray[j]);
                        result.add(string);
                    }

                }
            }
        }

        for (String resultString : result) {
            System.out.println("The String is " + resultString);
        }
    }

    public static void main(String[] args) {
        combinations("abcd");
    }

}
