package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

public class Player implements KeyListener {

    private int x;
    private int y;
    private Board board;
    Player(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        this.board.getSquare(x, y).setPlayer();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                if (this.y - 1 >= 0) {
                    board.getSquare(x, y).removePlayer();
                    this.y -= 1;
                    board.getSquare(x, y).setPlayer();
                }
                break;
            case 's':
                if (this.y + 1 <= 7) {
                    board.getSquare(x, y).removePlayer();
                    this.y += 1;
                    board.getSquare(x, y).setPlayer();
                }
                break;
            case 'd':
                if (this.x + 1 <= 7) {
                    board.getSquare(x, y).removePlayer();
                    this.x += 1;
                    board.getSquare(x, y).setPlayer();
                }
                break;
            case 'a':
                if (this.x - 1 >= 0) {
                    board.getSquare(x, y).removePlayer();
                    this.x -= 1;
                    board.getSquare(x, y).setPlayer();
                }
                break;

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}