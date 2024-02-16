package de.jAspy;

public class Simulation {

    int width;
    int height;
    int[][] board;

    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    public void printBoard() {
        System.out.println("---");
        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder("|");
            for (int x = 0; x < width; x++) {
                if (this.board[x][y] == 0) {
                    line.append(".");
                } else {
                    line.append("*");
                }
            }
            line.append("|");
            System.out.println(line);
        }
        System.out.println("---\n");
    }

    public void setAlive(int x, int y) {
        this.board[x][y] = 1;
    }

    public void setDead(int x, int y) {
        this.board[x][y] = 0;
    }

    public int countAliveNeighbors(int x, int y) {
        int count = 0;

        count += getState(x - 1,y - 1);
        count += getState(x,y - 1);
        count += getState(x + 1,y - 1);

        count += getState(x - 1,y);
        count += getState(x + 1,y);

        count += getState(x - 1,y + 1);
        count += getState(x,y + 1);
        count += getState(x + 1,y + 1);

        return count;
    }

    public int getState(int x, int y) {
        if(x < 0 || x >= width){
            return 0;
        }
        if(y < 0 || y >= height){
            return 0;
        }

        return this.board[x][y];
    }

    public void step() {
        int[][] newBoard = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int aliveNeighbors = countAliveNeighbors(x, y);

                if (getState(x, y) == 1) {
                    if (aliveNeighbors < 2 || aliveNeighbors > 3) {
                        newBoard[x][y] = 0;
                    } else {
                        newBoard[x][y] = 1;
                    }
                } else {
                    if (aliveNeighbors == 3) {
                        newBoard[x][y] = 1;
                    }
                }
            }
        }

        this.board = newBoard;
    }

    public static void main(String[] args) {
        Simulation sim = new Simulation(8, 5);

        sim.setAlive(2, 2);
        sim.setAlive(3, 2);
        sim.setAlive(4, 2);

        sim.printBoard();

        sim.step();
        sim.printBoard();

        sim.step();
        sim.printBoard();

    }

}
