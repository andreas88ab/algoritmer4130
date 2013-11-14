package oblig1algoritmer;

import java.util.*;
import java.io.*;
import java.lang.*;

/**
 *
 * @author andbergl
 */
public class Oppgave2b {

    public static int n;
    public static int id;
    public static String road;

    public static void main(String[] args) throws FileNotFoundException {
        long runTime = System.currentTimeMillis();
        System.out.println("" + args[0]);
        Scanner in = new Scanner(new File(args[0]));
        PrintWriter out = new PrintWriter(new File(args[1]));

        n = in.nextInt();

        int[][] startArr = new int[n][n];
        int x = 0;
        int y = 0;
        while (in.hasNextInt() && x < n) {
            int tall = in.nextInt();
            if (y == n) {
                y = 0;
                x++;
                startArr[x][y] = tall;
                y++;
            } else {
                startArr[x][y] = tall;
                y++;
            }

        }

        Node start = new Node();
        start.arr = startArr;
        start.id = id++;
        start.remaining();

        LinkedList<Node> pq = new LinkedList<Node>();
        LinkedList<Visits> visited = new LinkedList<Visits>();
        pq.add(start);
        boolean ok;
        int lvl = 0;
        int moves = 0;
        int nodesMade = 0;
        Node pointer = new Node();
        int runde = 0;

        //Making the "tree" A * search
        while (true) {

            ok = false;

            pointer = pq.getFirst();
            int pointerID = pq.get(0).id;

            //Locating the node with the lowest f(v) which now only is g(v)
            for (int i = 1; i < pq.size(); i++) {
                if (pointer.gv > pq.get(i).gv) {
                    pointer = pq.get(i);
                    pointerID = pq.get(i).id;
                }
            }
            pointer.remaining();
            if (pointer.hv == 0) {
                System.out.println("Finale");
                printArray(pointer.arr);
                System.out.println("" + pointer.path);
//                for (int i = 0; i < n; i++) {
//                    for (int j = 0; j < n; j++) {
//                        if (pointer.arr[i][j] == 0) {
//                            iPrev = iNow;
//                            jPrev = jNow;
//                            iNow = i;
//                            jNow = j;
//                            move(pointer, iPrev, jPrev, iNow, jNow);
//                        }
//                    }
//                }
                break;
            }
            lvl = pointer.gv + 1;
            moves++;

            //Making a list of the nodes that have been visited
            Visits v = new Visits();
            v.arr = new int[n][n];

            for (int k = 0; k < n; k++) {
                System.arraycopy(pointer.arr[k], 0, v.arr[k], 0, pointer.arr[k].length);
            }
            visited.add(v);

            Node node;
            int[][] array = new int[n][n];
            for (int k = 0; k < n; k++) {
                System.arraycopy(pointer.arr[k], 0, array[k], 0, pointer.arr[k].length);
            }

            //Locating the 0 in the matrix and trying every move of the zero
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (array[i][j] == 0) {

                        //Move UP
                        if (i - 1 >= 0) {
                            node = new Node();
                            node.arr = new int[n][n];
                            node.id = id++;
                            node.gv = lvl;
                            arrayCopy(array, node);
                            switchPlace(node, i, j, i - 1, j);
                            if (!areAlike(node.arr, visited)) {
//                                System.out.println("Legger i kø");
//                                printArray(node.arr);
                                pq.addFirst(node);
                                nodesMade++;
                                node.path = pointer.path + "U";
                                runde++;
                                if (runde % 1000 == 0) {
                                    System.out.println(runde);
                                }

                            }
                        }

                        //Move DOWN
                        if (i + 1 < n) {
                            node = new Node();
                            node.arr = new int[n][n];
                            node.id = id++;
                            node.gv = lvl;
                            arrayCopy(array, node);
                            switchPlace(node, i, j, i + 1, j);
                            if (!areAlike(node.arr, visited)) {
//                                System.out.println("Legger i kø");
//                                printArray(node.arr);
                                pq.addFirst(node);
                                nodesMade++;
                                node.path = pointer.path + "D";
                                runde++;
                                if (runde % 1000 == 0) {
                                    System.out.println(runde);
                                }
                            }
                        }

                        //Move LEFT
                        if (j - 1 >= 0) {
                            node = new Node();
                            node.arr = new int[n][n];
                            node.id = id++;
                            node.gv = lvl;
                            arrayCopy(array, node);
                            switchPlace(node, i, j, i, j - 1);
                            if (!areAlike(node.arr, visited)) {
//                                System.out.println("Legger i kø");
//                                printArray(node.arr);
                                pq.addFirst(node);
                                nodesMade++;
                                node.path = pointer.path + "L";
                                runde++;
                                if (runde % 1000 == 0) {
                                    System.out.println(runde);
                                }
                            }
                        }

                        //Move Right
                        if (j + 1 < n) {
                            node = new Node();
                            node.arr = new int[n][n];
                            node.id = id++;
                            node.gv = lvl;
                            arrayCopy(array, node);
                            switchPlace(node, i, j, i, j + 1);
                            if (!areAlike(node.arr, visited)) {
//                                System.out.println("Legger i kø");
//                                printArray(node.arr);
                                pq.addFirst(node);
                                nodesMade++;
                                node.path = pointer.path + "R";
                                runde++;
                                if (runde % 1000 == 0) {
                                    System.out.println(runde);
                                }
                            }
                        }
                        ok = true;
                    }
                    if (ok) {

                        break;
                    }
                }

                if (ok) {

                    break;
                }
            }

