package gameLogic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kuba on 16.06.17.
 */
class BoardTest {
    @Test
    void setCellState() {
        Board b = new Board(5, 5);
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            b.setCellState(-5, -5, true);
        });
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            b.setCellState(5, -5, true);
        });
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            b.setCellState(100, 5, true);
        });
    }

    @Test
    void getCellState() {
        Board b = new Board(5, 5);
        b.setCellState(0, 1, true);
        Assertions.assertEquals(true, b.getCellState(0, 1));
        Assertions.assertEquals(false, b.getCellState(0, 0));
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            b.getCellState(-5,0);
        });
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            b.getCellState(-5,-5);
        });
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            b.getCellState(100,5);
        });
    }

    @Test
    void getGeneration(){
        Board b = new Board(5, 5);
        b.tick();
        Assertions.assertEquals(1, b.getGeneration());
    }
}