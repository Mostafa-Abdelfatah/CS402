package ceaser;

import java.util.Scanner;

public class Ceaser {
    static Scanner in = new Scanner(System.in);
    public static String encrypt(String s, int k) {
        String ans = "";
//        if (k >= 0 && k <= 25) {
//            System.out.println("please enter valid number");
//            return encrypt(s, in.nextInt());
//        }
        for(int i = 0; i < s.length(); i++) {
            if(Character.isAlphabetic(s.charAt(i))) {
                if(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
                    int c = (s.charAt(i) - 'A' + k) % 26;
                    ans += (char)('A' + c);
                }
                else {
                    int c = (s.charAt(i) - 'a' + k) % 26;
                    ans += (char)('a' + c);
                }
            }
            else {
                ans += s.charAt(i);
            }
        }
        return ans;
    }

    public static String decrypt(String s, int k) {
        String ans = "";
//        if (k >= 0 && k <= 25) {
//            System.out.println("please enter valid number");
//            return encrypt(s, in.nextInt());
//        }
        for(int i = 0; i < s.length(); i++) {
            if(Character.isAlphabetic(s.charAt(i))) {
                if(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
                    int c = (s.charAt(i) - 'A' - k + 26) % 26;
                    ans += (char)('A' + c);
                }
                else {
                    int c = (s.charAt(i) - 'a' - k + 26) % 26;
                    ans += (char)('a' + c);
                }
            }
            else {
                ans += s.charAt(i);
            }
        }
        return ans;
    }

    public static String attack(String s) {
        String v = "";
        for(int i = 0; i < 26; i++) {
            v += "attack at key = " + i + " ";
            v += decrypt(s, i);
            v += '\n';
        }
        return v;
    }

    public static void main(String[] args) {
        System.out.println(encrypt("hager-z", 1));
        System.out.println(decrypt("ibhfs-a", 1));
        System.out.println("------------------------------------------");
        //String[] arr = attack("Mn Rd Sfrj nx RTXyfkf?");
        System.out.println(attack("Hello My name is mostafa234#"));

    }
}
