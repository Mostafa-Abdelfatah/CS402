package railFence;

public class Rail {

    // to set up the text remove non letter
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

    public static String encrypt(String text, int depth) {
        text = setUpText(text);
        StringBuilder[] rails = new StringBuilder[depth];
        for (int i = 0; i < depth; i++) {
            rails[i] = new StringBuilder();
        }
        boolean down = false;
        int row = 0;

        for (char ch : text.toCharArray()) {
            rails[row].append(ch);
            if (row == 0 || row == depth - 1) {
                down = !down;
            }
            row += down ? 1 : -1;
        }

        StringBuilder encryptedText = new StringBuilder();
        System.out.println("the rails in encrypt");
        for (StringBuilder rail : rails) {
            System.out.println(rail);
            encryptedText.append(rail);
        }
        return encryptedText.toString();
    }

    public static String decrypt(String text, int depth) {
        text = setUpText(text);
        StringBuilder[] rails = new StringBuilder[depth];
        for (int i = 0; i < depth; i++) {
            rails[i] = new StringBuilder();
        }
        boolean down = false;
        int row = 0;

        // Fill the rails with placeholders
        for (int i = 0; i < text.length(); i++) {
            rails[row].append('*');
            if (row == 0 || row == depth - 1) {
                down = !down;
            }
            row += down ? 1 : -1;
        }

        // Replace placeholders with characters from the encrypted text
        int index = 0;
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < rails[i].length(); j++) {
                if (rails[i].charAt(j) == '*' && index < text.length()) {
                    rails[i].setCharAt(j, text.charAt(index++));
                }
            }
        }
        System.out.println("the rails in decrypt");
        for (StringBuilder rail : rails) {
            System.out.println(rail);
        }
        // Reconstruct the original text from the rails
        StringBuilder decryptedText = new StringBuilder();
        down = false;
        row = 0;
        for (int i = 0; i < text.length(); i++) {
            //put the letter at index 0 always
            decryptedText.append(rails[row].charAt(0));
            //here delete the letter
            rails[row].deleteCharAt(0);
            if (row == 0 || row == depth - 1) {
                down = !down;
            }
            row += down ? 1 : -1;
        }
        return decryptedText.toString();
    }


}