            for (int i = 0; i < pq.size(); i++) {
                if (pq.get(i).id == pointerID) {
//                    System.out.println("Fjern array");
//                    printArray(pq.get(i).arr);
                    pq.remove(i);
                    break;
                }
            }

            printList(pq);
            //goalReached = true;
        }
        String output = "Nodes made: " + nodesMade
                + "\nMoves made: " + moves
                + "\nLevel: " + lvl
                + "\nPath: " + pointer.path;
        try {
            out.write(output);
        } finally {
        }
        out.close();
        long runTime2 = System.currentTimeMillis();
        runTime = runTime2 - runTime;
        System.out.println("Runtime: " + runTime);
    }

    //Copies a two dim array
    public static void arrayCopy(int[][] src, Node dest) {
        for (int k = 0; k < n; k++) {
            System.arraycopy(src[k], 0, dest.arr[k], 0, src[k].length);
        }
    }

    //Switch the place of two ints in an array
    public static void switchPlace(Node n, int i, int j, int i2, int j2) {
        int temp = n.arr[i][j];
        n.arr[i][j] = n.arr[i2][j2];
        n.arr[i2][j2] = temp;
    }

    //Prints a two dim arrray
    public static void printArray(int[][] arr) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    //Print the lists of node in pq
    public static void printList(LinkedList<Node> list) {
//        for (int i = 0; i < list.size(); i++) {
//            list.get(i).remaining();
////            System.out.println("Brikker på feil plass: " + list.get(i).hv);
////            printArray(list.get(i).arr);
//        }
    }

    //Prints the list of visited nodes
    public static void printList2(LinkedList<Visits> list) {
        for (int i = 0; i < list.size(); i++) {
            printArray(list.get(i).arr);
        }
    }

    //Checkes if the node we are trying to make, already have been made earlier
    public static boolean areAlike(int[][] arr, LinkedList<Visits> v) {
        for (int k = 0; k < v.size(); k++) {
            boolean alike = true;

            int[][] arr2 = v.get(k).arr;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (arr[i][j] != arr2[i][j]) {
                        alike = false;
                    }
                }
            }
            if (alike) {
                return true;
            } else {
            }
        }

        return false;
    }

    //General node
    public static class Node {

        int[][] arr;
        int hv = -1;
        int gv;
        int id;
        String path = "";

        public void remaining() {
            hv = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if ((j + (i * n) + 1) != arr[i][j]) {
                        hv++;
                    }
                }
            }
            hv--;
        }
    }

    //Class of visited nodes
    public static class Visits {

        int[][] arr;
    }

}
