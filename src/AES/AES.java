package AES;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AES {
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
//    private static final String[][] SBoxInv = {
//            {"52","09","6A","D5","30","36","A5","38","BF","40","A3","9E","81","F3","D7","FB"},
//            {"7C","E3","39","82","9B","2F","FF","87","34","8E","43","44","C4","DE","E9","CB"},
//            {"54","7B","94","32","A6","C2","23","3D","EE","4C","95","0B","42","FA","C3","4E"},
//            {"08","2E","A1","66","28","D9","24","B2","76","5B","A2","49","6D","8B","D1","25"},
//            {"72","F8","F6","64","86","68","98","16","D4","A4","5C","CC","5D","65","B6","92"},
//            {"6C","70","48","50","FD","ED","B9","DA","5E","15","46","57","A7","8D","9D","84"},
//            {"90","D8","AB","00","8C","BC","D3","0A","F7","E4","58","05","B8","B3","45","06"},
//            {"D0","2C","1E","8F","CA","3F","0F","02","C1","3A","91","11","41","4F","67","DC"},
//            {"AF","BD","03","01","13","8A","6B","EA","97","F2","CF","CE","F0","B4","E6","73"},
//            {"96","AC","74","22","E7","AD","35","85","E2","F9","37","E8","1C","75","DF","6E"},
//            {"47","F1","1A","71","1D","29","C5","89","6F","B7","62","0E","AA","18","BE","1B"},
//            {"FC","56","3E","4B","C6","D2","79","20","9A","DB","C0","FE","78","CD","5A","F4"},
//            {"1F","DD","A8","33","88","07","C7","31","B1","12","10","59","27","80","EC","5F"},
//            {"60","51","7F","A9","19","B5","4A","0D","2D","E5","7A","9F","93","C9","9C","EF"},
//            {"A0","E0","3B","4D","AE","2A","F5","B0","C8","EB","BB","3C","83","53","99","61"},
//            {"17","2B","04","7E","BA","77","D6","26","E1","69","14","63","55","21","0C","7D"}
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

    private final     static int[][] inverseSbox = {
        {0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb},
        {0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb},
        {0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e},
        {0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25},
        {0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92},
        {0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84},
        {0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06},
        {0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b},
        {0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73},
        {0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e},
        {0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b},
        {0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4},
        {0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f},
        {0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef},
        {0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61},
        {0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d}};
    private static final int[][] MixCol = {
            {0x02, 0x03, 0x01, 0x01},
            {0x01, 0x02, 0x03, 0x01},
            {0x01, 0x01, 0x02, 0x03},
            {0x03, 0x01, 0x01, 0x02}
    };

    private static final int[][] MixColInv = {
            {0x0E, 0x0B, 0x0D, 0x09},
            {0x09, 0x0E, 0x0B, 0x0D},
            {0x0D, 0x09, 0x0E, 0x0B},
            {0x0B, 0x0D, 0x09, 0x0E}
    };


    // plaintext = 128 bit = 16 letter
    public String realEncrypt(String p, int[][] keys) {
        StringBuilder plain = new StringBuilder(p);
        StringBuilder ans = new StringBuilder("");
       while(plain.length() % 16 != 0) {
           plain.insert(0, 'X');
       }
       for(int i = 0; i < plain.length(); i += 16) {
           ans.append(
             encrypt(plain.substring(i, i + 16), keys) );
       }
       writeFileEncrypt(hexToEnglish(ans.toString()));
       return ans.toString();
    }

    private String encrypt(String p, int[][] keys) {
        StringBuilder ans = new StringBuilder("");

        // setup initial state
        int[][] state = new int [4][4];
        int index = 0;
        for(int col = 0; col < 4; col++) {
            for(int row = 0; row < 4; row++) {
                state[row][col] = (int) p.charAt(index);
                index++;
            }
        }

        int startCol = 0;
        // round 0
        // key0
        state = addRound(state, getKeyMatrix(keys,startCol));
        writeStateE(state, 0);
        startCol += 4;

        //rounds 1 -> 9
        for(int i = 1; i <= 9; i++) {
            state = subBytes(state);
            state = shiftRows(state);
            state = mixCol(state);
            state = addRound(state, getKeyMatrix(keys, startCol));
            startCol += 4;
            writeStateE(state, i);
        }

        // round 10
        state = subBytes(state);
        state = shiftRows(state);
        state = addRound(state, getKeyMatrix(keys, startCol));
        writeStateE(state, 10);

        for(int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                ans.append(
                        String.format("%02X", state[row][col])
                );
            }
        }
        return ans.toString();
    }

    public String realDecrypt(String hexa, int[][] keys) {
        StringBuilder ans = new StringBuilder("");
        // at least 32 hexa letters
        StringBuilder cipher = new StringBuilder(hexa);
        while(cipher.length() % 32 != 0) {
            cipher.insert(0, '0');
        }
        for(int i = 0; i < cipher.length(); i += 32) {
            ans.append(
              decrypt(cipher.substring(i, i + 32), keys)
            );
        }
        writeFileDecrypt((ans.toString()));
        return ans.toString();
    }

    public String decrypt(String p, int[][] keys) {
        StringBuilder ans = new StringBuilder("");
        int[][] state = new int [4][4];
        int index = 0;
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                String byteHex = p.substring(index, index + 2);
                state[row][col] = Integer.parseInt(byteHex, 16);
                index += 2;
            }
        }


        int startCol = 40;
        // round 0
        state = addRound(state, getKeyMatrix(keys, startCol));
        startCol -= 4;
        writeStateD(state, 0);
        for(int i = 1; i <= 9; i++) {
            state = inverseShiftRows(state);
            state = invSubBytes(state);
            state = addRound(state, getKeyMatrix(keys, startCol));
            startCol -= 4;
            state = invMixCol(state);
            writeStateD(state, i);

        }

        //round 10;
        state = inverseShiftRows(state);
        state = invSubBytes(state);
        state = addRound(state, getKeyMatrix(keys, startCol));
        writeStateD(state, 10);


