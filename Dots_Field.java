package lab2;

public class Dots_Field {
    public  static Cord_Pair cords = new Cord_Pair();
    public static int GF,a4,a6,x1,y1;
    public static int dots_list[][] = new int[32089][2];
    Dots_Field(int field_size,int a4_equal,int a6_equal,int X_cord,int Y_cord){
        GF = field_size;
        a4 = a4_equal;
        a6 = a6_equal;
        x1 = X_cord;
        y1 = Y_cord;
        dots_list[0][0] = x1;
        dots_list[0][1] = y1;
    }
    public static void main_builder() {
        builder();
    }
    static  void builder(){     //расчет точек
        cords = first_cord(x1, y1);
        dots_list[1][0] = cords.x;
        dots_list[1][1] = cords.y;
        for (int i = 2; i < dots_list.length; i++){
            int x2 = dots_list[i-1][0];
            int y2 = dots_list[i-1][1];
            cords = dots_sum_x(x2, y2, x1 , y1);
            dots_list[i][0] = cords.x;
            dots_list[i][1] = cords.y;
            if (dots_list[i][0] == 0 && dots_list[0][1] == 0) break;
        }
    }
    static Cord_Pair first_cord(int x,int y){
        long lyambda = (long) (3 * Math.pow(x,2) + a4) * reverse(2 )* reverse(y) % GF;
        long cord_x = ((long) Math.pow(lyambda, 2) -x - x)%GF;
        if (cord_x < 0) cord_x = (GF + cord_x);
        long cord_y = ((lyambda * (x - cord_x)) - y)%GF ;
        if ( cord_y < 0) cord_y = (GF + cord_y);
        cords = new  Cord_Pair((int)cord_x, (int)cord_y);
        return(cords);
    }
    static Cord_Pair dots_sum_x(int x2,int y2, int x1, int y1) {// расчет координат х y
        if (y2 == 0 && x2 == 0){
            cords = new Cord_Pair(x1,y1);
            return(cords);
        }
        if (y2 == (GF - y1) && x2 == x1){
            cords = new Cord_Pair();
            return(cords);
        }
        long lyambda = ((y2 - y1) * reverse((x2 - x1))) % GF;
        long v =  ((y1 * x2 - y2 * x1) % GF * reverse((x2 - x1))) % GF;
        long cord_x = ((long) Math.pow(lyambda, 2) -x1 - x2)%GF;
        if (cord_x < 0) cord_x = (GF + cord_x);
        long cord_y = ((lyambda * (x1 - cord_x)) - y1)%GF ;
        if ( cord_y < 0) cord_y = (GF + cord_y);
        cords = new  Cord_Pair((int) cord_x,(int) cord_y);
        return(cords);
    }

    static Cord_Pair multiplier(int k,Cord_Pair dot_cords){
        Cord_Pair res = new Cord_Pair();
        int bit;
        int bit_counter = -1;
        int temp_k = k;
        while (temp_k > 0){
            bit_counter += 1;
            temp_k >>= 1;
        }
        for (int i = -1; i < bit_counter;){
            bit = k >> bit_counter;
            bit_counter -= 1;
            res = first_cord(res.x,res.y);
            if ((bit & 1) == 1) res = dots_sum_x(res.x,res.y, dot_cords.x, dot_cords.y);
        }
        return res;
    }


    static  int reverse(int a){ // расчет обратного а
        int n = GF - 2;
        int res = 1;
        while ((n) != 0){
            if ((n & 1) == 1) res = (res * a) % GF;
            a = (a * a) % GF;
            n >>= 1;
        }
        return res;
    }
    static boolean dot_chekcer(int x, int y){
        for (int i = 0; i < dots_list.length; i++){
            if (dots_list[i][0] == x && dots_list[i][1] == y) return true;
            if (dots_list[i][0] == 0 && dots_list[0][1] == 0) return false;;
        }
        return false;
    }



}
class Cord_Pair {
    int x;
    int y;
    Cord_Pair(int i, int j){
        x = i;
        y = j;
    }
    Cord_Pair(){
        x = 0;
        y = 0;
    }
    Cord_Pair(Cord_Pair cords){
        x = cords.x;
        y = cords.y;
    }
}
