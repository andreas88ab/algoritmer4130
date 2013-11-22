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
        nodes = new ArrayList<Node>();
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
        int[][] arrNf = new int[n][n];
        Node newNode;
        for (int i = 0; i < n; i++) {
            newNode = new Node();
            newNode.number = i + 1;
            newNode.paths = new ArrayList<Integer>();
            newNode.path = new ArrayList<Integer>();
            newNode.path.add(1);
            for (int j = 0; j < n; j++) {
                if (arrN[i][j] != 0) {
                    newNode.paths.add(arrN[i][j]);
                } else {
                    newNode.paths.add(0);
                }
            }
            //System.out.println(newNode.paths.toString());
            nodes.add(newNode);
        }

        LinkedList<Node> fifo = new LinkedList<Node>();

        boolean cut = false;
        int maxFlow = 0;
        Node wN = new Node();
        int steps = 0;
        //Finds all paths
        while (!cut) {
            
//            nodes.get(0).visited[0] = true;
            fifo.add(nodes.get(0));
            
            while (!fifo.isEmpty()) {

                wN = copyNode(fifo.pollFirst());
//                System.out.println("kø: ");
//                ListIterator<Node> iterator = fifo.listIterator();
//                while(iterator.hasNext()){
//                    System.out.print(iterator.next().number + ", ");
//                }
//                System.out.println("");
//                System.out.println(wN.path.toString());
                if (wN.number == n) {
//                    System.out.println("wN.number = " + wN.number);
                    break;
                }
                //
                //Må legge inn en regel når køen ikke blir tom og ikke når frem til n
                //
                for (int i = 0; i < n; i++) {
                    if (wN.paths.get(i) != 0 && !wN.path.contains(i+1)) {
                        Node nN = copyNode(nodes.get(i));
                        nN.path = copyPath(wN);
                        nN.path.add(nN.number);
                        fifo.add(nN);
                    } else {
                    }
                }

            }
            //Lower the capacity
            if (wN.path.get(wN.path.size() - 1) == n) {
                int flow = getFlow(wN.path, nodes);
                maxFlow += flow;
                steps++;
                int i = wN.path.get(0) - 1;
                int j = wN.path.get(1) - 1;
                for (int k = 2; k < wN.path.size(); k++) {
                    nodes.get(i).paths.set(j, nodes.get(i).paths.get(j) - flow);
                    arrNf[i][j] += flow;
                    i = j;
                    j = wN.path.get(k) - 1;
                }
                arrNf[i][j] += flow;
                nodes.get(i).paths.set(j, nodes.get(i).paths.get(j) - flow);
//                System.out.println("Path = " + wN.path.toString());
//                System.out.println("\nFlow " + flow + "");
//                        printArray(arrNf);
//                        for(int k = 0; k < nodes.size(); k++){
//                            System.out.println(nodes.get(k).paths.toString());
//                        }
            } else {
                cut = true;
            }

            fifo = new LinkedList<Node>();

        }
        System.out.println(maxFlow);
        printArray(arrNf);
        System.out.println(wN.path.toString());
        System.out.println(steps);
    }

    public static Node copyNode(Node node) {
        Node newNode = new Node();
        newNode.number = node.number;
        newNode.path = new ArrayList<Integer>();
        newNode.paths = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            newNode.paths.add(node.paths.get(i));
        }

        for (int i = 0; i < node.path.size(); i++) {
            newNode.path.add(node.path.get(i));
        }
        return newNode;
    }

    public static int getFlow(List<Integer> list, List<Node> nodes) {
        int i = list.get(0) - 1;
        int j = list.get(1) - 1;
        int min = nodes.get(i).paths.get(j);
        for (int k = 2; k < list.size(); k++) {
//            System.out.println("Flow" + nodes.get(i).paths.get(j));
            i = j;
            j = list.get(k) - 1;

            if (min > nodes.get(i).paths.get(j)) {
                min = nodes.get(i).paths.get(j);
//                System.out.println("New flow: " + min);
            }
            if (nodes.get(i).paths.get(j) == 0) {
//                System.out.println("Flow" + nodes.get(i).paths.get(j));
                return -1;
            }
        }
        return min;
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
    //Index in paths is the node a path lead to. The value is the capacity
    List<Integer> paths;
    //Temp path
    List<Integer> path;
    //Number is the nodenumber in the figure.
    int number;

}
