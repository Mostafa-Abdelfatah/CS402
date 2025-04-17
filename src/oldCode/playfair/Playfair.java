package oldCode.playfair;

class Pair {
    char e1;
    int pos;
    public Pair(char e1, int pos){
        this.e1 = e1;
        this.pos = pos;
    }
}

public class Playfair {
     char[][] mat = new char[5][5];
     int sizeOfNonLetter = 0;
    Pair[] non;

    // setting up the matrix 5x5 neglect I and put J
    public void setUpMatrix(String k) {
         k = k.toUpperCase();
        int[] arr = new int[26];
        for(int i = 0; i < k.length(); i++) {
            arr[k.charAt(i) - 'A'] = 1;
        }
        // put differant letters of key in matrix
        char[] tempK = new char[25];
        int j = 0;
        for(int i = 0; i < k.length(); i++) {
            if(arr[k.charAt(i) - 'A'] == 1) {
                tempK[j] = k.charAt(i);
                j++;
                arr[k.charAt(i) - 'A'] = 2;
            }
        }
        for(int i = 0; i < 26; i++) {
            if(arr[i] == 0) {
                if((char)('A' + i) == 'J') {
                    continue;
                }
                else {
                    tempK[j] = (char) ('A' + i);
                    j++;
                }
            }
        }
        j = 0;
        for(int i = 0; i < 5; i++) {
            for (int t = 0; t < 5; t++) {
                mat[i][t] = tempK[j];
                j++;
            }
        }

        for(int i = 0; i < 5; i++) {
            for (int t = 0; t < 5; t++){
                System.out.print(mat[i][t] + " ");
            }
            System.out.println();
        }
    }

    // make the string ready to encrypt or decrypt

    public String setUpText(String in) {
        in = in.toUpperCase();
        String clear = "";
        for(int i = 0; i < in.length(); i++) {
            if(!Character.isAlphabetic(in.charAt(i))) {
                sizeOfNonLetter++;
            }
            else {
                clear += in.charAt(i);
            }
        }

        non = new Pair[sizeOfNonLetter];
        int idx = 0;
        for(int i = 0; i < in.length(); i++) {
            if(!Character.isAlphabetic(in.charAt(i))) {
                non[idx] = new Pair(in.charAt(i), i);
                idx++;
            }
        }
        in = clear;
        StringBuffer str = new StringBuffer(in);
        for(int i = 0; i < str.length() - 1; i++) {
            if(i % 2 == 0) {
                if(str.charAt(i) == str.charAt(i + 1)) {
                    str.insert(i + 1, 'X');
                }
            }
        }
        String ot = str.toString();
        if(ot.length() % 2 == 1) {
            ot += 'X';
        }
        return ot;
    }

    public static int[] findCharacter(char[][] matrix, char target) {
        int[] indices = new int[2];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == target) {
                    indices[0] = i;
                    indices[1] = j;
                    return indices;
                }
            }
        }
        // Character not found, return (-1, -1)
        indices[0] = -1;
        indices[1] = -1;
        return indices;
    }
    //--------------------------------------------------------------------
    public String encrypt(String preparedText, char[][] matrix, Pair[] arr) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < preparedText.length(); i += 2) {
            char char1 = preparedText.charAt(i);
            char char2 = preparedText.charAt(i + 1);

            // Find indices of characters in the matrix
            int[] index1 = findCharacter(matrix, char1);
            int[] index2 = findCharacter(matrix, char2);

            // Same row, shift right
            if (index1[0] == index2[0]) {
                encryptedText.append(matrix[index1[0]][(index1[1] + 1) % 5]);
                encryptedText.append(matrix[index2[0]][(index2[1] + 1) % 5]);
            }
            // Same column, shift down
            else if (index1[1] == index2[1]) {
                encryptedText.append(matrix[(index1[0] + 1) % 5][index1[1]]);
                encryptedText.append(matrix[(index2[0] + 1) % 5][index2[1]]);
            }
            // Different row and column, form rectangle, take diagonals
            else {
                encryptedText.append(matrix[index1[0]][index2[1]]);
                encryptedText.append(matrix[index2[0]][index1[1]]);
            }
        }
        for(int i = 0; i < arr.length; i++) {
            encryptedText.insert(arr[i].pos, arr[i].e1);
        }

        return encryptedText.toString();
    }
    //---------------------------------------------------
    public String decrypt(String ciphertext, char[][] matrix, Pair[] arr) {
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            char char1 = ciphertext.charAt(i);
            char char2 = ciphertext.charAt(i + 1);

            // Find indices of characters in the matrix
            int[] index1 = findCharacter(matrix, char1);
            int[] index2 = findCharacter(matrix, char2);

            // Same row, shift left
            if (index1[0] == index2[0]) {
                decryptedText.append(matrix[index1[0]][(index1[1] - 1 + 5) % 5]);
                decryptedText.append(matrix[index2[0]][(index2[1] - 1 + 5) % 5]);
            }
            // Same column, shift up
            else if (index1[1] == index2[1]) {
                decryptedText.append(matrix[(index1[0] - 1 + 5) % 5][index1[1]]);
                decryptedText.append(matrix[(index2[0] - 1 + 5) % 5][index2[1]]);
            }
            // Different row and column, form rectangle, take diagonals
            else {
                decryptedText.append(matrix[index1[0]][index2[1]]);
                decryptedText.append(matrix[index2[0]][index1[1]]);
            }
        }
        for(int i = 0; i < arr.length; i++) {
            decryptedText.insert(arr[i].pos, arr[i].e1);
        }

        return decryptedText.toString();
    }


    public static void main(String[] args) {
        Playfair p1 = new Playfair();
        p1.setUpMatrix("hello");
        String s = p1.setUpText("#attacka5#tdawn");
        System.out.println(p1.encrypt(s, p1.mat, p1.non));
        System.out.println("-----------------------------");
        Playfair p2 = new Playfair();
        p2.setUpMatrix("hello");
        System.out.println(p2.decrypt(p2.setUpText("#OUUOKRO5#UGLYK"), p2.mat, p2.non));
    }
}
//ATTACKATDAWN
//OUUOKROUGLYK