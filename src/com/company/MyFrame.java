package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.ref.Reference;
import java.util.ArrayList;

public class MyFrame extends JFrame{

    public static Chess chess = new Chess(new Board());

    private Piece tmp;

    public MyFrame(){



        // Some basic settings
        this.setTitle("?");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(813, 835);
        this.setLayout(new GridLayout(8,8));
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        for (int i = 0; i < chess.getBoard().getLength(); i++) {
            for (int j = 0; j < chess.getBoard().getLength(); j++) {
                chess.getBoard().getSquare(j , i).addMouseListener(chess);
                this.add(chess.getBoard().getSquare(j , i));

            }
        }



        Player player = new Player(0, 0, chess.getBoard());
        this.addKeyListener(player);
        chess.setPieces();


    }


}