//        for(int col = 0; col < 4; col++) {
//            for (int row = 0; row < 4; row++) {
//                ans.append(
//                        String.format("%02X", state[row][col])
//                );
//            }
//        }
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                ans.append((char) state[row][col]);
            }
        }


        return ans.toString();
    }

    private int[][] addRound(int[][] state, int[][] key) {
        int[][] ans = new int[4][4];
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                ans[i][j] = state[i][j] ^ key[i][j];
            }
        }
        return ans;
    }

    private int[][] getKeyMatrix(int[][] keys, int startCol){
        int[][] ans = new int[4][4];
        int j = 0;
        for(int col = startCol; col < startCol + 4; col++) {
            for(int i = 0; i < 4; i++) {
                ans[i][j] = keys[i][col];
            }
            j++;
        }
        return ans;
    }

    private int[][] subBytes(int[][] state) {
        int[][] ans = new int[4][4];
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                int val = state[i][j];
                int row = val >> 4;
                int col = val & 0xf;
                ans[i][j] = sbox[row][col];
            }
        }
        return ans;
    }

    private int[][] invSubBytes(int[][] state) {
        int[][] ans = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int val = state[i][j];
                int row = val >> 4;
                int col = val & 0xf;
                ans[i][j] = inverseSbox[row][col];
            }
        }
        return ans;
    }

    private int[][] shiftRows (int[][] state) {
        int[][] ans = new int[4][4];
        // circular shift left
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                ans[row][col] = state[row][(col + row) % 4];
            }
        }
        return ans;
    }

    private int[][] inverseShiftRows(int[][] state) {
        int[][] ans = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ans[i][(j + i) % 4] = state[i][j];
            }
        }
        return ans;
    }
    private int[][] mixCol(int[][] state) {
        int[][] ans = new int[4][4];
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                ans[i][j] = 0;
                for(int k = 0; k < 4; k++) {
                    ans[i][j] ^= multiplyInGF_2_P8(state[k][j], (byte) MixCol[i][k]);
                }
            }
        }
        return ans;
    }

    private int[][] invMixCol(int[][] state) {
        int[][] ans = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ans[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    ans[i][j] ^= multiplyInGF_2_P8(state[k][j], MixColInv[i][k]);
                }
            }
        }
        return ans;
    }

    private int multiplyInGF_2_P8(int f_x, int x) {
        int ans = 0;
        int[] arr = multiply(f_x);
        for(int i = 1; i <= 128; i *= 2) {
            if((x & i) != 0) {
                ans ^= arr[Integer.numberOfTrailingZeros(i)];
            }
        }
        return (ans & 0xff);
    }


    /*
0 -> 1
1 -> x
2 -> x^2
3 -> x^3
4 -> x^4
5 -> x^5
6 -> x^6
7 -> x^7
these are the values of  at x^i * f(x)
    */
    private int[] multiply(int f) {
        int[] ans = new int[8];
        ans[0] = f;
        for(int i = 1; i < 8; i++) {
            if((ans[i - 1] & 0x80) == 0) {
                ans[i] = ans[i - 1]  << 1;
            }
            else {

                ans[i] = ((byte) (ans[i - 1]  << 1)) ^ 0x1b;
            }
        }
        return ans;
    }

    private void writeStateE(int[][] arr, int round) {
        StringBuilder ans = new StringBuilder("");
        ans.append("round " + round + " :\n");
        for(int col = 0; col < 4; col++) {
            for(int row = 0; row < 4; row++) {
                ans.append(String.format("%02X", arr[row][col]));
            }
        }
        ans.append("\n");
        ans.append("\n");
        File file = new File("C:\\Users\\Mostafa\\IdeaProjects\\CS402\\src\\AES\\EncryptRound.txt");
        try (FileWriter writer = new FileWriter(file, true)) {
            if (file.length() > 0) {
                writer.write(System.lineSeparator());
            }
            writer.write(ans.toString());
        } catch (IOException ex) {
            ex.printStackTrace(); // Optional: to see what went wrong
        }

    }
    private void writeStateD(int[][] arr, int round) {
        StringBuilder ans = new StringBuilder("");
        ans.append("round " + round + " :\n");
        for(int col = 0; col < 4; col++) {
            for(int row = 0; row < 4; row++) {
                ans.append(String.format("%02X", arr[row][col]));
            }
        }
        ans.append("\n");
        ans.append("\n");
        File file = new File("C:\\Users\\Mostafa\\IdeaProjects\\CS402\\src\\AES\\DecryptRound.txt");
        try (FileWriter writer = new FileWriter(file, true)) {
            if (file.length() > 0) {
                writer.write(System.lineSeparator());
            }
            writer.write(ans.toString());
        } catch (IOException ex) {
            ex.printStackTrace(); // Optional: to see what went wrong
        }

    }


    public static void main(String[] args) {
        AES as = new AES();
        Key k = new Key("Thats my Kung Fu");
        k.printKeys();
        int x = 0xf5;
        System.out.println(Integer.toBinaryString(x));
        System.out.println(x >> 7);
        as.multiply(0x87);
        System.out.println(as.multiplyInGF_2_P8(0x87, 0x02));
        System.out.println(as.realEncrypt("Two One Nine Two Hello", k.getKeys()));
        System.out.println(as.realDecrypt("A19A5C6AE4D2EF7C5C688166F4CF77979926E46B263C410A1A1B7392C11E1D53", k.getKeys()));

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

    private void writeFileEncrypt(String content) {
        File file = new File("C:\\Users\\Mostafa\\IdeaProjects\\CS402\\src\\AES\\cipherEnglish.txt");
        try (FileWriter writer = new FileWriter(file, true)) {
            if (file.length() > 0) {
                writer.write(System.lineSeparator());
            }
            writer.write(content);
        } catch (IOException ex) {
            ex.printStackTrace(); // Optional: to see what went wrong
        }
    }
    private void writeFileDecrypt(String content) {
        File file = new File("C:\\Users\\Mostafa\\IdeaProjects\\CS402\\src\\AES\\decryptEnglish.txt");
        try (FileWriter writer = new FileWriter(file, true)) {
            if (file.length() > 0) {
                writer.write(System.lineSeparator());
            }
            writer.write(content);
        } catch (IOException ex) {
            ex.printStackTrace(); // Optional: to see what went wrong
        }
    }
}


//correctPlain:0123456789abcdeffedcba9876543210
//cipher: FF0B844A0853BF7C6934AB4364148FB9
//plain:  0649A51281BB76A8C7C627F8F95E3321
