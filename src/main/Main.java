package main;

import gameLogic.Board;

import java.io.IOException;

/**
 * Created by kuba on 16.06.17.
 */
public class Main {
    public static void main(String[] args) {
        Board b = new Board(20, 20);
        b.setCellState(5, 5, true);
        b.setCellState(6, 5, true);
        b.setCellState(7, 5, true);
        b.setCellState(5, 6, true);
        b.setCellState(6, 7, true);
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    System.out.print("\033[H\033[2J");
                    b.tick();
                    System.out.println(b);
                }
            },
            0, 500
        );
    }
}
