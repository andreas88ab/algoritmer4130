/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.util.*;
import java.io.*;
import java.lang.*;

/**
 *
 * @author andbergl
 */
public class Oblig3v3 {

    private static int n;
    private static int sink;
    private static List<Node> nodes;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File(args[0]));

        n = in.nextInt();
        sink = n;
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

        printArray(arrN);

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
//            System.out.println(Arrays.toString(newNode.paths) + 
//                    "\n" + newNode.number + "\n" + newNode.nPaths);            
            nodes.add(newNode);
        }

        Path p = new Path();
        
        getPath(nodes.get(0), p);
        System.out.println("Siste");
        p.printPath();

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

    public static Path getPath(Node workingNode, Path p) {
        if (workingNode.number == n) {
            return p;
        }
        for (int i = 0; i < n; i++) {
            if (workingNode.paths[i] != 0 && !workingNode.visited[i]) {
                if (!p.path.contains(workingNode)) {
                    Path np = copyPath(p);
                    np.printPath();
                    workingNode.visited[i] = true;
                    workingNode = nodes.get(i);
                    np.addNode(workingNode);
                    getPath(workingNode, np);
                }

            }

        }

        return null;
    }

    public static Path copyPath(Path p) {
        Path cp = new Path();
        for (Node node : p.path) {
            cp.addNode(node);
        }
        return p;
    }

}

class Path {

    List<Node> path = new ArrayList<Node>();
    int flow;

    public void addNode(Node n) {
        path.add(n);
    }

    public void printPath() {
        for (int i = 0; i < path.size(); i++) {
            if (i + 1 != path.size()) {
                System.out.print(path.get(i).number + " ->");
            } else {
                System.out.print(path.get(i).number + "\n");

            }

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
