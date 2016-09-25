import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private int count;
    private int number;
    private ArrayList<Integer> open = new ArrayList<>();

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(n + " should be more then 0");
        }
        number = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        count = uf.count() - 2;
    }

    public void open(int i, int j) {

        isValid(i, j);

        int current = getNumber(i, j);

        if (!isOpen(i, j)) {
            open.add(current);
        }

        if (i == 1) {
            uf.union(current, 0);
        }

        ArrayList<Integer> neighbors = getNeighbors(current);
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

        if (current <= number && isOpen(current)) {
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

    private ArrayList<Integer> getNeighbors(int current) {
        int[] position = getPosition(current);
        ArrayList<Integer> neighbors = new ArrayList<>();

        if (position[0] < number) {
            neighbors.add(current + number);
        }

        if (position[0] > 1) {
            neighbors.add(current - number);
        }

        if (position[1] > 1) {
            neighbors.add(current - 1);
        }

        if (position[1] < number) {
            neighbors.add(current + 1);
        }

        return neighbors;
    }

    private int[] getPosition(int current) {
        int column = current % number;
        int row = ((current - column) / number) + (column == 0 ? 0 : 1);

        if (column == 0) {
            column = number;
        }

        return new int[]{row, column};
    }

    private boolean isOpen(int current) {
        return open.contains(current);
    }

    private void isValid(int i, int j) {
        if ((i <= 0) || (i > count) || (j <= 0) || (j > count)) {
            throw new IndexOutOfBoundsException("");
        }
    }

    public static void main(String[] args) {
        // test client (optional)
//        Percolation percolation = new Percolation(5);
//        percolation.open(2, 1);
//        percolation.open(1, 1);
//        percolation.open(2, 2);
//        percolation.open(3, 2);
//        percolation.open(4, 2);
//        percolation.open(4, 3);
//        percolation.open(4, 4);
//        percolation.open(4, 5);
//        percolation.open(5, 5);
//        percolation.isFull(4,5);
//        System.out.println(percolation.percolates());
    }

}
