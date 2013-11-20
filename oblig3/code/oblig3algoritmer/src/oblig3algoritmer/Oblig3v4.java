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
            newNode.paths = new int[n];
            newNode.visited = new boolean[n];
            for (int j = 0; j < n; j++) {
                if (arrN[i][j] != 0) {
                    newNode.paths[j] = arrN[i][j];
                }
            }
            newNode.nPaths();
            System.out.println(Arrays.toString(newNode.paths) + 
                    "\n" + newNode.number + "\n" + newNode.nPaths);            
            nodes.add(newNode);
        }

        paths = new ArrayList<List<Node>>();
        
        List<Node> start = new ArrayList<Node>();
        start.add(nodes.get(0));
        getPath(nodes.get(0), start);
        System.out.println("Siste");
        for(int i = 0; i < paths.size(); i++){
            printList(paths.get(i));
        }
        

    }

    public static List<Node> getPath(Node workingNode, List<Node> p) {
        
        if (workingNode.number == n) {
            System.out.println("return");
            return p;
        }
        for (int i = 0; i < n; i++) {
//            System.out.println("I = " + i);
            System.out.println("Workingnode = " +  workingNode.number);
            System.out.println("her " + workingNode.paths[i] + ". I = " + i);
            if (workingNode.paths[i] != 0 && !workingNode.visited[i]) {
                System.out.println("I = " + i);
                Node temp = workingNode;
                workingNode.visited[i] = true;
                
//                System.out.println("Node " + p.get(p.size() - 1).get(p.get(p.size() - 1).size() - 1 ).number);
                if (!p.contains(workingNode) || workingNode.number == 1) {
                    //p.get(p.size() - 1).get(p.get(p.size() - 1).size() - 1 )
                    workingNode = nodes.get(i);
//                    System.out.println("jupp");
                    List<Node> cp = copyPath(p);
                    
                    
                    cp.add(workingNode);
                    
                    printList(cp);
                    
                    List<Node> res = getPath(workingNode, cp);
                    if(res != null){
                        paths.add(res);
                    }
                } else {
                    workingNode = temp;
//                    System.out.println("Nope");
                    
                }
            }
        }
        return null;

    }

    public static List<Node> copyPath(List<Node> list) {
        List<Node> cp = new ArrayList<Node>();
        for (int i = 0; i < list.size(); i++) {
            cp.add(list.get(i));
        }
        return cp;
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
