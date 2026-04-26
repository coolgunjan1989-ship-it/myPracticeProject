package Programs;

public class CountNumberOfLettersInString {

    public static void countLetters(String s){
        int count =0;
        for(int i=0; i<s.length(); i++){
            count++;
        }
        System.out.println("The number of letters in String " + s + " is " + count);

    }

    public static void main(String[] args){

        countLetters("abc");

    }
}
