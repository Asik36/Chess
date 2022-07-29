package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Chess implements MouseListener{
    Board board;
    private Square lastSelectedPos;
    boolean whiteMove = true;
    ArrayList<Piece> whitePieces = new ArrayList<Piece>();
    ArrayList<Piece> blackPieces = new ArrayList<Piece>();
    ArrayList<Square> attackedSquares = new ArrayList<>();

    Chess(Board board){
        this.board = board;





    }
    public Board getBoard(){
        return board;
    }


    public void selectAndMove(MouseEvent e) {
        Square select = ((Square) e.getSource());


        // Select a piece
        if (select.getPiece() != null)
        {
            // Check if its this team turn
            if(select.getPiece().getTeam() == whiteMove)
            {
                // Check if a piece already have been selected, if so, remove the mark
                if (lastSelectedPos != null)
                {
                    board.boardSetDefault();
                }
                lastSelectedPos = select;
                lastSelectedPos.setBackground(Color.yellow);
                lastSelectedPos.getPiece().showMoveSet();
            }
        }

        // Check if piece selected
        if(lastSelectedPos != null){
            if(lastSelectedPos.getPiece() != null){
                // MOVE. or well, try at least
                if(lastSelectedPos.getPiece().checkMoves(select)) {
                    lastSelectedPos.getPiece().move(select);
                    board.boardSetDefault();
                    lastSelectedPos.setBackground(new Color(206, 236, 0));
                    select.setBackground(new Color(255, 255, 0));

                    nextMove();


                    lastSelectedPos = null;
                }

            }else if(!lastSelectedPos.getPiece().checkMoves(select)){
                board.boardSetDefault();
                lastSelectedPos = null;

            }

        }
    }

    private void nextMove() {
        attackedSquares = new ArrayList<>();
        blackPieces.forEach(Piece::updateMoveSet);
        whitePieces.forEach(Piece::updateMoveSet);

        if (whiteMove) {
            whiteMove = false;
            // pawn attack even if they cant move
            for (int i = 0; i < whitePieces.size(); i++) {
                if(whitePieces.get(i).getName().equals("pawn")){
                    attackedSquares.addAll(whitePieces.get(i).attackMoves);

                } else {
                    attackedSquares.addAll(whitePieces.get(i).moves);

                }
            }
            blackPieces.forEach(Piece::updateMoveSet);


        }
        else {
            whiteMove = true;
            // pawn attack even if they cant move
            for (int i = 0; i < blackPieces.size(); i++) {
                if(blackPieces.get(i).getName().equals("pawn")){
                    attackedSquares.addAll(blackPieces.get(i).attackMoves);

                } else {
                    attackedSquares.addAll(blackPieces.get(i).moves);

                }
            }
            whitePieces.forEach(Piece::updateMoveSet);

        }
        board.updateBoard();
        for (int i = 0; i < attackedSquares.size(); i++) {
            //attackedSquares.get(i).setBackground(Color.blue);
            attackedSquares.get(i).setAttacked(true);
        }
        /*for (int i = 0; i < whitePieces.size(); i++) {
            if(whitePieces.get(i).getName().equals("king")){
                if(whitePieces.get(i).getSquare().attacked){
                    for (int j = 0; j < whitePieces.size(); j++) {
                        for (int k = 0; k < whitePieces.get(j).getMoves().size(); k++) {
                            if(!whitePieces.get(j).theoreticalMove(whitePieces.get(j).moves.get(k),whitePieces.get(i))){
                                whitePieces.get(j).moves.remove(j);
                            }
                        }
                    }
                }
            }
        }*/

    }

    public void setPieces(){
        for (int i = 0; i < 8; i++) {
            new Piece("pawn", true, i, 6);
            new Piece("pawn", false, i, 1);
        }


        new Piece("rock", true, 0, 7);
        new Piece("rock", false, 0, 0);
        new Piece("rock", false, 7, 0);
        new Piece("rock", true, 7, 7);

        new Piece("knight", true, 1, 7);
        new Piece("knight", true, 6, 7);
        new Piece("knight", false, 1, 0);
        new Piece("knight", false, 6, 0);

        new Piece("bishop", true, 2, 7);
        new Piece("bishop", true, 5, 7);
        new Piece("bishop", false, 2, 0);
        new Piece("bishop", false, 5, 0);

        new Piece("queen", true, 4, 7);
        new Piece("queen", false, 4, 0);

        new Piece("king", true, 3, 7);
        new Piece("king", false, 3, 0);
        whitePieces.forEach(Piece::updateMoveSet);
        blackPieces.forEach(Piece::updateMoveSet);



    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        selectAndMove(e);
    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {


    }
}
