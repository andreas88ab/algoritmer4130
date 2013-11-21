package oblig3algoritmer;

import java.util.*;
import java.io.*;
import java.lang.*;

/**
 *
 * @author ab
 */
public class Oblig3v4 {

    private static int n;
    private static List<Node> nodes;
    private static List<List<Node>> paths;

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
        nodes = new ArrayList<Node>();
        Node newNode;
        for (int i = 0; i < n; i++) {
            newNode = new Node();
            newNode.number = i + 1;
            newNode.paths = new ArrayList<Integer>();
            newNode.visited = new boolean[n];
            newNode.path = new ArrayList<Integer>();
            newNode.path.add(1);
            for (int j = 0; j < n; j++) {
                if (arrN[i][j] != 0) {
                    newNode.paths.add(arrN[i][j]);
                } else {
                    newNode.paths.add(0);
                }
            }
            //newNode.nPaths();
            System.out.println(newNode.paths.toString());
            nodes.add(newNode);
        }
        System.out.println("Path nodes(0) " + nodes.get(0).path.toString());

        LinkedList<Node> fifo = new LinkedList<Node>();

        boolean cut = false;
        //Finds all paths
        //while(!cut){
        fifo.add(nodes.get(0));
        Node wN;
        while (!fifo.isEmpty()) {

            wN = copyNode(fifo.pollFirst());
            System.out.println("WorkingNode = " + wN.number);
            for (int i = 0; i < n; i++) {
                if (wN.paths.get(i) != 0) {
                    nodes.get(wN.number - 1).visited[i] = true;
                    Node nN = copyNode(nodes.get(i));
                    nN.path = copyPath(wN);
                    nN.path.add(nN.number);
                    System.out.println(nN.path.toString());
                    fifo.add(nN);
                }

            }
            if (wN.number == n) {
                System.out.println("Siste" + wN.path.toString());
                System.out.println("Ferdig1");
                break;
            }
            //break;

        }

        //}
//        for (int i = 0; i < paths.size(); i++) {
//            printList(paths.get(i));
//        }
    }

    public static Node copyNode(Node node) {
        Node newNode = new Node();
        newNode.number = node.number;
        newNode.visited = new boolean[n];
        newNode.path = new ArrayList<Integer>();
        newNode.paths = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            newNode.paths.add(node.paths.get(i));

            newNode.visited[i] = node.visited[i];

        }

        for (int i = 0; i < node.path.size(); i++) {
            newNode.path.add(node.path.get(i));
        }
        return newNode;
    }

    public static List<Integer> copyPath(Node node) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < node.path.size(); i++) {
            list.add(node.path.get(i));
        }
        return list;
    }

    public static void printList(List<Node> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i).number + "  ");
        }
        System.out.print("\n");
    }

    private static boolean allNodesVisited(Node n) {
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

class Node {

    //An array that holds the nodes visited from this node.
    //Helps when finding the possible paths to find the shortest one
    boolean[] visited;
    //Index in paths is the node a path lead to. The value is the capacity
    List<Integer> paths;
    //Number of paths. When capacity reach 0, there is no more possible
    //paths to that node from this one
    int nPaths;
    //Temp path
    List<Integer> path;
    //Number is the nodenumber in the figure.
    int number;

//    public void nPaths() {
//        nPaths = 0;
//        for (int i = 0; i < paths.length; i++) {
//            if (paths[i] != 0) {
//                nPaths++;
//            }
//        }
//    }
}
