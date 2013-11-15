
package oblig3algoritmer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Oblig3algoritmer {
    private static int n;

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("" + args[0]);
        Scanner in = new Scanner(new File(args[0]));
        
        n = in.nextInt();

        int[][] arrN = new int[n][n];
        int x = 0;
        int y = 0;
        while (in.hasNextInt() && x < n) {
            int tall = in.nextInt();
            if (y == n) {
                y = 0;
                x++;
                arrN[x][y] = tall;
                y++;
            } else {
                arrN[x][y] = tall;
                y++;
            }

        }
        
        printArray(arrN);
    }
    
//    public static void arrayCopy(int[][] src, Node dest) {
//        for (int k = 0; k < n; k++) {
//            System.arraycopy(src[k], 0, dest.arr[k], 0, src[k].length);
//        }
//    }
    
    public static void printArray(int[][] arr) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
    
}
