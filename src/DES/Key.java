package DES;

import java.util.Arrays;

/**
 * the length of the key must 16
 * input in hexa
 * pc1 -> make it 56 bit
 * pc2 -> make it 48 bit
 * C0 , D0 not considered the key we use
 * */


public class Key {
    private static final int[] PC1 = {57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};
    private static final int[] PC2 = {14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47,
            55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
    private static final int[] LS = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    private String key, subKey = "";
    private String[] realKeys = new String[16];

    public Key(String s) {
        this.key = String.format("%64s", new java.math.BigInteger(s, 16).toString(2)).replace(' ', '0');
        System.out.println(key);
        // make 56 bit subKey
        for(int i : PC1) {
            subKey += key.charAt(i - 1);
        }
        System.out.println(subKey);

        String C0 = "", D0 = "";
        C0 = subKey.substring(0, 28);
        D0 = subKey.substring(28);
        String[] Cs = new String[17];
        String[] Ds = new String[17];

        Cs[0] = C0;
        Ds[0] = D0;;


        for(int i = 1; i < 17; i++) {
            String[] temp = shiftLeft(Cs[i-1], Ds[i - 1], LS[i - 1]);
            Cs[i] = temp[0];
            Ds[i] = temp[1];
        }
       for(int i = 0; i < 16; i++) {
           String temp = Cs[i+1] + Ds[i+1];
           realKeys[i] = "";
           for(int j : PC2){
               realKeys[i] += temp.charAt(j - 1);
           }
       }

    }

    public String[] getRealKeys() {
        return realKeys;
    }

    private String[] shiftLeft(String C, String D, int l) {
        String newC = C.substring(l);
        newC += C.substring(0,l);

        String newD = D.substring(l);
        newD += D.substring(0, l);

        String[] ans = {newC, newD};
        return ans;
    }


    public static void main(String[] args) {
//        Key k = new Key("0123456789ABCDEF");
//        String[] keys = k.getRealKeys();
//        for (int i = 0; i < 16; i++) {
//            System.out.println(keys[i].length());
//            System.out.println(keys[i]);
//        }
//        String[] t = new String[6];
//        t[0] = "000010110000001001100111100110110100100110100101";
//        t[1] = "011010011010011001011001001001010110101000100110";
//        t[2] = "010001011101010010001010101101000010100011010010";
//        t[3] = "011100101000100111010010101001011000001001010111";
//        t[4] = "001111001110100000000011000101111010011011000010";
//        t[5] = "001000110010010100011110001111001000010101000101";
//
//        for(int i = 0; i < 6; i++) {
//            System.out.println(keys[i].equals(t[i]));
//        }


    }


}
