package oldCode.hillCipher;

class Pair {
    char e1;
    int pos;
    public Pair(char e1, int pos){
        this.e1 = e1;
        this.pos = pos;
    }
}


public class Hill {

    long[][] mat = new long[3][3];
    long[][] invMat = new long[3][3];
    long[][] matAttackP = new long[3][3];
    long[][] matAttackC = new long[3][3];


    // check determinant of the inverse

//    public static long  valid_matrix(long[][] arr) {
//        long d = arr[0][0] * ( (arr[1][1] * arr[2][2]) - (arr[1][2] * arr[2][1]) );
//        d -= arr[0][1] * ( (arr[1][0] * arr[2][2]) - (arr[1][2] * arr[2][0]) );
//        d += arr[0][2] * ( (arr[1][0] * arr[2][1]) - (arr[1][1] * arr[2][0]) );
//        return ( (d % 26) + 26) % 26;
//    }

    public static long determinant(long[][] matrix) {
        long det = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
                - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
                + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
        return ((det%26) + 26) % 26;
    }

    // set up the matrix of key
    public void setMat(String k) {
        int idx = 0;
        k = k.toUpperCase();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                this.mat[i][j] = (long) (k.charAt(idx) - 'A');
                idx++;
            }
        }
    }

    public long[][] getMat() {
        return mat;
    }

    //encrypt
    public String encrypt(String p, long[][] mat) {
        p = p.toUpperCase();
        StringBuilder s = new StringBuilder();
        int l = 0;

        // remove non letter
        for(int i = 0; i < p.length(); i++) {
            if(Character.isAlphabetic(p.charAt(i)))
                s.append(p.charAt(i));
            else {
                l++;
            }
        }

        // keep the non letter and it's position
        Pair[] notChar = new Pair[l];
        int idx = 0;
        for(int i = 0; i < p.length(); i++) {
            if(!Character.isAlphabetic(p.charAt(i))){
                notChar[idx] = new Pair(p.charAt(i), i);
                idx++;
            }
        }

        // make the length of s mod 3 = 0
        int t = s.length() % 3;
        for(int i = 0; i < t; i++) {
            s.append('X');
        }

        StringBuilder ans = new StringBuilder();
        String[] a = new String[s.length() / 3];
        for(int i = 0; i < (s.length() / 3) ; i++)
            a[i] = "";
        idx = 0; // for string s
        int idx2 = 0; // for array a
        for(int i = 0; idx < s.length() && idx2 < (s.length() / 3); i++) {
            if(i == 0 || i % 3 != 0) {
                a[idx2] += s.charAt(idx);
                idx++;
            }
            else {
                idx2++;
                i = -1;
            }
        }

        // the real encrypt
        for(int i = 0; i < s.length()/3; i++) {
            ans.append( (char) ( (( (int)(a[i].charAt(0) - 'A') * mat[0][0] +
                    (int)(a[i].charAt(1) - 'A') * mat[1][0] +
                    (int)(a[i].charAt(2) - 'A') * mat[2][0] )
                    % 26) + 'A')  );
            ans.append( (char) ( (( (int)(a[i].charAt(0) - 'A') * mat[0][1] +
                    (int)(a[i].charAt(1) - 'A') * mat[1][1] +
                    (int)(a[i].charAt(2) - 'A') * mat[2][1] )
                    % 26)+ 'A' ) );
            ans.append( (char) ( (( (int)(a[i].charAt(0) - 'A') * mat[0][2] +
                    (int)(a[i].charAt(1) - 'A') * mat[1][2] +
                    (int)(a[i].charAt(2) - 'A') * mat[2][2] )
                    % 26) + 'A')  );
        }

//        for(int i = 0; i < notChar.length; i++) {
//            ans.insert(notChar[i].pos, notChar[i].e1);
//        }

        return ans.toString();
    }
