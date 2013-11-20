package newpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Oblig3algoritmer {

    private static int n;
    private static boolean[] nodesVisited;
    private static int[][] arrNtemp;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File(args[0]));

        n = in.nextInt();

        //
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

        int[][] arrNf = new int[n][n];
        arrNtemp = new int[n][n];
        nodesVisited = new boolean[n];
        int[][] paths = new int[n][n];
        int iPath = 0;
        int jPath = 0;
        arrayCopy(arrN, arrNtemp);
        boolean allPath = false;
        boolean cut = false;
        int i = 0;
        int j = 0;
        while (!cut) {
            while (!allPath) {

                //System.out.println("I = " + i);
                //Finner første forekomst av en verdi på linjen i
                while (j < n) {
                    if (arrNtemp[i][j] != 0) {
                        break;
                    } else if ((j + 1) == n) {
                        if ((i + 1) < n) {
                            i++;
                        }
                        j = 0;

                    } else {
                        
                        j++;
                    }
                }
                System.out.println("i = " + i + "\nj = " + j);

                if (nodesVisited[j] == false) {
                    //Setter mulig økning i flow. Sammenligner hver path
                    //og setter max økning i flow i siste rute i path array
                    //for hver path. 
                    if (arrNtemp[i][j] < paths[iPath][n - 1] || i == 0) {
                        paths[iPath][n - 1] = arrNtemp[i][j];
                    }
                    paths[iPath][jPath++] = j + 1;
                    i = j;
                    nodesVisited[i] = true;
                    j = 0;

                } else {
                    if(j+1 != n)
                        j++;
                }

                if (j == n && i != n - 1) {
                }

                //Hvis n er nådd, eller cut = true
                if ((i + 1) == n) {
                    printArray(paths);
                    if (pathEqual(paths)) {
                        i = jumpBackinPath(paths);
                        jPath = i;
                        System.out.println("I i path = " + (i));
                        for (int k = i+2; k < i+1; k++) {
                            if(!isNodeInPath(iPath, jPath, paths)){
                                nodesVisited[k] = false;
                            }
                        }
//                        for (int k = i+2; k < n; k++) {
//                            nodesVisited[k] = false;
//                        }

                        for (int k = 0; k < n; k++) {
                            if (paths[iPath][k] == i + 1) {
                                jPath = k + 1;
                            }
                        }
                        for (int k = jPath + 1; k < paths.length; k++) {
                            System.out.println("null ut " + paths[iPath + 1][k]);
                            paths[iPath][k] = 0;
                        }
                        System.out.println("iPath = " + iPath + " jPath = " + jPath);
                        paths[iPath][paths.length - 1] = updateMaxFlow(paths, arrNtemp, jPath, iPath);

                    } else {
                        i = 0;
                        j = 0;
                        nodesVisited = new boolean[n];
                        iPath++;
                        jPath = 0;
                    }

//                    allPath = true;
                }
//            for (boolean b : nodesVisited) {
//                System.out.println(b);
//            }

            }
//            int minPath = shortestPath(paths);
//            System.out.println("Minst vei" + minPath);
//            node = 0;
//            for (int jNode = 0; jNode < paths.length; jNode++) {
//                //System.out.println("paths[minPath][jNode] " + paths[minPath][jNode]);
//                if (paths[minPath][jNode] == 0) {
//                    break;
//                }
//                arrNtemp[node][paths[minPath][jNode] - 1] -= paths[minPath][paths.length - 1];
//                arrNf[node][paths[minPath][jNode] - 1] += paths[minPath][paths.length - 1];
//                node = paths[minPath][jNode] - 1;
//            }
//            System.out.println("arrN");
//            printArray(arrN);
//            System.out.println("arrNtemp");
//            printArray(arrNtemp);
//
//            System.out.println("arrNf");
//            printArray(arrNf);
//            break;
        }

    }
    
    private static boolean isNodeInPath(int i, int j, int[][] p){
        int node = p[i][j]-1;
        for(int k = 0; k < n; k++){
            if(arrNtemp[node][k] == node){
                System.out.println("True = " + k);
                return true;
            }
        }
        return false;
    }

    private static int updateMaxFlow(int[][] p, int[][] a, int end, int start) {
        int min = a[0][p[start][0] - 1];
        int k = 0;
        while (k != end) {

            if (a[p[start][k] - 1][p[start][k + 1] - 1] < min) {
                min = a[p[start][k] - 1][p[start][k + 1] - 1];

            }
            System.out.println("min " + min + " end " + end + " start " + start);
            k++;
        }
        return min;
    }

    private static int jumpBackinPath(int[][] p) {
        int iPath = 0;
        while (p[iPath + 1][0] != 0) {
            iPath++;
        }
        int jPath = p.length - 2;
        while (p[iPath][jPath] == 0) {
            jPath--;
        }
        if (p[iPath][jPath] == n) {
            System.out.println("Ferdig path " + (p[iPath][jPath - 2] - 1));

            return p[iPath][jPath - 2] - 1;
        } else {
            System.out.println("Ikke ferdig path");
            return p[iPath][jPath - 1] - 1;
        }
    }

    private static boolean pathEqual(int[][] p) {
        int iPath = 0;
        while (p[iPath + 1][0] != 0) {
            iPath++;

        }

        boolean equal;
        for (int i = 0; i < iPath; i++) {
            equal = true;
            for (int j = 0; j < p.length - 1; j++) {
                System.out.println("i equal = " + i + ", " + p[i][j] + " = " + p[iPath][j]);
                if (p[i][j] != p[iPath][j]) {
                    equal = false;
                }
            }
            if (equal) {
                return true;
            }
        }
        return false;
    }

    public static void arrayCopy(int[][] src, int[][] dest) {
        for (int k = 0; k < n; k++) {
            System.arraycopy(src[k], 0, dest[k], 0, src[k].length);
        }
    }

    public static int shortestPath(int[][] p) {
        int minPath = p.length + 1;
        int min = p.length + 1;
        int j = 0;
        for (int i = 0; i < p.length; i++) {
            if (p[i][j] != 0) {
                j = 0;
                while (p[i][j] != 0) {
                    j++;
                }
                if (j < min) {
                    minPath = i;
                }
            }
        }
        return minPath;
    }

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
