package oldCode.oneTimePad;

import java.util.Random;

public class One {
    private int[] key;
    private int size = 0;
    public boolean isKeySet = false;

    private void setKey(int l) {
        Random r = new Random();
        key = new int[l];
        for(int i = 0; i < l; i++) {
            key[i] = r.nextInt(26)%26;
        }
        size = l;
        System.out.println("the key arr ");
        for(int i : key) {
            System.out.print((char)('A' +i));
        }
        System.out.println();
    }


    private static String setUpText(String p) {
        p = p.toUpperCase();
        StringBuilder text = new StringBuilder();
        for(int i = 0; i < p.length(); i++) {
            if(Character.isAlphabetic(p.charAt(i))) {
                text.append(p.charAt(i));
            }
        }
        return text.toString();
    }

    public String encrypt(String p) {
        p = setUpText(p);
        if(!isKeySet){
            setKey(p.length());
            isKeySet = true;
        }

        StringBuilder ans = new StringBuilder();
        for(int i = 0; i < p.length(); i++) {
            int x = p.charAt(i) - 'A';
            x += key[i];
            x %= 26;
            x += 26;
            x %= 26;
            ans.append((char) ('A' + x));
        }
        return ans.toString();

    }

    public String decrypt(String c) {
        c = setUpText(c);
        if(!isKeySet){
            setKey(c.length());
            isKeySet = true;
        }

        StringBuilder ans = new StringBuilder();
        for(int i = 0; i < c.length(); i++) {
            int x = c.charAt(i) - 'A';
            x -= key[i];
            x %= 26;
            x += 26;
            x %= 26;
            ans.append((char) ('A' + x));
        }
        return ans.toString();

    }

    public int getSize() {
        return size;
    }
}
