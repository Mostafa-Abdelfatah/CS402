package AES;

import java.io.FileWriter;
import java.io.IOException;

public class Key {
    private int[][] keys = new int[4][44];
    private static final String[] RC = {"01","02","04","08","10","20","40","80","1B","36"};
//    private static final String[][] SBOX = {
//            {"63","7C","77","7B","F2","6B","6F","C5","30","01","67","2B","FE","D7","AB","76"},
//            {"CA","82","C9","7D","FA","59","47","F0","AD","D4","A2","AF","9C","A4","72","C0"},
//            {"B7","FD","93","26","36","3F","F7","CC","34","A5","E5","F1","71","D8","31","15"},
//            {"04","C7","23","C3","18","96","05","9A","07","12","80","E2","EB","27","B2","75"},
//            {"09","83","2C","1A","1B","6E","5A","A0","52","3B","D6","B3","29","E3","2F","84"},
//            {"53","D1","00","ED","20","FC","B1","5B","6A","CB","BE","39","4A","4C","58","CF"},
//            {"D0","EF","AA","FB","43","4D","33","85","45","F9","02","7F","50","3C","9F","A8"},
//            {"51","A3","40","8F","92","9D","38","F5","BC","B6","DA","21","10","FF","F3","D2"},
//            {"CD","0C","13","EC","5F","97","44","17","C4","A7","7E","3D","64","5D","19","73"},
//            {"60","81","4F","DC","22","2A","90","88","46","EE","B8","14","DE","5E","0B","DB"},
//            {"E0","32","3A","0A","49","06","24","5C","C2","D3","AC","62","91","95","E4","79"},
//            {"E7","C8","37","6D","8D","D5","4E","A9","6C","56","F4","EA","65","7A","AE","08"},
//            {"BA","78","25","2E","1C","A6","B4","C6","E8","DD","74","1F","4B","BD","8B","8A"},
//            {"70","3E","B5","66","48","03","F6","0E","61","35","57","B9","86","C1","1D","9E"},
//            {"E1","F8","98","11","69","D9","8E","94","9B","1E","87","E9","CE","55","28","DF"},
//            {"8C","A1","89","0D","BF","E6","42","68","41","99","2D","0F","B0","54","BB","16"}
//    };

    private final static int[][] sbox = {
            //0     1    2      3     4    5     6     7      8    9     A      B    C     D     E     F
            {0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76},
            {0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0},
            {0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15},
            {0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75},
            {0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84},
            {0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf},
            {0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8},
            {0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2},
            {0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73},
            {0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb},
            {0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79},
            {0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08},
            {0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a},
            {0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e},
            {0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf},
            {0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16}};


    public Key(String s) {

        StringBuilder tempHexa = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            tempHexa.append(String.format("%02X", (int) s.charAt(i)));
        }

        // 16 bytes = 32 hex ,
        while (tempHexa.length() < 32) {
            tempHexa.insert(0, '0');
        }
        tempHexa = new StringBuilder(tempHexa.substring(0, 32));

        // w0, w1, w2,w3
        int index = 0;
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                String byteHex = tempHexa.substring(index, index + 2);
                keys[row][col] = Integer.parseInt(byteHex, 16);
                index += 2;
            }
        }

        setKeys();
        printKeys();
    }

    private void setKeys() {
        for (int col = 4; col < 44; col++) {
            int[] temp = new int[4];

            // w(col - 1)
            for (int row = 0; row < 4; row++) {
                temp[row] = keys[row][col - 1];
            }


            if (col % 4 == 0) {
                // Rotate
                int first = temp[0];
                temp[0] = temp[1];
                temp[1] = temp[2];
                temp[2] = temp[3];
                temp[3] = first;

                // SBOX
                for (int i = 0; i < 4; i++) {
                    int value = temp[i];
                    int r = value >> 4; // right shit ->
                    int c = value & 0xF; // 1 & 1 = 1, 1 & 0 = 1
                    temp[i] = sbox[r][c];
                }

                // xor RC
                temp[0] ^= Integer.parseInt(RC[(col / 4) - 1], 16);
            }

            // XOR with word 4 positions back
            for (int row = 0; row < 4; row++) {
                keys[row][col] = keys[row][col - 4] ^ temp[row];
            }
        }
    }

    public int[][] getKeys() {
        return keys;
    }

    public void printKeys() {
        StringBuilder ans = new StringBuilder("");
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 44; col++) {
                System.out.print(String.format("%02X ", keys[row][col]));
                ans.append(String.format("%02X ", keys[row][col]));
            }
            System.out.println();
            ans.append('\n');
        }
        writeFile(ans.toString());
//        System.out.println();
//        for (int row = 0; row < 4; row++) {
//            for (int col = 0; col < 44; col++) {
//                System.out.print( keys[row][col] + " ");
//            }
//            System.out.println();
//        }
    }
    public static String hexToEnglish(String hex) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            String hexPair = hex.substring(i, i + 2);
            int decimalValue = Integer.parseInt(hexPair, 16);
            char c = (char) (decimalValue);
            result.append(c);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Key k = new Key("\t"); // samllest char
        int c = 0x53;
        System.out.println(c >> 4); // 5 row
        System.out.println(c & 0xf);
        k.printKeys();
//        System.out.println(hexToEnglish("0f1571c947d9e8590cb7add6af7f6798"));
    }


    private void writeFile(String content) {
        try (FileWriter writer = new FileWriter("C:\\Users\\Mostafa\\IdeaProjects\\CS402\\src\\AES\\key.txt")) {
            writer.write(content);
        } catch (IOException ex) {

        }
    }
}
