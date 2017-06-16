package gameLogic;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by kuba on 16.06.17.
 */
public class Board {
    private boolean[] board;
    private boolean[] nextBoardState;
    private int generation;
    private int xSize;
    private int ySize;
    private char deadStateChar;
    private char aliveStateChar;

    public Board(int x, int y) {
        this(x, y, 'x', '.');
    }

    public Board(int x, int y, char aliveStateChar, char deadStateChar) {
        this.generation = 0;
        this.xSize = x;
        this.ySize = y;
        this.deadStateChar = deadStateChar;
        this.aliveStateChar = aliveStateChar;

        this.board = new boolean[x * y];
        for (int i = 0; i < this.board.length; i++) {
            this.board[i] = false;
        }
        this.nextBoardState = new boolean[x * y];
    }

    public int getGeneration() {
        return generation;
    }

    public void setCellState(int x, int y, boolean state) {
        if (!cellExists(x, y)) throw new ArrayIndexOutOfBoundsException();

        this.board[getIndexFromCoords(x, y)] = state;
    }

    public boolean getCellState(int x, int y) {
        if (!cellExists(x, y)) throw new ArrayIndexOutOfBoundsException();

        return this.board[getIndexFromCoords(x, y)];
    }

    public void tick() {
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                this.nextBoardState[j + i * xSize] = checkNextState(j, i);
            }
        }

        for (int i = 0; i < this.board.length; i++) {
            this.board[i] = this.nextBoardState[i];
        }

        generation++;
    }

    private boolean cellExists(int x, int y) {
        return (x * y < this.board.length - 1) && (x >= 0 && x < xSize) && (y >= 0 && y < ySize);
    }

    private int getIndexFromCoords(int x, int y) {
        if (cellExists(x, y)) return x + y * this.xSize;
        else throw new ArrayIndexOutOfBoundsException();
    }

    private int countAliveNeighbours(int x, int y) {
        int alive = 0;
        for (int i = 0; i < 3; i++) { //neighbourhood of cell -> 3x3
            for (int j = 0; j < 3; j++) {
                if (j == 1 && i == 1) continue; //don't count cell itself
                if (cellExists((x - 1) + j, (y - 1) + i)) alive += getCellState((x - 1) + j, (y - 1) + i) ? 1 : 0;
            }
        }
        return alive;
    }

    private boolean getStateFromRules(boolean cellState, int numberOfAliveNeighbours) {
        if (cellState && (numberOfAliveNeighbours == 2 || numberOfAliveNeighbours == 3)) return true;
        if (!cellState && numberOfAliveNeighbours == 3) return true;
        return false;
    }

    private boolean checkNextState(int x, int y) {
        return getStateFromRules(getCellState(x, y), countAliveNeighbours(x, y));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.board.length; i++) {
            if (i % xSize == 0) builder.append("\n");

            if (this.board[i] == true) builder.append(aliveStateChar);
            else builder.append(deadStateChar);
            builder.append(" ");
        }
        return builder.toString();
    }
}