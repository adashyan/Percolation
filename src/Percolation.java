import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private int count;
    private int number;
    private int[] opens;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(n + " should be more then 0");
        }
        number = n;
        count = n * n;

        uf = new WeightedQuickUnionUF(count + 1);

        opens = new int[count+1];

        for (int i = 0; i < count; i++) {
            opens[i] = 0;
        }
    }

    public void open(int i, int j) {

        isValid(i, j);

        int current = getNumber(i, j);

        if (isOpen(current)) {
            return;
        }

        opens[current] = 1;

        if (i == 1) {
            uf.union(current, 0);
        }

        ArrayList<Integer> neighbors = getNeighbors(i, j);
        neighbors.stream().filter(this::isOpen).forEach(n -> uf.union(current, n));
    }

    public boolean isOpen(int i, int j) {
        isValid(i, j);
        return isOpen(getNumber(i, j));
    }

    public boolean isFull(int i, int j) {

        isValid(i, j);
        int current = getNumber(i, j);

        if (!isOpen(current)) {
            return false;
        }

        if (current <= number) {
            return true;
        }

        return uf.connected(current, 0);
    }

    public boolean percolates()
    {
        for (int i = 1; i <= number; i++) {
            if (isFull(number, i)) {
                return true;
            }
        }

        return false;
    }

    private int getNumber(int i, int j) {
        return number * i - (number - j);
    }

    private ArrayList<Integer> getNeighbors(int i, int j) {
        int current = getNumber(i, j);

        ArrayList<Integer> neighbors = new ArrayList<>();

        if (i < number) {
            neighbors.add(current + number);
        }

        if (i > 1) {
            neighbors.add(current - number);
        }

        if (j > 1) {
            neighbors.add(current - 1);
        }

        if (j < number) {
            neighbors.add(current + 1);
        }

        return neighbors;
    }

    private boolean isOpen(int current) {
        return opens[current] > 0;
    }

    private void isValid(int i, int j) {

        if (i <= 0 || i > number) {
            throw new IndexOutOfBoundsException("");
        }

        if (j <= 0 || j > number) {
            throw new IndexOutOfBoundsException("");
        }
    }

    public static void main(String[] args) {
//        Stopwatch stopwatch = new Stopwatch();

//        In in = new In("greeting57.txt");
//        int n = in.readInt();

//        Percolation p = new Percolation(n);
//        while (!in.isEmpty()) {
//            int i = in.readInt();
//            int j = in.readInt();
//            p.open(i, j);

//            if(p.percolates()) {
//                break;
//            }
//        }

//        System.out.println(p.percolates());
//        System.out.println(stopwatch.elapsedTime());
    }

}
