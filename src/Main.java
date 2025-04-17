import oldCode.DES.*;


public class Main {

    public static String railFenceEncrypt(String text, int depth) {
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
        for (StringBuilder rail : rails) {
            encryptedText.append(rail);
        }
        return encryptedText.toString();
    }

    public static String railFenceDecrypt(String text, int depth) {
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



    public static void main(String[] args) {
//        String s = Rail.encrypt("meetmeaftertheparty", 4);
//        System.out.println(s);
//
//        System.out.println("------------------------------------");
//        String s1 = Rail.decrypt("mematrhpryetefeteat", 2);
//        System.out.println(s1);
//
//        System.out.println("------------------------------------");
//
//        //3
////mmthretefeteatearpy
////2
////mematrhpryetefeteat
//        //--------------------------------------------------------------------------
//        System.out.println("--------------------------------------------");
//        One o = new One();
//        System.out.println(o.encrypt("mr mustard with the candlestick in the hall"));
//        System.out.println(o.decrypt(o.encrypt("mr mustard with the candlestick in the hall")));
//
//        //mr mustard with the candlestick in the hall
        Key k = new Key("0123456789ABCDEF");
        DES d = new DES();
        System.out.println(d.encrypt("0123456789ABCDEF", k.getRealKeys()));
        System.out.println(d.decrypt("56CC09E7CFDC4CEF", k.getRealKeys()));
    }
}
//0123456789ABCDEF
//56CC09E7CFDC4CEF
