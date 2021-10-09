package lab2;

public class ElGamal {
    public static void main(String[] args) {
        Dots_Field start_field = new Dots_Field(31991, 31988, 1000, 0, 5585);
        Cypher test = new Cypher(10000);
        int R[] = new int[3];
        Cord_Pair Db = new Cord_Pair(12507, 2027);
        Cord_Pair G = new Cord_Pair(0, 5585);
        int[] in = {9767,11500,11685,5103};
        int res;

        R = test.cypher(523,Db,31991);
        System.out.println(R[0]+" "+R[1]+" "+R[2]);

        res = test.decypher(in);
        System.out.println(res);



    }
}

class Simple_number {
    int digits_num;
    Simple_number(int i){
        digits_num = i;
    }
    int generator(){
        int min, max, p;
        min = (int) Math.pow(2, digits_num - 1);
        max = (int) Math.pow(2, digits_num) - 1;
        p = (int) (Math.random() * (max - min) + min);
        while (true) {
            p = (int) (Math.random() * (max - min) + min);
            if (p % 2 == 0 || p % 3 == 0 || p % 5 == 0 || p % 2 == 7 || p % 11 == 0) continue;
            if (mr_test(p, 50) == true) break;
        }
        return p;
    }
    static int mod_pow(int a,int t,int n){
        int bit;
        int bit_counter = -1;
        int temp_t = t;
        while (temp_t > 0){
            bit_counter += 1;
            temp_t >>= 1;
        }
        long res = 1;
        for (int i = -1; i < bit_counter;){
            bit = t >> bit_counter;
            bit_counter -= 1;
            if ((bit & 1) == 1) res = (res * a) % n;
            res = (res * res) % n;
        }
        return ((int)res);
    }
    boolean mr_test(int n,int  k){
        boolean flag;
        int t, s;
        s = 0;
        t = n - 1;
        while (t % 2 == 0){
            t /= 2;
            s += 1;
        }
        for (int i = 0; i < k; i++) {
            flag = false;
            int a = (int) (Math.random() * (n - 2)) + 2;
            int x =  mod_pow(a, t, n);
            if (x == 1 | x == n - 1) continue;
            for (int j = 0; j < s - 1;j++){
                x = (x * x) % n;
                if (x == 1) return false;
                if (x == n - 1) {
                    flag = true;
                    break;
                }
            }
            if (flag == true) continue;
            return  false;
        }
        return true;
    }
}

class Cypher{
    Cord_Pair point = new Cord_Pair(0, 5585);
    int msg;
    Cypher(int text){
        msg = text;
    }
     int[] cypher(int k,Cord_Pair Db,int p){
        Cord_Pair R = new Cord_Pair();
        int x,y;
        R = Dots_Field.multiplier(k, point);
        Cord_Pair P = new Cord_Pair();
        P = Dots_Field.multiplier(k,Db);
        msg = (msg * P.x) % p;
        int res[] = new int[3];
        res[0] = R.x;
        res[1] = R.y;
        res[2] = msg;
        return res;
     }
     int decypher(int[] text){
        Cord_Pair R = new Cord_Pair(text[0],text[1]);
        int e = text[2];
        int Cb = text[3];
        Cord_Pair Q = new  Cord_Pair();
        Q = Dots_Field.multiplier(Cb,R);
        msg = e * Dots_Field.reverse(Q.x) % Dots_Field.GF;
        return msg;
     }
}
