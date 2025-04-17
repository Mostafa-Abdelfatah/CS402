package oldCode.vigenere;

class Pair {
    char e1;
    int pos;

    public Pair(char e1, int pos){
        this.e1 = e1;
        this.pos = pos;
    }
}

public class Vigi {
    // Encrypt plaintext using the Vigenere cipher with the given key
    public static String encrypt(String plaintext, String key) {
        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase();
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0, j = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if (Character.isAlphabetic(c)) {
                int shift = key.charAt(j % key.length()) - 'A';
                c = (char) ('A' + (c - 'A' + shift) % 26);
                j++;
            }
            ciphertext.append(c);
        }

        return ciphertext.toString();
    }

    // Decrypt ciphertext using the Vigenere cipher with the given key
    public static String decrypt(String ciphertext, String key) {
        ciphertext = ciphertext.toUpperCase();
        key = key.toUpperCase();
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0, j = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (Character.isAlphabetic(c)) {
                int shift = key.charAt(j % key.length()) - 'A';
                c = (char) ('A' + (c - 'A' - shift + 26) % 26);
                j++;
            }
            plaintext.append(c);
        }

        return plaintext.toString();

    }
}
