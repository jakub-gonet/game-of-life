package gameLogic;

/**
 * Used to manage game board.
 */
public class Board {


    private boolean[] board;
    private boolean[] nextBoardState;
    private int generation;
    private int xSize;
    private int ySize;
    private char deadStateChar;
    private char aliveStateChar;

    /**Creates new Board with specified size.
     *
     */
    public Board(int x, int y) {
        this(x, y, 'x', '.');
    }

    /**Gets board.
     * @return
     */
    public boolean[] getBoard() {
        return board;
    }

    /**Creates new Board with specified size and characters indicating state of cell.
     * @param x
     * @param y
     * @param aliveStateChar Char representing living cell.
     * @param deadStateChar Char representing dead cell.
     */
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

    /**Gets current cell generation
     *
     */
    public int getGeneration() {
        return generation;
    }

    /**Sets cell state at specified position.
     *
     */
    public void setCellState(int x, int y, boolean state) {
        if (!cellExists(x, y)) throw new ArrayIndexOutOfBoundsException();

        this.board[getIndexFromCoords(x, y)] = state;
    }

    /**Gets cell state at specified position.
     *
     */
    public boolean getCellState(int x, int y) {
        if (!cellExists(x, y)) throw new ArrayIndexOutOfBoundsException();

        return this.board[getIndexFromCoords(x, y)];
    }

    /**Apply rules to board and tick forward.
     *
     */
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

    /**Checks if cell exist at specified position.
     *
     */
    private boolean cellExists(int x, int y) {
        return (x * y < this.board.length - 1) && (x >= 0 && x < xSize) && (y >= 0 && y < ySize);
    }

    /**Gets array index from coordinates.
     *
     */
    private int getIndexFromCoords(int x, int y) {
        if (cellExists(x, y)) return x + y * this.xSize;
        else throw new ArrayIndexOutOfBoundsException();
    }

    /**Count living cell neighbours.
     *
     */
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

    /**Defines new state of specified cell.
     *
     */
    private boolean getStateFromRules(boolean cellState, int numberOfAliveNeighbours) {
        if (cellState && (numberOfAliveNeighbours == 2 || numberOfAliveNeighbours == 3)) return true;
        if (!cellState && numberOfAliveNeighbours == 3) return true;
        return false;
    }

    /**Checks new state of cell according to rules.
     *
     */
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