//--------------------------------------------------------------

    // decrypt

    // get multi. invarese of det(K)
    public long getMultInv(long det) {
        if(det == 1)
            return 1;
        else {
            for(int i = 2; i < 26; i++) {
                if((det * i) % 26 == 1){
                    return i;
                }
            }
        }
        return -1;
    }

    // get adjoint matrix
    public static long[][] adjointMatrix(long[][] matrix) {
        long[][] adjoint = new long[3][3];
        adjoint[0][0] = matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1];
        adjoint[0][0]  = ( (adjoint[0][0] % 26) + 26) %26;

        adjoint[0][1] = matrix[0][2] * matrix[2][1] - matrix[0][1] * matrix[2][2];
        adjoint[0][1]  = ( (adjoint[0][1] % 26) + 26) %26;

        adjoint[0][2] = matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1];
        adjoint[0][2]  = ( (adjoint[0][2] % 26) + 26) %26;

        adjoint[1][0] = matrix[1][2] * matrix[2][0] - matrix[1][0] * matrix[2][2];
        adjoint[1][0]  = ( (adjoint[1][0] % 26) + 26) %26;

        adjoint[1][1] = matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0];
        adjoint[1][1]  = ( (adjoint[1][1] % 26) + 26) %26;

        adjoint[1][2] = matrix[0][2] * matrix[1][0] - matrix[0][0] * matrix[1][2];
        adjoint[1][2]  = ( (adjoint[1][2] % 26) + 26) %26;

        adjoint[2][0] = matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0];
        adjoint[2][0]  = ( (adjoint[2][0] % 26) + 26) %26;

        adjoint[2][1] = matrix[0][1] * matrix[2][0] - matrix[0][0] * matrix[2][1];
        adjoint[2][1]  = ( (adjoint[2][1] % 26) + 26) %26;

        adjoint[2][2] = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        adjoint[2][2]  = ( (adjoint[2][2] % 26) + 26) %26;
        return adjoint;
    }

    public void setInvMat() {
        long d = determinant(this.mat);
        d = getMultInv(d);
        long[][] v = adjointMatrix(this.mat);
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
               this.invMat[i][j] = (v[i][j] * d) % 26;
            }
        }

    }

    public long[][] getInvMat() {
        return invMat;
    }

    // I will use the encrypt method to decrypt no need to write a new one
    //---------------------------------------------------------------------

    //attack

    public void setMatAttackP_setMatAttackC(String p, String c) {
        p = p.toUpperCase();
        c = c.toUpperCase();
        String[] tempC = new String[c.length()/3];
        for(int i = 0; i < c.length()/3; i++) {
            tempC[i] = "";
        }
        int  i = 0;
        int t = 0;
        for(int idxC= 0; idxC < c.length();) {
            if(t != 3) {
                tempC[i] += c.charAt(idxC);
                idxC++;
                t++;

            }
            else {
                i++;
                t = 0;
            }
        }
        for(i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                matAttackC[i][j] = tempC[i].charAt(j) - 'A';
            }
        }

        String[] tempP = new String[p.length()/3];
        for(i = 0; i < p.length()/3; i++) {
            tempP[i] = "";
        }
        i = 0;
        t =0;
        for(int idxP= 0; idxP < p.length();) {
            if(t != 3) {
                tempP[i] += p.charAt(idxP);
                idxP++;
                t++;
            }
            else {
                i++;
                t = 0;
            }
        }
        for(i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                matAttackP[i][j] = tempP[i].charAt(j) - 'A';
            }
        }
        if(determinant(matAttackP) != 0) {
            long d = determinant(matAttackP);
            d = getMultInv(d);
            if(d == -1) {
                System.out.println("no multiplicative invearse");
                return;
            }
            long[][] v = adjointMatrix(matAttackP);
            for( i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    this.matAttackC[i][j] = (v[i][j] * d) % 26;
                }
            }
        }
    }

    public String attack() {
        long[][] result = new long[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    result[i][j] += matAttackP[i][k] * matAttackC[k][j];
                }
            }
        }

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                result[i][j] = ((result[i][j] % 26) + 26) % 26;
            }
        }
        String key = "";
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                key += (char)('A' + result[i][j]);
            }
        }
        return key;
    }




    /*
arr[0][0]  arr[0][1]  arr[0][2]
arr[1][0]  arr[1][1]  arr[1][2]
arr[2][0]  arr[2][1]  arr[2][2] */
}
