package newpackage;

import java.util.*;
import java.io.*;
import java.lang.*;

/**
 *
 * @author ab
 */
public class Oblig3v2 {

    private static int n;

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

        //Initializing a lists of the nodes with apropriate variables
        List<Node> nodes = new ArrayList<Node>();
        Node newNode;
        for (int i = 0; i < n; i++) {
            newNode = new Node();
            newNode.number = i + 1;
            newNode.paths = new int[n];
            newNode.visited = new boolean[n];
            for (int j = 0; j < n; j++) {
                if (arrN[i][j] != 0) {
                    newNode.paths[j] = arrN[i][j];
                }
            }
            newNode.nPaths();
//            System.out.println(Arrays.toString(newNode.paths) + 
//                    "\n" + newNode.number + "\n" + newNode.nPaths);            
            nodes.add(newNode);
        }

        int i = 0;
        int j = 0;
        boolean allPath = false;
        boolean cut = false;
        int[][] paths = new int[n][n];
        int iPath = 0;
        int jPath = 0;
        Node workingNode = nodes.get(i);
//        Node firstNodeInPath = nodes.get(i);
        boolean[] nodesVisited = new boolean[n];
        int jumpBack = 1;
        boolean nodeChanged = false;
        while (!cut) {
            //waits for all paths to be found
            while (!allPath) {
                //waits for one path to be found
                nodeChanged = false;
                while (j < n) {
                    if (workingNode.paths[j] != 0 && !workingNode.visited[j] && !nodesVisited[j]) {
                        System.out.println("forbannade");
                        workingNode.visited[j] = true;
                        nodeChanged = true;
                        nodesVisited[j] = true;
//                        for (boolean b : nodesVisited) {
//                            System.out.println(b);
//                        }
                        //Finner maksflow
                        System.out.println("iPath " + iPath);
                        if (paths[iPath][paths.length - 1] == 0) {
                            paths[iPath][paths.length - 1] = workingNode.paths[j];
                        } else {
                            paths[iPath][paths.length - 1] = maxFlow(paths[iPath][paths.length - 1], workingNode.paths[j]);
                        }
                        workingNode = nodes.get(j);
                        paths[iPath][jPath++] = j + 1;

                        break;
                    } else if ((j + 1) == n && i == 0) {

                        //allPath = true;
                        break;
                    } else {
                        j++;
                    }

                }
                printArray(paths);
                if(!nodeChanged){
                    jPath = jPath -1;
                    if(jPath == -1){
                        workingNode = nodes.get(0);
                        System.out.println("workingNode = " + nodes.get(0));
                    }else if(jPath >= 0){
                        System.out.println("workingNode = " + nodes.get(paths[iPath][jPath] - 1).number
                        + "jPath = " + jPath);
                        workingNode = nodes.get(paths[iPath][jPath] - 1);
                        nodesVisited[paths[iPath][jPath+1]-1] = false;
                        for (boolean b : nodesVisited) {
                            System.out.println(b);
                        }
                    }else{
                        //allPath = true;
                    }
                    
                    
                }
                //Hvis vi er kommet til node n (siste node)
                if (nodesVisited[n - 1]) {
                    jPath = jPath -1;
                    if (pathEqual(paths)) {
                        System.out.println("Path equal");
                        i = 0;
                        j = 0;
                        System.out.println("jPath " + jPath);
//                        for(int k = jPath; k > jPath - jumpBack; k--){
//                            nodesVisited[paths[iPath][k]-1] = false;
//                            for (boolean b : nodesVisited) {
//                            System.out.println(b);
//                        }
//                            paths[iPath][k] = 0;
//                            System.out.println("NY");
//                            printArray(paths);
//                        }
                        jPath = jPath - 1;
                        System.out.println("Nodenr " + nodes.get(paths[iPath][jPath] - 1).number);
                        workingNode = nodes.get(paths[iPath][jPath] - 1);
                        System.out.println("workingNode = " + nodes.get(paths[iPath][jPath] - 1).number);
                        paths[iPath][jPath+1] = 0;
                        
                    } else {
                        System.out.println("Ferdig path funnet");
                        nodesVisited = new boolean[n];
                        for (int k = 0; k < nodes.size(); k++) {
                            nodes.get(k).visited = new boolean[n];
                        }
                        i = 0;
                        j = 0;
                        iPath++;
                        jPath = 0;
                        workingNode = nodes.get(0);
                    }
                }
//                else if(){
//                    
//                }
                //System.out.println("I = " + i + " J = " + j);


            }
            System.out.println("galt");
            break;
        }
    }
    
    public static getPath(Node workingNode){
        
    }
    
    private static boolean allNodesVisited(Node n){
        return true;
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

    public static int maxFlow(int pMin, int nMin) {
        if (nMin < pMin) {
            return nMin;
        } else {
            return pMin;
        }
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

    public static void arrayCopy(int[][] src, int[][] dest) {
        for (int k = 0; k < n; k++) {
            System.arraycopy(src[k], 0, dest[k], 0, src[k].length);
        }
    }
}

class Nod1e {

    //An array that holds the nodes visited from this node.
    //Helps when finding the possible paths to find the shortest one
    boolean[] visited;
    //Index in paths is the node a path lead to. The value is the capacity
    int[] paths;
    //Number of paths. When capacity reach 0, there is no more possible
    //paths to that node from this one
    int nPaths;
    //Number is the nodenumber in the figure.
    int number;

    public void nPaths() {
        nPaths = 0;
        for (int i = 0; i < paths.length; i++) {
            if (paths[i] != 0) {
                nPaths++;
            }
        }
    }
}
