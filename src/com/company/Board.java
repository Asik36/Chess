package com.company;

import java.awt.*;

public class Board{
    private Square[][] board;
    private Square[] attackedSquares;
    Board(){
        newBoard(new Color(47, 152, 136, 255),new Color(238, 238, 210));

    }
    private void newBoard(Color color1, Color color2) {
        board = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = new Square();
                square.setLayout(new GridBagLayout());

                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        square.setNewDefaultColor(color1);
                        square.setDefaultColor();

                    } else {
                        square.setNewDefaultColor(color2);
                        square.setDefaultColor();

                    }
                } else {
                    if (j % 2 == 0) {
                        square.setNewDefaultColor(color2);
                        square.setDefaultColor();

                    } else {
                        square.setNewDefaultColor(color1);
                        square.setDefaultColor();

                    }
                }
                square.setBounds(j * 100, i * 100, 100, 100);
                square.setCoordinates(j, i);
                board[j][i] = square;
                square.setOpaque(true);

            }

        }
    }

    public void boardSetDefault(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[j][i].setDefaultColor();
            }
        }
    }
    public void updateBoard(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[j][i].setAttacked(false);
            }
        }
    }

    public Square getSquare(int x , int y){
        return board[x][y];
    }
    public int getLength(){
        return board.length;
    }

}
