package com.company;

import javax.swing.*;
import java.awt.*;

public class Square extends JPanel {

    private Piece piece = null;
    private int coordinatesY;
    private int coordinatesX;
    private Color defaultColor;
    private boolean player = false;
    boolean attacked = false;
    public void setAttacked(boolean bool){
        attacked = bool;
    }
    public void setCoordinates(int x, int y) {

        this.coordinatesX = x;
        this.coordinatesY = y;
    }

    public int getCoordinatesX() {
        return this.coordinatesX;

    }

    public int getCoordinatesY() {
        return this.coordinatesY;

    }

    public void printLocation() {
        System.out.println("( " + getCoordinatesX() + " , " + getCoordinatesY() + " )");
    }

    public void setPiece(Piece p) {
        this.piece = p;
    }

    public void setPlayer() {
        this.player = true;
        this.setBackground(Color.RED);
    }

    public void removePlayer() {
        this.player = false;
        this.setDefaultColor();

    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setNewDefaultColor(Color color) {
        this.defaultColor = color;
    }

    public void setDefaultColor() {
        this.setBackground(defaultColor);
    }

}